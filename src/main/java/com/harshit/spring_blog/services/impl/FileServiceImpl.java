package com.harshit.spring_blog.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.harshit.spring_blog.services.FileService;

@Service
public class FileServiceImpl implements FileService {

  @Override
  public String uploadImage(String path, MultipartFile file) throws IOException {
    // File name abc.png
    String name = file.getOriginalFilename();

    // random name generate file
    String randomId = UUID.randomUUID().toString();

    String fileName = "";
    if (name != null) {
      fileName = randomId.concat(name.substring(name.lastIndexOf(".")));
    }

    // full path
    String filePath = path + File.separator + fileName;

    // create folderif not created
    File f = new File(path);
    if (!f.exists()) {
      f.mkdir();
    }

    // file copy
    Files.copy(file.getInputStream(), Paths.get(filePath));
    return fileName;
  }

  @Override
  public InputStream getResoure(String path, String filename) throws FileNotFoundException {
    String fullPath = path + File.separator + filename;
    InputStream is = new FileInputStream(fullPath);
    // db logic to return inputstream
    return is;
  }

}
