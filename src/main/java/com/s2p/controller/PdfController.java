package com.s2p.controller;

import com.s2p.util.PdfReaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/pdf")
public class PdfController {

    @PostMapping("/upload")
    public ResponseEntity<String> uploadPdf(@RequestParam("file") MultipartFile file) {
        try {
//            String uploadDir = "uploads/";
            String uploadDir = System.getProperty("user.dir") + File.separator + "uploads" + File.separator;

            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String filePath = uploadDir + file.getOriginalFilename();
            file.transferTo(new File(filePath));

            String text = PdfReaderUtil.extractText(filePath);
            System.out.println("Extracted text:\n" + text);

            Pattern namePattern = Pattern.compile("Name:\\s*(.*)");
            Matcher matcher = namePattern.matcher(text);
            if (matcher.find()) {
                String name = matcher.group(1);
                System.out.println("Student Name: " + name);
                
            }

            return ResponseEntity.ok("File uploaded and data extracted successfully!");

        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}
