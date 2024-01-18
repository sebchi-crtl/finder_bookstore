package com.interview.bookstore.dto;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
class BookRequestDTOTest {
    @BeforeAll
    static void setup() {
        log.info("@BeforeAll - executes once before all test methods in this class");
    }

    @BeforeEach
    void init() {
        log.info("@BeforeEach - executes before each test method in this class");
    }


    @Test
    @DisplayName("Single test successful")
    void testConstructorAndGetters() {
        String title = "Book 1";
        String author = "Author 1";
        boolean available = true;

        BookRequestDTO request = new BookRequestDTO(
                title,
                author,
                available
        );

        assertEquals(title, request.title());
        assertEquals(author, request.author());
        assertEquals(available, request.available());
    }

    @Test
    @DisplayName("test if String")
    void testToString() {
        String title = "Book 1";
        String author = "Author 1";
        boolean available = true;

        BookRequestDTO request = new BookRequestDTO(
                title,
                author,
                available
        );

        String result = request.toString();

        // Assert
        String expected = "BookRequestDTO" +
                "[title=" + title +
                ", author=" + author +
                ", available=" + available +
                "]";
        assertEquals(expected, result);
    }


    @Test
    @DisplayName("Test the book request")
    public void testBookRequestDto() {
        BookRequestDTO request = new BookRequestDTO(
                "Book 1",
                "Author 1",
                true
        );

        assertEquals("Book 1", request.title());
        assertEquals("Author 1", request.author());
        assertEquals(true, request.available());
    }

    @Test
    @DisplayName("Test equals and hash code")
    void testEqualsAndHashCode() {
        String title = "Book 1";
        String author = "Author 1";
        boolean available = true;

        BookRequestDTO request1 = new BookRequestDTO(
                title,
                author,
                available
        );

        String title2 = "Book 1";
        String author2 = "Author 1";
        boolean available2 = true;

        BookRequestDTO request2 = new BookRequestDTO(
                title2,
                author2,
                available2
        );

        String title3 = "Book 2";
        String author3 = "Author 2";
        boolean available3 = false;

        BookRequestDTO request3 = new BookRequestDTO(
                title3,
                author3,
                available3
        );

        assertEquals(request1, request2);
        assertEquals(request1.hashCode(), request2.hashCode());
        assertEquals(request1, request1);
        assertEquals(request1.hashCode(), request1.hashCode());
        assertEquals(request1.equals(request3), false);
        assertEquals(request1.hashCode() == request3.hashCode(), false);
        assertEquals(request1.equals(null), false);
        assertEquals(request1.equals(new Object()), false);
    }

    @Test
    @DisplayName("test static mockito")
    public void testMockitoStatic() {
        try (MockedStatic<BookRequestDTO> mockedRequest = Mockito.mockStatic(BookRequestDTO.class)) {
            mockedRequest.when(() -> BookRequestDTO.of("Book 1", "Author 1", true))
                    .thenReturn(new BookRequestDTO("Book 1", "Author 1", true));

            BookRequestDTO request = BookRequestDTO.of("Multree", "test@example.com", true);

            assertEquals("Book 1", request.title());
            assertEquals("Author 1", request.author());
            assertEquals(true, request.available());
        }
    }
}