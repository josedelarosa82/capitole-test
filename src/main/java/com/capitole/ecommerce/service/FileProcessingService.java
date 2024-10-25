package com.capitole.ecommerce.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileProcessingService {
    List<String> fileList();

    String uploadFile(MultipartFile file) throws Exception;

    Resource downloadFile(String fileName);
}
