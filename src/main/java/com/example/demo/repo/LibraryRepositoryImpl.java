package com.example.demo.repo;

import com.example.demo.controller.StorageBook;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class LibraryRepositoryImpl implements LibraryRepositoryCustom{
    @Autowired
    LibraryRepository libraryRepository;

    @Override
    public List<StorageBook> findAllByAuthor(String authorName) {
        List <StorageBook> booksWithAuthor= new ArrayList<StorageBook>();

        List <StorageBook> storageBookList =libraryRepository.findAll();
        for (StorageBook item: storageBookList){
           if(item.getAuthor().equalsIgnoreCase(authorName)) {
                booksWithAuthor.add(item);
           }
        }
        return booksWithAuthor;
    }
}
