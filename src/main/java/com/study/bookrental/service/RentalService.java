package com.study.bookrental.service;

import com.study.bookrental.entity.Book;
import com.study.bookrental.entity.Rental;
import com.study.bookrental.entity.User;
import com.study.bookrental.repository.BookRepository;
import com.study.bookrental.repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalRepository rentalRepository;
    private final BookRepository bookRepository;

    public void rentBook(User user, Book book) {
        if (!book.isAvailable()) return;

        Rental rental = Rental.builder()
                .user(user)
                .book(book)
                .rentalDate(LocalDateTime.now())
                .build();

        book.setAvailable(false);
        bookRepository.save(book);
        rentalRepository.save(rental);
    }

    public void returnBook(Long rentalId) {
        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new IllegalArgumentException("대여 기록이 존재하지 않습니다."));

        if (rental.getReturnDate() != null) {
            throw new IllegalStateException("이미 반납된 도서입니다.");
        }

        rental.setReturnDate(LocalDateTime.now());
        rental.getBook().setAvailable(true); // 도서 상태 사용 가능으로 변경

        rentalRepository.save(rental);
        bookRepository.save(rental.getBook());
    }

    public List<Rental> getUserRentals(User user) {
        return rentalRepository.findByUser(user);
    }
}
