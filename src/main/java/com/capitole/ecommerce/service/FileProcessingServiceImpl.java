package com.capitole.ecommerce.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.*;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileProcessingServiceImpl implements FileProcessingService {

    @Value("${filePath}")
    private String basePath;

    @Override
    public List<String> fileList() {
        File dir = new File(basePath);
        File[] files = dir.listFiles();

        return files != null ? Arrays.stream(files).map(File::getName).collect(Collectors.toList()) : null;
    }

    @Override
    public String uploadFile(MultipartFile file) throws Exception {
        File dir = new File(basePath + file.getName());
        long length = 0;
        String line;
        /*if (dir.exists()) {
            throw new RuntimeException("File already exists");
        }*/

        Path path = Path.of(basePath + file.getName());

        try {
            if (file.getSize() == 0) {
                throw new RuntimeException("File is empty");
            }

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(file.getInputStream()));
            while ((line = in.readLine()) != null) {
                if (line.isEmpty()) {
                    break;
                }
                System.out.println(line);
                length += line.length();
            }


            /*byte[] buf = new byte[1024];
            int len;
            while ((len = file.getInputStream().read(buf)) > 0) {
                System.out.println(new String(buf, 0, len));
            }
            System.out.println(file.getInputStream().read(buf));*/
            return "CREATED";
        } catch (Exception e) {
            throw new RuntimeException("Error uploading file "+ e.getMessage());
        }
    }

    @Override
    public Resource downloadFile(String fileName) {
        File dir = new File(basePath + fileName);
        try {
            if (dir.exists()) {
                return new UrlResource(dir.toURI());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return null;
    }

}
