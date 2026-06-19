package com.s2p.controller;

import com.s2p.service.HolidayExcelService;
import com.s2p.service.HolidayPdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/holidays")
public class HolidayController {

    @Autowired
    private HolidayExcelService excelService;

    @Autowired
    private HolidayPdfService pdfService;

    @PostMapping("/upload/excel")
    public String uploadExcel(@RequestParam MultipartFile file) {
        excelService.upload(file);
        return "Excel uploaded successfully";
    }
    @PostMapping("/upload/pdf")
    public String uploadPdf(@RequestParam MultipartFile file) {
        pdfService.upload(file);
        return "PDF uploaded successfully";
    }
}
