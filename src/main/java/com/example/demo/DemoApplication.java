package com.example.demo;

import com.example.demo.controller.StorageBook;
import com.example.demo.repo.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
//public class DemoApplication implements CommandLineRunner {
   public class DemoApplication {

    @Autowired
    LibraryRepository libraryRepository;
	//StorageBook book;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

//    @Override
//    public void run(String... args) throws Exception {
//        StorageBook lib = libraryRepository.findById("isbn123").get();
//        System.out.println(lib.getAuthor());
//		StorageBook book = new StorageBook();
//        book.setBookName("AutowireNO");
//        book.setAisle(1);
//        book.setIsbn("newisbn");
//        book.setAuthor("Kate Baz");
//        book.setId("1newisbnBAZ");
//		libraryRepository.save(book);
//        List<StorageBook> storageBookList = libraryRepository.findAll();
//		System.out.println(storageBookList);
//        for( StorageBook item: storageBookList){
//            System.out.println(item.getId());
//        }
//        //System.out.println(libraryRepository.findAll().size());
//    }
}
