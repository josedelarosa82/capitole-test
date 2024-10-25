package com.capitole.ecommerce.service;

import com.capitole.ecommerce.model.Car;
import com.capitole.ecommerce.model.Item;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ShoppingCarService {

    Car calculateShoppingCart(List<Item> items);

    Car uploadFile(MultipartFile file);

}
