package mycode.book_author_example.app.repository;

import mycode.book_author_example.app.model.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {

    @EntityGraph(attributePaths = {"authors"}, type = EntityGraph.EntityGraphType.FETCH)
    Optional<Book> findByBookName(String name);

    @EntityGraph(attributePaths = {"authors"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT b FROM Book b where b.id in(select a.book.id from Author a  group by a.book.id having COUNT(a) = (select MAX(author_count) from (select count(a1) as author_count from Author a1 group by a1.book.id) as subquery))")
    Optional<List<Book>> bookWithMostAuthors();
}
