package com.interview.bookstore.controller;

import com.interview.bookstore.dto.BookRequestDTO;
import com.interview.bookstore.dto.BookResponseDTO;
import com.interview.bookstore.service.IBookService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.interview.bookstore.constant.BookConstant.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
@Slf4j
public class BookController {

    private final IBookService bookService;

    @GetMapping("/book")
    @PreAuthorize("hasRole('USER)")
    public ResponseEntity<List<BookResponseDTO>> getAllBooks() {
        List<BookResponseDTO> allBooks = bookService.getAllBooks();
        return ResponseEntity
                .ok(allBooks);
    }

    @GetMapping("/public")
    public String publicEnd() {
        return "you can view";
    }
    @GetMapping("/public/this")
    public String publicEndThis() {
        return "you can view this ";
    }

    @GetMapping("/book/{id}")
    @PreAuthorize("hasRole('USER)")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getBookById(@PathVariable Long id) {
        try {
            BookResponseDTO book = bookService.getBookById(id);
            if (book != null) {
                return ResponseEntity.ok(book);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BOOK_ID_NOT_FOUND);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(SERVER_ERROR);
        }
    }

    @PostMapping("/book")
    @PreAuthorize("hasRole('USER)")
    @ResponseStatus(HttpStatus.CREATED)
    public BookResponseDTO addBook(@RequestBody BookRequestDTO book) {
        return bookService.addBook(book);
    }

    @PutMapping("/book/{id}")
    @PreAuthorize("hasRole('USER)")
    public ResponseEntity<BookResponseDTO> updateBook(@PathVariable Long id, @RequestBody BookRequestDTO updatedBook) {
        BookResponseDTO bookResponseDTO = bookService.updateBook(id, updatedBook);
        ResponseEntity<BookResponseDTO> ok = ResponseEntity.ok(bookResponseDTO);
        return ok;
    }

    @DeleteMapping("/book/{id}")
    @PreAuthorize("hasRole('ADMIN)")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(DELETE_SUCCESS + id, HttpStatus.OK);
    }
}
