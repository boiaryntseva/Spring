package com.example.demo.repo;

import com.example.demo.controller.StorageBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryRepository extends JpaRepository <StorageBook, String>, LibraryRepositoryCustom {
}
