package com.capitole.ecommerce;

import com.capitole.ecommerce.controller.ShoppingCarController;
import com.capitole.ecommerce.service.FileProcessingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ShoppingCarController.class)
public class ShoppingCarControllerTests {
    @Autowired
    private MockMvc mvc;

    @Value("${filePath}")
    private String basePath;

    @MockBean
    private FileProcessingService service;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAllFilesShouldReturnExistingFileList() throws Exception {

        List<String> files = List.of("file1.txt", "file2.txt");

        given(service.fileList()).willReturn(files);

        mvc.perform(get("/file/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void fileShouldBeDownloaded() throws Exception {
        String fileName = "file1.txt";
        File file = new File(basePath + fileName);

        when(service.downloadFile(fileName)).thenReturn(new UrlResource(file.toURI()));

        mockMvc.perform(get("/file/download/{name}", fileName))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(content().bytes(Files.readAllBytes(file.toPath())));
    }

    @Test
    public void fileShouldBeUploaded() throws Exception {

        File f1 = new File(basePath + "file1.txt");
        MockMultipartFile file = new MockMultipartFile("uploadTest.txt", Files.readAllBytes(f1.toPath()));

        given(service.uploadFile(Mockito.any(MultipartFile.class))).willReturn("CREATED");

        mvc.perform(MockMvcRequestBuilders.multipart("/file/upload")
                        .file("file", Files.readAllBytes(f1.toPath())))
                .andExpect(status().isCreated());
    }
}
