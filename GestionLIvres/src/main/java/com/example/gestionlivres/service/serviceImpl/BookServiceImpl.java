package com.example.gestionlivres.service.serviceImpl;

import com.example.gestionlivres.entity.Book;
import com.example.gestionlivres.entity.dto.BookDto;
import com.example.gestionlivres.entity.dto.UpdateBookDto;
import com.example.gestionlivres.mapper.BookMapper;
import com.example.gestionlivres.repository.BookRepository;
import com.example.gestionlivres.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final  BookRepository bookRepository;
    private final  BookMapper bookMapper;


    @Override
    public void createBook(BookDto bookDto) {
        Book bookEntity = bookMapper.toEntity(bookDto);
        bookRepository.save(bookEntity);
    }

    @Override
    public List<BookDto> findAllBook() {

        List<Book> books = bookRepository.findAll();

        if (books.isEmpty()) {
            throw new RuntimeException("No books found in the database");
        }
        return books.stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookDto findById(Integer id) {

        Optional<Book> book = bookRepository.findById(id);

        if (book.isPresent()) {
            return bookMapper.toDto(book.get());
        } else {
            throw new RuntimeException("This book does not exist");
        }
    }

    @Override
    public void update(Integer id, BookDto bookDto) {

        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {

            Book newBook = book.get();
            newBook.setTitle(bookDto.getTitle());
            newBook.setAuthor(bookDto.getAuthor());
            newBook.setDescription(bookDto.getDescription());
            bookRepository.save(book.get());
        }
        else {
            throw new RuntimeException("This book does not exist");
        }
    }

    @Override
    public void setByUpdateBook(Integer id, UpdateBookDto updateBookDto) {

        Optional<Book> book = bookRepository.findById(id);

        if (book.isPresent()) {
            Book newBook = book.get();
            if (updateBookDto.getTitle() != null) {
                newBook.setTitle(updateBookDto.getTitle());
            }
            if (updateBookDto.getDescription() != null) {
                newBook.setDescription(updateBookDto.getDescription());
            }
            bookRepository.save(newBook);
        } else {
            throw new RuntimeException("Book with ID " + id + " not found");
        }
    }

    @Override
    public void deleteBook(Integer id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            bookRepository.delete(book.get());
        }
        else{
            throw new RuntimeException("This book does not exist");
        }
    }

}
