package com.interview.bookstore;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interview.bookstore.dao.BookStoreRepo;
import com.interview.bookstore.dto.BookRequestDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookstoreApplicationTests {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private BookStoreRepo repo;

	@Test
	void shouldCreateProduct() throws Exception {
		BookRequestDTO productRequest = getProductRequest();
		String productRequestString = objectMapper.writeValueAsString(productRequest);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/sign-up")
						.contentType(MediaType.APPLICATION_JSON)
						.content(productRequestString))
				.andExpect(status().isCreated());
		assertEquals(1, repo.findAll().size());
	}

	private BookRequestDTO getProductRequest() {
		BookRequestDTO request = new BookRequestDTO("orgName", "email@mail.com", true);

		return request;
	}

	@Test
	void contextLoads() {
		assertTrue(true);
	}

}
