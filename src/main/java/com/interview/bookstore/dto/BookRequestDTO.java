package com.interview.bookstore.dto;

public record BookRequestDTO(
        String title,
        String author,
        boolean available
) {
    public static BookRequestDTO of(String title, String author, boolean available) {
        return null;
    }
}
