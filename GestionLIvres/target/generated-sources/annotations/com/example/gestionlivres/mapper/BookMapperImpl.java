package com.example.gestionlivres.mapper;

import com.example.gestionlivres.entity.Book;
import com.example.gestionlivres.entity.dto.BookDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-26T12:19:39+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class BookMapperImpl implements BookMapper {

    @Override
    public BookDto toDto(Book book) {
        if ( book == null ) {
            return null;
        }

        BookDto.BookDtoBuilder bookDto = BookDto.builder();

        bookDto.title( book.getTitle() );
        bookDto.author( book.getAuthor() );
        bookDto.description( book.getDescription() );

        return bookDto.build();
    }

    @Override
    public Book toEntity(BookDto bookDto) {
        if ( bookDto == null ) {
            return null;
        }

        Book.BookBuilder book = Book.builder();

        book.title( bookDto.getTitle() );
        book.author( bookDto.getAuthor() );
        book.description( bookDto.getDescription() );

        return book.build();
    }
}
