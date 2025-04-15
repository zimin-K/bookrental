package com.study.bookrental.service;

import com.study.bookrental.entity.Book;
import com.study.bookrental.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public void save(Book book) {
        book.setAvailable(true); // 기본값
        bookRepository.save(book);
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public List<Book> search(String keyword) {
        return bookRepository.findByTitleContaining(keyword);
    }

    public Book findById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }
}
