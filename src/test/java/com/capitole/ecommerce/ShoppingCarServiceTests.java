package com.capitole.ecommerce;

import com.capitole.ecommerce.service.FileProcessingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShoppingCarServiceTests {

    @Value("${filePath}")
    private String filePath;

    @Autowired
    private FileProcessingService service;

    @Test
    public void fileListMethodShouldReturnTheExistingFileList(){

        File file = new File(filePath);

        List<String> existing = Arrays.asList(Objects.requireNonNull(file.list()));

        List<String> fileList =  service.fileList();

        assertEquals(fileList, existing);
    }

    @Test
    public void fileUploadMethodShouldReturnCreatedOnSuccess() throws Exception {

        MultipartFile mf = new MockMultipartFile("testUpload.txt", "This is test".getBytes());

        String response =  service.uploadFile(mf);

        assertEquals("CREATED", response);

        File file = new File(filePath+"testUpload.txt");

        file.delete();
    }


}
