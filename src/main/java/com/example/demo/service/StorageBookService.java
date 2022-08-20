package com.example.demo.service;

import com.example.demo.controller.StorageBook;
import com.example.demo.repo.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service //business logic to be checked here
public class StorageBookService {
    @Autowired
    LibraryRepository libraryRepository;

    public String buildId(String isbn, int aile) {
        if (isbn.startsWith("Z")) {
            return "OLD" + isbn + aile;
        }
        return isbn + aile;
    }

    public boolean checkBookAlreadyExist(String id) {
        Optional<StorageBook> storageBook = libraryRepository.findById(id);
        if (storageBook.isPresent())
            return true;
        else
            return false;
    }

    public StorageBook getBookById(String id){
        return libraryRepository.findById(id).get();
    }
}
