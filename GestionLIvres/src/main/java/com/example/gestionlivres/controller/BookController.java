package com.example.gestionlivres.controller;

import com.example.gestionlivres.entity.dto.BookDto;
import com.example.gestionlivres.entity.dto.UpdateBookDto;
import com.example.gestionlivres.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework. web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService bookservice;


    @PostMapping("/addBook")
    public ResponseEntity<String> addBook(@RequestBody BookDto bookDto) {
        bookservice.createBook(bookDto);
        return new ResponseEntity<>("successfully created book", HttpStatus.CREATED);
    }

    @GetMapping("/findAllBook")
    public ResponseEntity <List<BookDto>> findAllBook() {
        try {
            List<BookDto> bookDtos = bookservice.findAllBook();
            return new ResponseEntity<>(bookDtos, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<BookDto> findById(@PathVariable Integer id) {

        try {
            BookDto bookDto = bookservice.findById(id);
            return new ResponseEntity<>(bookDto, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/updateBook/{id}")
    public ResponseEntity<String> updateBook(@PathVariable Integer id, @RequestBody BookDto bookDto) {

        try {
            bookservice.update(id, bookDto);
            return new ResponseEntity<>("successfully updated book", HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping ("/updateBookByParams/{id}")
    public ResponseEntity<String> updateDescriptionBook(@PathVariable Integer id, @RequestBody UpdateBookDto updateBookDto) {
        try {
            bookservice.setByUpdateBook(id, updateBookDto);
            return new ResponseEntity<>( "successfully updated by params book" ,HttpStatus.OK );
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteBook/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Integer id) {
        try {
            bookservice.deleteBook(id);
            return new ResponseEntity<>("successfully deleted book", HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
