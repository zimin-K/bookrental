package com.study.bookrental.repository;

import com.study.bookrental.entity.Rental;
import com.study.bookrental.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findByUser(User user);
}
