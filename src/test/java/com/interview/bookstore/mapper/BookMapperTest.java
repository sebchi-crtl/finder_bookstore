package com.interview.bookstore.mapper;

import com.interview.bookstore.dto.BookResponseDTO;
import com.interview.bookstore.entity.Book;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BookMapperTest {

    @InjectMocks
    private BookMapper mapper;

    @Test
    void apply() {
        Book book = Book
                .builder()
                .id(1L)
                .title("Book 1")
                .author("Author 1")
                .available(true)
                .dateCreated(LocalDateTime.now())
                .build();
        BookResponseDTO responseDTO = mapper.apply(book);
        assertNotNull(responseDTO);
        assertEquals("Book 1", responseDTO.title());
        assertEquals("Author 1", responseDTO.author());
    }
}