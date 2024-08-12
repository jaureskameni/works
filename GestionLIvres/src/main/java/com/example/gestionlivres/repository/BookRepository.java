package com.example.gestionlivres.repository;

import com.example.gestionlivres.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
