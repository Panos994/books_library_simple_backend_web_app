package library.management.system.demo.controllers;

import jakarta.validation.Valid;
import library.management.system.demo.dto.BookCreationResponseDTO;
import library.management.system.demo.dto.CreateBookRequestDTO;
import library.management.system.demo.dto.PageResponseDTO;
import library.management.system.demo.entity.Book;
import library.management.system.demo.services.BookService;
import library.management.system.demo.utils.PageMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;


    public BookController(BookService bookService) {
        this.bookService = bookService;

    }

    @PreAuthorize("hasRole('LIBRARY_EMPLOYEE')")
    @PostMapping("/create/{userId}")
    public ResponseEntity<BookCreationResponseDTO> createBook(@Valid @RequestBody CreateBookRequestDTO createBookRequestDTO, @PathVariable UUID userId) {
        Book book = bookService.createBook(createBookRequestDTO, userId);

        BookCreationResponseDTO creationResponseDTO = BookCreationResponseDTO.builder().title(book.getTitle())
                .userId(book.getUser().getId()).build();

        return ResponseEntity.status(HttpStatus.CREATED).body(creationResponseDTO);
    }

//    @GetMapping πριν φτιαξω wrapper dto
//    public ResponseEntity<Page<BookCreationResponseDTO>> getAllBooks(Pageable pageable) {
//        Page<BookCreationResponseDTO> response = bookService.findAllBooks(pageable).map(this::mapToResponse);
//        return ResponseEntity.ok(response);
//    }

    @GetMapping
    public ResponseEntity<PageResponseDTO<BookCreationResponseDTO>> getAllBooks(Pageable pageable) {
        Page<BookCreationResponseDTO> dtoPage = bookService.findAllBooks(pageable).map(this::mapToResponse);
        PageResponseDTO<BookCreationResponseDTO> response = PageMapper.toResponse(dtoPage);
        return ResponseEntity.ok(response);
    }








    //    @GetMapping("/user/{userId}") //χωρις pagination ηταν
//    public ResponseEntity<List<BookCreationResponseDTO>> getUserBooks(@PathVariable UUID userId){
//       List<BookCreationResponseDTO> response = bookService.findBooksByUser(userId)
//               .stream()
//               .map(this::mapToResponse)
//               .toList();
//       return ResponseEntity.ok(response);
//    }

//    @GetMapping("/user/{userId}") //πριν βαλω τον wrapper
//    public ResponseEntity<Page<BookCreationResponseDTO>> getUserBooks(@PathVariable UUID userId, Pageable pageable) {
//        Page<BookCreationResponseDTO> response = bookService.findBooksByUser(userId, pageable).map(this::mapToResponse);
//        return ResponseEntity.ok(response);
//    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<PageResponseDTO<BookCreationResponseDTO>> getUserBooks(@PathVariable UUID userId, Pageable pageable) {
        Page<BookCreationResponseDTO> dtoPage = bookService.findBooksByUser(userId, pageable).map(this::mapToResponse);
        PageResponseDTO<BookCreationResponseDTO> response = PageMapper.toResponse(dtoPage);
        return ResponseEntity.ok(response);
    }

    //helper method mapToResponse
    private BookCreationResponseDTO mapToResponse(Book book) {
        return BookCreationResponseDTO.builder()
                .title(book.getTitle())
                .userId(book.getUser().getId())
                .build();
    }

}
