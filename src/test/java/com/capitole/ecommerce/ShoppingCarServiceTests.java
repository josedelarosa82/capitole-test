package com.capitole.ecommerce;

import com.capitole.ecommerce.model.Car;
import com.capitole.ecommerce.model.Item;
import com.capitole.ecommerce.service.ShoppingCarService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShoppingCarServiceTests {

    @Value("${filePath}")
    private String filePath;

    @Autowired
    private ShoppingCarService service;

    private Car car = Car.builder().taxRate(18).totalTaxes(2520).totalNet(11480).totalGross(14000).totalItems(4).build();

    @Test
    public void uploadFile() throws Exception  {

        File f1 = new File(filePath + "file1.txt");
        MockMultipartFile file = new MockMultipartFile("file1.txt", Files.readAllBytes(f1.toPath()));

        assertEquals(service.uploadFile(file).getTotalNet(), car.getTotalNet());
    }

    @Test
    public void calculateShoppingCartOnSuccess() throws Exception {

        List<Item> items = List.of(
                Item.builder().id("1").name("Cocacola").value(2000).build(),
                Item.builder().id("2").name("Pepsi").value(3000).build(),
                Item.builder().id("3").name("Colombiana").value(4000).build(),
                Item.builder().id("3").name("Postobon").value(5000).build());

        assertEquals(service.calculateShoppingCart(items).getTotalNet(), car.getTotalNet());

    }


}
