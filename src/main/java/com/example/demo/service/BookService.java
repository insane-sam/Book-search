package com.example.demo.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.exception.BookNotFoundException;
import com.example.demo.exception.NoBookFoundBetweenPriceException;
import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {

        this.bookRepository = bookRepository;
    }

    // addBook(Book book)
    public Book adBook(Book book) {
        Book saved = bookRepository.save(book);
        //System.out.println("Saved book: " + saved.getTitle() + " by " + saved.getAuthor());
        return saved;
    }

    // getAllBooks()
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // getBookById(Long id)
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not find with id : " + id));
    }

    // get book by author name
    public List<Book> getBookbyAuthor(String name) {
        List<Book> list = bookRepository.findByAuthor(name);
        if (list.isEmpty()) {
            throw new BookNotFoundException("No book is written by this author");
        }
        return list;
    }

    // updateBook(Long id, Book book)
    @Transactional
    public Book updateBook(Long id, Book updatedBook) {
        return bookRepository.findById(id)
                .map(book -> {
                    book.setTitle(updatedBook.getTitle());
                    book.setAuthor(updatedBook.getAuthor());
                    book.setPrice(updatedBook.getPrice());
                    return book;
                })
                .orElse(null);
    }

    // deleteBook(Long id)
    public void deleteBook(long id) {
        bookRepository.deleteById(id);
    }

    // return books with price less than 1000.
    public List<String> getPriceLess(int price) {
        List<Book> books = bookRepository.findByPriceLessThan(price);
        return books.stream().map(Book::getTitle).toList();
    }

    // RETURN LIST OF BOOKS WITH PRICE BETWEEN MIN AND MAX
    public List<String> getPriceMinMax(int min, int Max) {
        List<Book> list = bookRepository.findByPriceBetween(min, Max);
        if (list.isEmpty()) {
            throw new NoBookFoundBetweenPriceException("No book found between given prices");
        }
        return list.stream().map(Book::getTitle).toList();
    }

    // RETURN A BOOK WITH GIVEN AUTHOR AND TITLE
    public Book getBookByAuthorTitle(String title, String author) {
        return bookRepository.findByTitleAndAuthor(title, author)
                .orElseThrow(() -> new BookNotFoundException("No such book found by author" + author));
    }

    // GET BOOK BY PAGE NUMBER AND SORTING

    // ->//Pageable is a Spring Data interface that tells your repository how to
    // fetch data —
    // like which page, how many items per page, and how to sort them.
    // It doesn’t fetch data itself — it’s just the instructions.

    // ->//PageRequest is a class that implements Pageable.
    // It builds that instruction object for you.

    public Page<Book> getPages(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pagable = PageRequest.of(page, size, sort);
        return bookRepository.findAll(pagable);
    }
}
