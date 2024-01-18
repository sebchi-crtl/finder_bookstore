package com.interview.bookstore.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.interview.bookstore.dto.BookRequestDTO;
import com.interview.bookstore.dto.BookResponseDTO;
import com.interview.bookstore.service.IBookService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IBookService bookService;

    @Test
    void getAllBooks() throws Exception{
        List<BookResponseDTO> mockBooks = Arrays.asList(
                new BookResponseDTO(1L, "Book 1", "Author 1", true, LocalDateTime.now()),
                new BookResponseDTO(2L, "Book 2", "Author 2", false, LocalDateTime.now())
        );

        Mockito.when(bookService.getAllBooks()).thenReturn(mockBooks);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/book"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", Matchers.is("Book 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].author", Matchers.is("Author 2")));

    }

    @Test
    void getBookById() throws Exception {
        Long bookId = 1L;
        BookResponseDTO mockBook = new BookResponseDTO(bookId, "Book 1", "Author 1", true, LocalDateTime.now());

        Mockito.when(bookService.getBookById(bookId)).thenReturn(mockBook);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/book/{id}", bookId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Book 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author", Matchers.is("Author 1")));

    }

    @Test
    void addBook() throws Exception {
        BookRequestDTO requestDTO = new BookRequestDTO("New Book", "New Author", true);
        BookResponseDTO mockResponseDTO = new BookResponseDTO(1L, "New Book", "New Author", true, LocalDateTime.now());

        // Mock service behavior
        Mockito.when(bookService.addBook(requestDTO)).thenReturn(mockResponseDTO);

        // Perform the request
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("New Book")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author", Matchers.is("New Author")));
    }

    @Test
    void updateBook() throws Exception {
        Long bookId = 1L;
        BookRequestDTO requestDTO = new BookRequestDTO("Updated Book", "Updated Author", false);
        BookResponseDTO mockResponseDTO = new BookResponseDTO(bookId, "Updated Book", "Updated Author", false, LocalDateTime.now());

        Mockito.when(bookService.updateBook(bookId, requestDTO)).thenReturn(mockResponseDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/book/{id}", bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Updated Book")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.available", Matchers.is(false)));

    }

    @Test
    void deleteBook() throws Exception {
        long bookId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/book/{id}", bookId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("deletion successful with id = " + bookId));

    }
}