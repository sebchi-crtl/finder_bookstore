package com.interview.bookstore.mapper;

import com.interview.bookstore.dto.BookResponseDTO;
import com.interview.bookstore.entity.Book;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class BookMapper implements Function<Book, BookResponseDTO> {

    @Override
    public BookResponseDTO apply(Book book) {
        return new BookResponseDTO(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.isAvailable(),
                book.getDateCreated()
        );
    }

}
