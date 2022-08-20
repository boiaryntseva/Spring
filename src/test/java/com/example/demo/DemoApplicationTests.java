package com.example.demo;

import com.example.demo.controller.AddResponse;
import com.example.demo.controller.StorageBook;
import com.example.demo.controller.StorageBookController;
import com.example.demo.repo.LibraryRepository;
import com.example.demo.service.StorageBookService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
//@WebMvcTest

class DemoApplicationTests {

	@Autowired
	StorageBookController storageBookController;

	@MockBean
	LibraryRepository libraryRepository;
	@MockBean
	StorageBookService storageBookService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	@Test
	public void checkIdLogic() {
		String id = storageBookService.buildId("ZMAN", 123);
		assertEquals(id, "OLDZMAN123");
	}

	@Test
	public void addBookTest() {
		StorageBook storageBook = buildBook();
		when(storageBookService.buildId(storageBook.getIsbn(), storageBook.getAisle())).
				thenReturn(storageBook.getId());
		when(storageBookService.checkBookAlreadyExist(storageBook.getId())).thenReturn(false);


		ResponseEntity responseEntity = storageBookController.addBookImplementation(buildBook());
		responseEntity.getStatusCode();
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

		AddResponse addResponse = (AddResponse) responseEntity.getBody();
		addResponse.getId();
		assertEquals(storageBook.getId(), addResponse.getId());


	}

	@Test
	public void addBookTestControllerTest() throws Exception {
		StorageBook storageBook = buildBook();
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonString = objectMapper.writeValueAsString(storageBook);

		when(storageBookService.buildId(storageBook.getIsbn(), storageBook.getAisle())).
				thenReturn(storageBook.getId());
		when(storageBookService.checkBookAlreadyExist(storageBook.getId())).thenReturn(false);
		when(libraryRepository.save(any())).thenReturn(storageBook);
		this.mockMvc.perform(post("/addBook").contentType(MediaType.APPLICATION_JSON)
						.content(jsonString)).andDo(print()).andExpect(status().isCreated())
				.andExpect((ResultMatcher) jsonPath("$.id").value(storageBook.getId()));
	}

	@Test
	public void getBookByAuthor() throws Exception {
		List<StorageBook> storageBookList = new ArrayList<StorageBook>();
		storageBookList.add(buildBook());
		storageBookList.add(buildBook());
		when(libraryRepository.findAllByAuthor(any())).thenReturn(storageBookList);
		this.mockMvc.perform(get("/getBooks/author").param("authorName", "Alex"))
				.andDo(print()).andExpect(status().isOk())
				.andExpect((ResultMatcher) jsonPath("$.length()").value(2))
				.andExpect((ResultMatcher) jsonPath("$.[0].id").value("unittest123"));
	}

	@Test
	public void updateBook() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonString = objectMapper.writeValueAsString(updatedBook());
		StorageBook storageBook = buildBook();
		when(storageBookService.getBookById(any())).thenReturn(buildBook());
		this.mockMvc.perform(put("/updateBook/" + storageBook.getId()).contentType(MediaType.APPLICATION_JSON)
				.content(jsonString)).andDo(print()).andExpect(status().isOk())
				.andExpect((ResultMatcher) content()
						.json("{\"bookName\":\"update Test Book\",\"aisle\":1234,\"id\":\"unittest123\",\"isbn\":\"unittest\",\"author\":\"updatespring\"}"));

	}

//	@Test
//	public void deleteBookControllerTest() throws Exception {
//		when(storageBookService.getBookById(any())).thenReturn(buildBook());
//		doNothing().when(libraryRepository).delete(buildBook());
//		this.mockMvc.perform(delete("/deleteBook").contentType(MediaType.APPLICATION_JSON)
//				.content("{\n" +
//						"    \"id\": \"unittest123\"\n" +
//						"}")).andDo(print()).andExpect(status().isCreated()).
//				andExpect(content().string("Book is deleted"));
//	}

	public StorageBook buildBook() {
		StorageBook storageBook = new StorageBook();
		storageBook.setBookName("Test Book");
		storageBook.setId("unittest123");
		storageBook.setAuthor("spring");
		storageBook.setIsbn("unittest");
		storageBook.setAisle(123);
		return storageBook;
	}

	public StorageBook updatedBook() {
		StorageBook storageBook = new StorageBook();
		storageBook.setBookName("update Test Book");
		storageBook.setId("unittest123");
		storageBook.setAuthor("updatespring");
		storageBook.setIsbn("unittest");
		storageBook.setAisle(1234);
		return storageBook;

	}
}