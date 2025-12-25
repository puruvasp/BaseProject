package com.s2p.service;

import com.s2p.model.Holiday;
import com.s2p.repository.HolidayRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class HolidayExcelService {

    @Autowired
    private HolidayRepository repository;

    public void upload(MultipartFile file) {

        try (Workbook wb = new XSSFWorkbook(file.getInputStream())) {

            Sheet sheet = wb.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                Row row = sheet.getRow(i);

                Holiday h = new Holiday();
                h.setHolidayName(row.getCell(0).getStringCellValue());
                h.setStartDate(row.getCell(1)
                        .getLocalDateTimeCellValue().toLocalDate());
                h.setEndDate(row.getCell(2)
                        .getLocalDateTimeCellValue().toLocalDate());
                h.setMessage(row.getCell(3).getStringCellValue());

                repository.save(h);
            }
        } catch (Exception e) {
            throw new RuntimeException("Excel upload failed", e);
        }
    }
}
