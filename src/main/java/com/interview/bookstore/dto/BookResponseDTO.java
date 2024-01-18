package com.interview.bookstore.dto;

import java.time.LocalDateTime;

public record BookResponseDTO(
        Long id,
        String title,
        String author,
        boolean available,
        LocalDateTime dateCreated
) {


}
