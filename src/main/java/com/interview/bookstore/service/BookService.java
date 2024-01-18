package com.interview.bookstore.service;

import com.interview.bookstore.dao.BookStoreRepo;
import com.interview.bookstore.dto.BookRequestDTO;
import com.interview.bookstore.dto.BookResponseDTO;
import com.interview.bookstore.entity.Book;
import com.interview.bookstore.exception.BookNotFoundException;
import com.interview.bookstore.exception.RequestValidationException;
import com.interview.bookstore.mapper.BookMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class BookService implements IBookService{

    private final BookStoreRepo bookStoreRepo;
    private final BookMapper mapper;

    @Override
    public List<BookResponseDTO> getAllBooks() {
        try{
            return bookStoreRepo.findAll()
                    .stream()
                    .map(mapper)
                    .collect(Collectors.toList());
        }
        catch (Exception ex){
            log.error("error fetching" );
            log.error(ex.getMessage());
            throw new BookNotFoundException("error getting list of books " );
        }

    }

    @Override
    public BookResponseDTO getBookById(Long id) {

        Book book = bookStoreRepo.findById(id)
                .orElseThrow(() -> new BookNotFoundException(
                        "book with id [%s] not found: ".formatted(id)));
        return mapper.apply(book);
    }

    @Override
    public BookResponseDTO addBook(BookRequestDTO book) {
        try{

            Book newBook = Book
                    .builder()
                    .title(book.title())
                    .author(book.author())
                    .available(book.available())
                    .dateCreated(LocalDateTime.now())
                    .build();
            Book saveBook = bookStoreRepo.save(newBook);
            return mapper.apply(saveBook);
        }
        catch (Exception ex) {
            log.error(ex.getMessage());
            throw new RequestValidationException("An error occurred. Please try again");
        }
    }

    @Override
    public BookResponseDTO updateBook(Long id, BookRequestDTO request) {
        try{

            Book bookId = bookStoreRepo.findById(id)
                    .orElseThrow(
                            () -> new RequestValidationException("An error occurred. Please try again")
                    );

            Book updateBook = Book
                    .builder()
                    .id(id)
                    .title(request.title() != null ? request.title() : bookId.getTitle())
                    .author(request.author() !=null ? request.author() : bookId.getAuthor())
                    .available(!request.available() ? bookId.isAvailable() : bookId.isAvailable())
                    .dateCreated(bookId.getDateCreated())
                    .build();

            Book saveBook = bookStoreRepo.save(updateBook);
            return mapper.apply(saveBook);
        }
        catch (Exception ex) {
            log.error(ex.getMessage());
            throw new BookNotFoundException("An error occurred. Please try again");
        }
    }

    @Override
    public void deleteBook(Long id) {
        bookStoreRepo.deleteById(id);
    }
}
