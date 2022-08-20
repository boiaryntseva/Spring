package com.example.demo.repo;

import com.example.demo.controller.StorageBook;

import java.util.List;

public interface LibraryRepositoryCustom {
    List<StorageBook> findAllByAuthor (String authorName);
}
