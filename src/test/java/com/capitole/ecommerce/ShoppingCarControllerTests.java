package com.capitole.ecommerce;

import com.capitole.ecommerce.controller.ShoppingCarController;
import com.capitole.ecommerce.model.Car;
import com.capitole.ecommerce.model.Item;
import com.capitole.ecommerce.service.ShoppingCarService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ShoppingCarController.class)
public class ShoppingCarControllerTests {
    @Autowired
    private MockMvc mvc;

    @Value("${filePath}")
    private String basePath;

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ShoppingCarService serviceShopping;

    private Car car = Car.builder().taxRate(18).totalTaxes(1).totalGross(0).totalItems(4).build();

    @Test
    public void fileShouldBeUploaded() throws Exception {

        File f1 = new File(basePath + "file1.txt");
        MockMultipartFile file = new MockMultipartFile("file1.txt", Files.readAllBytes(f1.toPath()));

        given(serviceShopping.uploadFile(Mockito.any(MultipartFile.class))).willReturn(car);

        mvc.perform(MockMvcRequestBuilders.multipart("/shopping-car/upload")
                        .file("file", Files.readAllBytes(f1.toPath())))
                .andExpect(status().isOk());
    }

    @Test
    public void calculateShoppingCar() throws Exception {
        Gson gson = new Gson();

        List<Item> items = List.of(
                Item.builder().id("1").name("Cocacola").value(2000).build(),
                Item.builder().id("2").name("Pepsi").value(3000).build(),
                Item.builder().id("3").name("Colombiana").value(4000).build(),
                Item.builder().id("3").name("Postobon").value(5000).build());

        given(serviceShopping.calculateShoppingCart(items)).willReturn(car);

        mvc.perform(post("/shopping-car/calculate-total")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(items)))
                .andExpect(status().isOk());
    }
}
