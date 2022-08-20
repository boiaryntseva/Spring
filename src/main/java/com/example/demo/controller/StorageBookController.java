package com.example.demo.controller;

import com.example.demo.repo.LibraryRepository;
import com.example.demo.service.StorageBookService;
import org.apache.log4j.Logger;
import org.hibernate.annotations.common.util.impl.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController
public class StorageBookController {

    @Autowired
    LibraryRepository libraryRepository;
    @Autowired
    StorageBookService storageBookService;

    private final Logger logger = Logger.getLogger("StorageBookController");
   // public  static final Logger logger=LoggerFactory.getLogger(StorageBookController.class);

    @PostMapping("/addBook")

    public ResponseEntity addBookImplementation(@RequestBody StorageBook storageBook) {
        AddResponse addResponse = new AddResponse();


        String id = storageBookService.buildId(storageBook.getIsbn(), storageBook.getAisle());

        if (!storageBookService.checkBookAlreadyExist(id)) {
            storageBook.setId(id);
            libraryRepository.save(storageBook);

            HttpHeaders headers = new HttpHeaders();
            headers.add("unique", (storageBook.getIsbn() + storageBook.getAisle()));
            addResponse.setMsg("Added");
            addResponse.setId(storageBook.getIsbn() + storageBook.getAisle());
            return new ResponseEntity<AddResponse>(addResponse, headers, HttpStatus.CREATED);
        } else {
            logger.info("Book exists");
            addResponse.setMsg("Book exists");
            addResponse.setId(id);
            return new ResponseEntity<AddResponse>(addResponse, HttpStatus.ACCEPTED);
        }
    }

    @GetMapping("/getBooks/{id}")
        public StorageBook getBookById (@PathVariable(value="id") String id) {
           try {
               StorageBook storageBook = libraryRepository.findById(id).get();
               return storageBook;
           }
           catch (Exception e) {
               throw new ResponseStatusException(HttpStatus.NOT_FOUND);
           }
        }

    @GetMapping ("/getBooks/author")
        public List<StorageBook> getBookByAuthorName(@RequestParam(value="authorName") String authorName) {
        return libraryRepository.findAllByAuthor(authorName);
    }

    @PutMapping ("/updateBook/{id}")
         public ResponseEntity<StorageBook> updateBook(@PathVariable(value = "id") String id, @RequestBody StorageBook storageBook){
            //StorageBook existingBook = libraryRepository.findById(id).get();
            StorageBook existingBook = storageBookService.getBookById(id);

            existingBook.setAisle(storageBook.getAisle());
            existingBook.setAuthor(storageBook.getAuthor());
            existingBook.setBookName(storageBook.getBookName());
            libraryRepository.save(existingBook);
            return new ResponseEntity<StorageBook>(existingBook, HttpStatus.OK);

    }
    @DeleteMapping ("/deleteBook")
        public ResponseEntity deleteBookById (@RequestBody StorageBook storageBook) {
           //StorageBook bookToDelete = libraryRepository.findById(storageBook.getId()).get();
        StorageBook bookToDelete = storageBookService.getBookById(storageBook.getId());
           libraryRepository.delete(bookToDelete);
           return new ResponseEntity<>("Book is deleted", HttpStatus.CREATED);

    }
}
