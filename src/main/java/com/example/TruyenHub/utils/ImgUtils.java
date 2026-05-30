package com.example.TruyenHub.utils;

import com.example.TruyenHub.exception.DelegationServiceException;
import com.example.TruyenHub.model.entity.ChapterComic;
import com.example.TruyenHub.model.entity.ChapterImage;
import com.example.TruyenHub.model.enums.ResultCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

public class ImgUtils {

    public static String saveFile(MultipartFile file, String folderName, String uploadDir) throws IOException {
        Path folderPath = Paths.get(uploadDir, folderName);
        if (!Files.exists(folderPath)) {
            Files.createDirectories(folderPath);
        }
        String fileName = UUID.randomUUID() + "_" + Objects.requireNonNull(file.getOriginalFilename());
        Path filePath = folderPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return uploadDir + "/" + folderName + "/" + fileName;
    }
}
