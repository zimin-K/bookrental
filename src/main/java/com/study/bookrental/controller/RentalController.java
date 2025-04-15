package com.study.bookrental.controller;

import com.study.bookrental.entity.Book;
import com.study.bookrental.entity.Rental;
import com.study.bookrental.entity.User;
import com.study.bookrental.service.BookService;
import com.study.bookrental.service.RentalService;
import com.study.bookrental.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/rentals")
public class RentalController {

    private final RentalService rentalService;
    private final BookService bookService;
    private final UserService userService;

    @PostMapping("/rent/{bookId}")
    public String rent(@PathVariable Long bookId,
                       @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        Book book = bookService.findById(bookId);
        rentalService.rentBook(user, book);
        return "redirect:/books";
    }

    @PostMapping("/{id}/return")
    public String returnBook(@PathVariable Long id) {
        rentalService.returnBook(id);
        return "redirect:/rentals/my";
    }


    @GetMapping("/my")
    public String myRentals(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findByUsername(userDetails.getUsername());
        List<Rental> rentals = rentalService.getUserRentals(user);
        model.addAttribute("rentals", rentals);
        return "rental/my";
    }
}
