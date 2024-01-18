package com.interview.bookstore.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookConstant {


    @Value("#{'${cors.allowed-methods}'.split(',')}")
    public List<String> allowedMethods;
    @Value("#{'${cors.allowed-headers}'.split(',')}")
    public List<String> allowedHeaders;
    public static final String LIST_FETCHING_ERROR = "error getting list of books. Please contact customer care";
    public static final String BOOK_ID_NOT_FOUND = "book with id [%s] not found: ";
    public static final String SERVER_ERROR = "Internal server error";
    public static final String REQUEST_VALIDATION_ERROR = "request error occurred. Please try again";
    public static final String DELETE_SUCCESS = "deletion successful with id = ";

}
