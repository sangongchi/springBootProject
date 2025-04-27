package org.example.springbootdemo.service;

import org.springframework.stereotype.Service;

@Service
public class BookService {
    public String getBookById(Long id){
        return "123456"+id;
    }
}
