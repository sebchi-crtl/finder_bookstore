package com.interview.bookstore.dao;

import com.interview.bookstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookStoreRepo extends JpaRepository<Book, Long> {
}
