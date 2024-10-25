package com.capitole.ecommerce.service;




import com.capitole.ecommerce.model.Car;
import com.capitole.ecommerce.model.Item;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShoppingCarServiceImpl implements ShoppingCarService{

    @Value("${app.capitole.values.iva}")
    private int IVA;


    //Funcion que calcula el total bruto de los items
    private double calculateTotalGross(List<Item> items) {
        return items.stream().mapToDouble(Item::getValue).sum();
    }


    //Funcion que calcula el carrito de compras
    public Car calculateShoppingCart(List<Item> items) {
        try{
            double totalGross = calculateTotalGross(items);
            return Car.builder().
                                totalGross(totalGross).
                                totalNet( totalGross - ((totalGross * IVA) / 100) ).
                                totalTaxes((totalGross * IVA) / 100).
                                totalItems(items.size()).
                                taxRate(IVA).
                                build();
        } catch (Exception e) {
            throw new RuntimeException("Error calculating total gross "+ e.getMessage());
        }
    }


    public Car uploadFile(MultipartFile file) {
        List<Item> items = new ArrayList<>();
        String line;
        try {
            if (file.getSize() == 0)
                throw new RuntimeException("File is empty");
            BufferedReader in = new BufferedReader( new InputStreamReader(file.getInputStream()) );
            while ((line = in.readLine()) != null) {
                if (line.isEmpty())
                    break;
                String[] item = line.split(",");
                if(item.length != 3)
                    throw new RuntimeException("Item ("+line+") with incorrect size");
                try {
                    items.add(Item.builder().id(item[0]).name(item[1]).value(Float.parseFloat(item[2])).build());
                }catch (Exception e) {
                    throw new RuntimeException("Error formating Item ("+line+") "+ e.getMessage());
                }
            }
            return calculateShoppingCart(items);
        } catch (Exception e) {
            throw new RuntimeException("Error uploading file "+ e.getMessage());
        }
    }

}
