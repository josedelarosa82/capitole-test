package com.capitole.ecommerce.controller;

import com.capitole.ecommerce.model.Error;
import com.capitole.ecommerce.model.Item;
import com.capitole.ecommerce.service.FileProcessingService;
import com.capitole.ecommerce.service.ShoppingCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/shopping-car")
public class ShoppingCarController {


    @Autowired
    private ShoppingCarService shoppingCarService;
    @Autowired
    private FileProcessingService fileProcessingService;

    @PostMapping("/calculate-total")
    public ResponseEntity<?> calculateShoppingCar(@RequestBody List<Item> items) {
        try{
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(shoppingCarService.calculateShoppingCart(items));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatusCode.valueOf(500)).body(Error.builder().code("ERR001").type("Technical").description(e.getMessage()).build());
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestBody MultipartFile file){

        try {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(shoppingCarService.uploadFile(file));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatusCode.valueOf(500)).body(Error.builder().code("ERR001").type("Technical").description(e.getMessage()).build());
        }

    }

}
