package com.s2p.service;

import com.s2p.model.Holiday;
import com.s2p.repository.HolidayRepository;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Service
public class HolidayPdfService {

    @Autowired
    private HolidayRepository repository;

    public void upload(MultipartFile file) {

        try (PDDocument doc = PDDocument.load(file.getInputStream())) {

            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(doc);

            for (String line : text.split("\n")) {

                String[] data = line.split("\\|");
                if (data.length < 4) continue;

                Holiday h = new Holiday();
                h.setHolidayName(data[0].trim());
                h.setStartDate(LocalDate.parse(data[1].trim()));
                h.setEndDate(LocalDate.parse(data[2].trim()));
                h.setMessage(data[3].trim());

                repository.save(h);
            }
        } catch (Exception e) {
            throw new RuntimeException("PDF upload failed", e);
        }
    }
}
