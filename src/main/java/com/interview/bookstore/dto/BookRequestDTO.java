package com.interview.bookstore.dto;

public record BookRequestDTO(
        String title,
        String author,
        boolean available
) {
}
