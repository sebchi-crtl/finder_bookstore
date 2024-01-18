package com.interview.bookstore.constant;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.*;

class BookConstantTest {

    @InjectMocks
    private BookConstant constant;

    @Test
    public void testAllowedMethods(){
        assertNotNull(constant.allowedMethods);
    }

    @Test
    public void testAllowedHeaders() {
        assertNotNull(constant.allowedHeaders);
    }

    @Test
    public void testConstants() {
        assertEquals("error getting list of books. Please contact customer care", BookConstant.LIST_FETCHING_ERROR);
        assertEquals("book with id [%s] not found: ", BookConstant.BOOK_ID_NOT_FOUND);
        assertEquals("Internal server error", BookConstant.SERVER_ERROR);
        assertEquals("request error occurred. Please try again", BookConstant.REQUEST_VALIDATION_ERROR);
        assertEquals("deletion successful with id = ", BookConstant.DELETE_SUCCESS);

    }

}