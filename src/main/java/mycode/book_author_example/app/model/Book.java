package mycode.book_author_example.app.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static jakarta.persistence.GenerationType.SEQUENCE;

@AllArgsConstructor
@ToString
@NoArgsConstructor
@Data
@Getter
@Setter
@Builder
@Table(name = "book")
@Entity(name = "Book")
public class Book {

    @Id
    @SequenceGenerator(
            name="book_sequence",
            sequenceName = "book_sequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "book_sequence"
    )

    @Column(
            name = "id"
    )
    private int id;


    @Column(
            name = "book_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String bookName;

    @Column(
            name = "created_at",
            nullable = false,
            columnDefinition = "DATE"
    )
    private LocalDate createdAt;

    @OneToMany(mappedBy ="book",fetch = FetchType.LAZY,cascade = CascadeType.ALL ,orphanRemoval = true)
    @Builder.Default
        @ToString.Exclude
    private Set<Author> authors= new HashSet<>();


    public void addAuthor(Author author){
        this.authors.add(author);
        author.setBook(this);
    }

    public void deleteAuthor(Author author){
        this.authors.remove(author);

        author.setBook(null);
    }
}
