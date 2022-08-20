package com.example.demo;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

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
}
