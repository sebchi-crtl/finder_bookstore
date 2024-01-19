package com.interview.bookstore.service;

import com.interview.bookstore.dao.BookStoreRepo;
import com.interview.bookstore.dto.BookRequestDTO;
import com.interview.bookstore.dto.BookResponseDTO;
import com.interview.bookstore.entity.Book;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.interview.bookstore.entity.Book.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@Slf4j
@SpringBootTest
class BookServiceTest {
    @Mock
    private BookStoreRepo bookStoreRepo;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void getAllBooks() {
        List<Book> mockBooks = Arrays.asList(

                builder()
                    .id(1L)
                    .title("Book 1")
                    .author("Author1")
                    .available(true)
                    .build(),
                builder()
                        .id(2L)
                        .title("Book 2")
                        .author("Author 2")
                        .available(true)
                        .build(),
                builder()
                        .id(3L)
                        .title("Book 3")
                        .author("Author 3")
                        .available(false)
                        .build()
        );

        when(bookStoreRepo.findAll()).thenReturn(mockBooks);

        List<BookResponseDTO> result = bookService.getAllBooks();

        assertEquals(mockBooks, result);
        assertEquals(3, result.size());
        assertEquals("Book 1", result.get(0).title());
        assertEquals("Author 2", result.get(1).author());
        assertEquals(false, result.get(2).available());
    }

    @Test
    void getBookById() {
        Long id = 1L;
        Book mockBook = builder()
                .id(id)
                .title("Book 1")
                .author("Author1")
                .available(true)
                .build();

        when(bookStoreRepo.findById(id)).thenReturn(Optional.of(mockBook));

        BookResponseDTO result = bookService.getBookById(id);

        assertNotNull(result);
        assertEquals("Author 1", result.author());
    }

    @Test
    void addBook() {
        BookRequestDTO requestDTO = new BookRequestDTO("New Book", "New Author", true);
        Book newBook = builder()
                .id(null)
                .title("New Book")
                .author("New Author")
                .available(true)
                .dateCreated(null)
                .build();
        Book savedBook = builder()
                .id(1L)
                .title("New Book")
                .author("New Author")
                .available(true)
                .dateCreated(LocalDateTime.now())
                .build();

        when(bookStoreRepo.save(Mockito.any(Book.class))).thenReturn(savedBook);

        BookResponseDTO result = bookService.addBook(requestDTO);

        assertNotNull(result);
        assertEquals(1L, result.id().longValue());
        assertEquals("New Book", result.title());
        assertEquals("New Author", result.author());
        assertTrue(result.available());
    }

    @Test
    void updateBook() {
        long id = 1L;
        BookRequestDTO requestDTO = new BookRequestDTO("Updated Book", "Updated Author", false);
        Book existingBook = builder()
                .id(id)
                .title("Old Book")
                .author("Old Author")
                .available(true)
                .dateCreated(LocalDateTime.now())
                .build();
        Book updatedBook = builder()
                .id(id)
                .title("Update Book")
                .author("update Author")
                .available(false)
                .dateCreated(LocalDateTime.now())
                .build();

        when(bookStoreRepo.findById(id)).thenReturn(Optional.of(existingBook));
        when(bookStoreRepo.save(Mockito.any(Book.class))).thenReturn(updatedBook);

        BookResponseDTO result = bookService.updateBook(id, requestDTO);

        assertNotNull(result);
        assertEquals(id, result.id().longValue());
        assertEquals("Updated Book", result.title());
        assertEquals("Updated Author", result.author());
        assertFalse(result.available());
    }

    @Test
    void deleteBook() {
        Long bookId = 1L;

        when(bookStoreRepo.existsById(bookId)).thenReturn(true);

        bookService.deleteBook(bookId);

        Mockito.verify(bookStoreRepo, Mockito.times(1)).deleteById(bookId);
    }
}