package com.rentalhive.rentalhive.service;


import com.rentalhive.rentalhive.model.Files;
import com.rentalhive.rentalhive.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;


    public void saveFile(Files file) {
        fileRepository.save(file);
    }

    public Files getFile(int id) {
        return fileRepository.findById(id).orElse(null);
    }

    public void deleteFile(int id) {
        fileRepository.deleteById(id);
    }

    public List<Files> getAllByRentalRequestId(int rentalRequestId) {
        return fileRepository.findByRentalRequestId(rentalRequestId);
    }

}
