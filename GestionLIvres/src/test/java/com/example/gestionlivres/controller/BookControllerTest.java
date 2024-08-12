package com.example.gestionlivres.controller;

import com.example.gestionlivres.entity.dto.BookDto;
import com.example.gestionlivres.entity.dto.UpdateBookDto;
import com.example.gestionlivres.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static java.util.Arrays.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @MockBean
    private BookService bookService;

    @Autowired
    MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    @DisplayName("Create New Book")
    void addBookTest() throws Exception {

        //Given
        String title = "title";
        String author = "author";
        String description = "description";
        BookDto bookDto = BookDto.builder()
                .title(title)
                .author(author)
                .description(description)
                .build();

        String bookDtoJson = objectMapper.writeValueAsString(bookDto);

        doNothing().when(bookService).createBook(bookDto);

        //When
        ResultActions resultRequest = mockMvc.perform(
                post("/book/addBook")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookDtoJson)
        );

        //Then
        resultRequest.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string("successfully created book"));
    }

    @Test
    void findAllBook_1_Test() throws Exception {

        //Given
        BookDto bookDto1 = BookDto.builder()
                .title("title1")
                .author("author1")
                .description("description1")
                .build();
        BookDto bookDto2 = BookDto.builder()
                .title("title2")
                .author("author2")
                .description("description2")
                .build();

        List<BookDto> bookDtoList = asList(bookDto1, bookDto2);

        when(bookService.findAllBook()).thenReturn(bookDtoList);

        //When
        ResultActions resultRequest = mockMvc.perform(get("/book/findAllBook")
                .contentType(MediaType.APPLICATION_JSON));

        //Then
        resultRequest.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(bookDtoList.size()))
                .andExpect(jsonPath("$[0].title").value(bookDtoList.getFirst().getTitle()))
                .andExpect(jsonPath("$[0].author").value(bookDtoList.getFirst().getAuthor()))
                .andExpect(jsonPath("$[0].description").value(bookDtoList.getFirst().getDescription()))
                .andExpect(jsonPath("$[1].title").value(bookDtoList.get(1).getTitle()))
                .andExpect(jsonPath("$[1].author").value(bookDtoList.get(1).getAuthor()))
                .andExpect(jsonPath("$[1].description").value(bookDtoList.get(1).getDescription()));

    }

    @Test
    @DisplayName("Error To Fetch All Books")
    void findAllBook_2_Test() throws Exception {

        //Given
        when(bookService.findAllBook()).thenThrow(new RuntimeException("Book not found"));

        //When
        ResultActions resultActions = mockMvc.perform(get("/book/findAllBook")
                .contentType(MediaType.APPLICATION_JSON));

        //Then
        resultActions.andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Successful Fetch Book By Id")
    void findById_1_Test() throws Exception {

        //Given
        BookDto bookDto = BookDto.builder()
                .title("title1")
                .author("author1")
                .description("description1")
                .build();
        Integer idBook = 1;

        when(bookService.findById(idBook)).thenReturn(bookDto);

        //When
        ResultActions resultAction = mockMvc.perform(get("/book/findById/{id}", idBook)
                .contentType(MediaType.APPLICATION_JSON));

        //Then
        resultAction.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(bookDto.getTitle()))
                .andExpect(jsonPath("$.author").value(bookDto.getAuthor()))
                .andExpect(jsonPath("$.description").value(bookDto.getDescription()));
    }

    @Test
    @DisplayName("Error To Fetch Book Because Id Not Found")
    void findById_2_Test() throws Exception {

        //Given
        Integer idBook = 2;
        when(bookService.findById(idBook)).thenThrow(new RuntimeException("Book not found"));

        //When
        ResultActions resul = mockMvc.perform(get("/book/findById/{id}", idBook)
                .contentType(MediaType.APPLICATION_JSON));

        //Then
        resul.andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Successful Update Book")
    void updateBook_1_Test() throws Exception {

        //Given
        Integer idBook = 1;

        BookDto bookDto = mock(BookDto.class);
        String bookDtoJson = objectMapper.writeValueAsString(bookDto);

        doNothing().when(bookService).update(idBook, bookDto);
        //When
        ResultActions result = mockMvc.perform(
                put("/book/updateBook/{id}", idBook)
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookDtoJson));
        //Then
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("successfully updated book"));
    }

    @Test
    @DisplayName("Error To Update Book Because Book Not Found")
    void updateBook_2_Test() throws Exception {

        Integer idBook = 1;
        BookDto bookDto = mock(BookDto.class);
        String bookDtoJson = objectMapper.writeValueAsString(bookDto);

        doThrow(new RuntimeException("Book not found")).when(bookService).update(idBook, bookDto);

        ResultActions result = mockMvc.perform(put("/book/updateBook/", idBook)
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookDtoJson));

        result.andDo(print())
                .andExpect(status().isNotFound());
    }@Test
    @DisplayName("Successful Update Book By Params")
    void updateBookByParams_1_Test() throws Exception {

        //Given
        Integer idBook = 1;

        UpdateBookDto bookDto = mock(UpdateBookDto.class);
        String bookDtoJson = objectMapper.writeValueAsString(bookDto);

        doNothing().when(bookService).setByUpdateBook(idBook, bookDto);

        //When
        ResultActions result = mockMvc.perform(
                put("/book/updateBookByParams/{id}", idBook)
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookDtoJson));
        //Then
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("successfully updated by params book"));
    }

    @Test
    @DisplayName("Error To Update Book Because Book Not Found")
    void updateBookByParams_2_Test() throws Exception {

        Integer idBook = 1;
        UpdateBookDto bookDto = mock(UpdateBookDto.class);
        String bookDtoJson = objectMapper.writeValueAsString(bookDto);

        doThrow(new RuntimeException("Book not found")).when(bookService).setByUpdateBook(idBook, bookDto);

        ResultActions result = mockMvc.perform(put("/book/updateBookByParams/", idBook)
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookDtoJson));

        result.andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Successful Delete Book")
    void deleteBook_1_Test() throws Exception {

        Integer idBook = 1;

        doNothing().when(bookService).deleteBook(idBook);

        ResultActions result = mockMvc.perform(delete("/book/deleteBook/{id}", idBook)
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(content().string("successfully deleted book"));
    }

    @Test
    @DisplayName("error To Delete Book")
    void deleteBook_2_Test() throws Exception {

        Integer idBook = 1;

        doThrow(new RuntimeException("Book Not Found")).when(bookService).deleteBook(idBook);

      mockMvc.perform(delete("/book/deleteBook/{id}", idBook)
                .contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isNotFound());


    }
}