package com.interview.bookstore.controller;

import com.interview.bookstore.dto.BookRequestDTO;
import com.interview.bookstore.dto.BookResponseDTO;
import com.interview.bookstore.entity.Book;
import com.interview.bookstore.service.IBookService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/book")
@Slf4j
public class BookController {

    private final IBookService bookService;

    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> getAllBooks() {
        List<BookResponseDTO> allBooks = bookService.getAllBooks();
        return (ResponseEntity<List<BookResponseDTO>>) ResponseEntity
                .ok(allBooks).getBody();
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookResponseDTO getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookResponseDTO addBook(@RequestBody BookRequestDTO book) {
        return bookService.addBook(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDTO> updateBook(@PathVariable Long id, @RequestBody BookRequestDTO updatedBook) {
        BookResponseDTO bookResponseDTO = bookService.updateBook(id, updatedBook);
        ResponseEntity<BookResponseDTO> ok = ResponseEntity.ok(bookResponseDTO);
        return ok;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>("deletion successful with id = " + id, HttpStatus.OK);
    }
}
