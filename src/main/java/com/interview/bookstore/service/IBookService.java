package com.interview.bookstore.service;

import com.interview.bookstore.dto.BookRequestDTO;
import com.interview.bookstore.dto.BookResponseDTO;

import java.util.List;

public interface IBookService {

    List<BookResponseDTO> getAllBooks();
    BookResponseDTO getBookById(Long id);
    BookResponseDTO addBook(BookRequestDTO book);
    BookResponseDTO updateBook(Long id, BookRequestDTO updatedBook);
    void deleteBook(Long id);

}
