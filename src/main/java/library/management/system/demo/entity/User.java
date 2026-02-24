package library.management.system.demo.entity;


import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="users")
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column
    private String fullName;

    @Column

    private String email;

    @Column

    private String password;

    @Enumerated(EnumType.STRING)
    @Column
    private Role role;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY, cascade = CascadeType.ALL) //sto mapped μπαινει αυτο που εχω mappαρει με το αλλο entity στο Book δηλαδη που εβαλα private User user
    private List<Book> books;


//    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinTable(name="user_books", joinColumns = @JoinColumn(name="user_id"), inverseJoinColumns = @JoinColumn(name="book_id"))
//    private Set<Book> bookOfUsers = new HashSet<>(); εξασκηση ManyToMany relationship
}
