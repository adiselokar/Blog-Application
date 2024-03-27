package com.blog.services.impl;

import com.blog.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadImage( String path, MultipartFile multipartFile ) throws IOException {
        // File Name
        String filename = multipartFile.getOriginalFilename();

        // Random File Name Generator
        String randomId = UUID.randomUUID().toString();
        String newFileName = randomId.concat(filename.substring(filename.lastIndexOf(".")));

        // Full Path
        String filePath = path + File.separator + newFileName;

        // Create Folder if not created or exist
        File file = new File(path);
        if (!file.exists()){
            file.mkdir();
        }

        // FIle Copy
        Files.copy(multipartFile.getInputStream(), Paths.get(filePath));

        return newFileName;
    }

    @Override
    public InputStream getResource( String path, String fileName ) throws FileNotFoundException
    {
        String fullPath = path + File.separator + fileName;
        InputStream inputStream = new FileInputStream(fullPath);
        return inputStream;
    }

}
