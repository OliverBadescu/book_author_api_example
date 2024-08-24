package mycode.book_author_example.app.view;


import mycode.book_author_example.app.model.Author;
import mycode.book_author_example.app.model.Book;
import mycode.book_author_example.app.repository.AuthorRepository;
import mycode.book_author_example.app.repository.BookRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class View {

    private AuthorRepository authorRepository;
    private BookRepository bookRepository;
    private Scanner scanner;

    public View(AuthorRepository authorRepository, BookRepository bookRepository){
        this.authorRepository =authorRepository;
        this.bookRepository = bookRepository;
        this.scanner = new Scanner(System.in);
        play();
    }


    private void meniu(){


        System.out.println("Books:");
        System.out.println("1. Add book");
        System.out.println("2. Delete book");
        System.out.println("3. Update book");
        System.out.println("4. Show books");

        System.out.println("Authors:");
        System.out.println("5. Show the authors of a book");
        System.out.println("6. Add a new author");
        System.out.println("7. Delete a author");

        System.out.println("8. Show book with most authors");




    }
    private void play(){

        boolean running = true;

        while (running) {
            meniu();
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    deleteBook();
                    break;
                case 3:
                    updateBook();
                    break;
                case 4:
                    showBooks();
                    break;
                case 5:
                    showAuthorsOfBook();
                    break;
                case 6:
                    addAuthor();
                    break;
                case 7:
                    deleteAuthor();
                    break;
                case 8:
                    showBookWithMostAuthors();
                    break;
                default:
                    System.out.println("Wrong input");
            }

        }

    }

    private void addBook(){

        System.out.println("Enter name: ");
        String name = scanner.nextLine();

        Book book = Book.builder().bookName(name).createdAt(LocalDate.now()).build();

        bookRepository.saveAndFlush(book);


    }

    private void deleteBook(){

        System.out.println("Book name: ");
        String name = scanner.nextLine();

        Optional<Book> book = bookRepository.findByBookName(name);

        book.ifPresent(book1 -> bookRepository.delete(book1));
    }

    private void updateBook(){

        System.out.println("Book name: ");
        String name = scanner.nextLine();

        Optional<Book> book = bookRepository.findByBookName(name);

        System.out.println("New name: ");
        String nou = scanner.nextLine();

        if(book.isPresent()){
            book.get().setBookName(nou);
            bookRepository.saveAndFlush(book.get());
        }

    }

    private void showBooks(){

        List<Book> list = bookRepository.findAll();

        list.forEach(System.out::println);

    }

    private void showAuthorsOfBook(){

        System.out.println("Book name:");
        String name = scanner.nextLine();

        Optional<Book> book = bookRepository.findByBookName(name);

        book.ifPresent(book1 -> System.out.println(book1.getAuthors()));
    }

    private void addAuthor(){

        System.out.println("Book name:");
        String name = scanner.nextLine();

        Optional<Book> book = bookRepository.findByBookName(name);

        if(book.isPresent()) {
            System.out.println("Add a author");
            System.out.println("First name: ");
            String fName = scanner.nextLine();
            System.out.println("Last name; ");
            String lName = scanner.nextLine();
            System.out.println("Age: ");
            int age = Integer.parseInt(scanner.nextLine());
            System.out.println("Email: ");
            String email = scanner.nextLine();

            Author author = Author.builder().firstName(fName).lastName(lName).age(age).email(email).build();
            book.get().addAuthor(author);
            bookRepository.saveAndFlush(book.get());
        }
    }

    private void deleteAuthor(){

        System.out.println("First name: ");
        String fName = scanner.nextLine();
        System.out.println("Last name; ");
        String lName = scanner.nextLine();

        Optional<Author> author= authorRepository.findByFirstNameAndLastName(fName,lName);

        author.ifPresent(author1 -> authorRepository.delete(author1));


    }

    private void showBookWithMostAuthors(){

        Optional<List<Book>> list = bookRepository.bookWithMostAuthors();

        list.ifPresent(books -> System.out.println(books.get(0)));
    }

}
