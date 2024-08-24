package mycode.book_author_example;

import mycode.book_author_example.app.model.Author;
import mycode.book_author_example.app.model.Book;
import mycode.book_author_example.app.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class BookAuthorExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookAuthorExampleApplication.class, args);
    }


    @Bean
    CommandLineRunner commandLineRunner(BookRepository bookRepository){

        return args -> {

            Author author = Author.builder().firstName("Lucian").lastName("Blaga").age(56).email("test@gmail.com").build();

            Optional<Book> book = bookRepository.findByBookName("Luceafarul");

            book.get().addAuthor(author);
            bookRepository.saveAndFlush(book.get());
        };

    }

}
