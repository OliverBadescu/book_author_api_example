package mycode.book_author_example.app.repository;

import mycode.book_author_example.app.model.Author;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

    Optional<Author> findByFirstNameAndLastName(String fName, String lName);


    Optional<List<Author>> authorWithMostBooksWrote();
}
