package com.example.demo.repository;

import com.example.demo.model.Book;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    // /book/search

    // /book/search?author=James
    List<Book> findByAuthor(String author);

    List<Book> findByPriceLessThan(int price);

    // /book/search?minPrice=100&maxPrice=400
    List<Book> findByPriceBetween(int min, int max);

    // book/search?title=Spring Boot&author=John
    //select book from table where title="" and author=""
    Optional<Book> findByTitleAndAuthor(String title, String author);

}
