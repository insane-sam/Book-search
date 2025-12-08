package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Book;
import com.example.demo.service.BookService;

import jakarta.validation.Valid;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService bookservice;

    public BookController(BookService bookService) {
        this.bookservice = bookService;
    }

    // add book
    @PostMapping("/addbook")
    public Book addBook(@Valid @RequestBody Book book) {
        // System.out.println("Received: " + book.getTitle() + " by " +
        // book.getAuthor());
        return bookservice.adBook(book);
    }

    // get all book
    @GetMapping("/getAll")
    public List<Book> getBook() {
        return bookservice.getAllBooks();
    }

    // get book by id
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {

        return bookservice.getBookById(id);
    }

    // get book by author
    @GetMapping("/author/search")
    public List<Book> getBookbyAuthor(@RequestParam("name") String author) {
        return bookservice.getBookbyAuthor(author);
    }

    // update book
    @PutMapping("/path/{id}")
    public Book putMethodName(@PathVariable Long id, @RequestBody Book book) {
        return bookservice.updateBook(id, book);
    }

    // delete book
    @DeleteMapping("/id/{id}")
    public String deleteBook(@PathVariable long id) {
        bookservice.deleteBook(id);
        return "Book deleted successfully";
    }

    // this will return book with price less than;
    @GetMapping("/price/{price}")
    public List<String> getBooksCheaperThan(@PathVariable int price) {
        return bookservice.getPriceLess(price);
    }

    // RETURN LIST OF BOOKS WITH PRICE BETWEEN MIN AND MAX
    @GetMapping("/search")
    public List<String> getBookByPriceMinMax(@RequestParam("min") int min, @RequestParam("max") int max) {
        return bookservice.getPriceMinMax(min, max);
    }

    // RETURN A BOOK WITH GIVEN AUTHOR AND TITLE
    @GetMapping("/search/filter")
    public Book getBookByAuthorandTitle(@RequestParam("title") String title, @RequestParam("author") String author) {
        return bookservice.getBookByAuthorTitle(title, author);
    }

    // public List<Book> searchBooks(
    // @RequestParam(required = false, value = "title") String title,
    // @RequestParam(required = false, value = "author") String author,
    // @RequestParam(required = false, value = "min") String min,
    // @RequestParam(required = false, value = "max") String max) {

    // }
    @GetMapping("/search/page")
    public Page<Book> searchBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        return bookservice.getPages(page, size, sortBy, sortDir);
    }
}
