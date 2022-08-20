package com.example.demo;

import com.example.demo.controller.StorageBook;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.util.Assert;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class testIT {

    @Test
    public void getAuthorName() throws JSONException {
        TestRestTemplate restTemplate = new TestRestTemplate();
        String expected = "[\n" +
                "    {\n" +
                "        \"bookName\": \"H2\",\n" +
                "        \"aisle\": 1,\n" +
                "        \"id\": \"isbn1\",\n" +
                "        \"isbn\": \"isbn\",\n" +
                "        \"author\": \"Kate\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"bookName\": \"H3\",\n" +
                "        \"aisle\": 2,\n" +
                "        \"id\": \"isbn2\",\n" +
                "        \"isbn\": \"isbn\",\n" +
                "        \"author\": \"Kate\"\n" +
                "    }\n" +
                "]";
        ResponseEntity<String> response =
                restTemplate.getForEntity("http://localhost:8080/getBooks/author?authorName=Kate", String.class);
        System.out.println(response.getBody());
        System.out.println(response.getStatusCode());
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void addBookITTest(){
        TestRestTemplate testRestTemplate = new TestRestTemplate();

        HttpHeaders headers = new HttpHeaders(); //creates headers
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity <StorageBook>httpEntity =
                new HttpEntity <StorageBook>(buildBook(), headers);// converts java object to json string (body), Request

        ResponseEntity <String> responseEntity =
                testRestTemplate.postForEntity("http://localhost:8080/addBook", httpEntity, String.class );
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(buildBook().getId(),responseEntity.getHeaders().get("unique").get(0));
    }
    public StorageBook buildBook() {
        StorageBook storageBook = new StorageBook();
        storageBook.setBookName("Test Book");
        storageBook.setId("unittest123");
        storageBook.setAuthor("spring");
        storageBook.setIsbn("unittest");
        storageBook.setAisle(123);
        return storageBook;
    }
}
