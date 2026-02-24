package library.management.system.demo.services;

import library.management.system.demo.dto.CreateBookRequestDTO;
import library.management.system.demo.entity.Book;
import library.management.system.demo.entity.User;
import library.management.system.demo.repository.BookRepository;
import library.management.system.demo.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public BookService(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public Book createBook(CreateBookRequestDTO dto, UUID userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setUser(user);
        return bookRepository.save(book);
    }

    public Page<Book> findAllBooks(Pageable pageable){
        return bookRepository.findAll(pageable);
    }

    public Page<Book> findBooksByUser(UUID userId, Pageable pageable){
        return bookRepository.findByUser_Id(userId, pageable);
    }
}
