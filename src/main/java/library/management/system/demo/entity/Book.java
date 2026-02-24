package library.management.system.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="books")
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column
    private String title;

    @ManyToOne
    private User user;

//    @ManyToMany(mappedBy = "users")
//    private Set<User> users = new HashSet<>(); εξασκηση ManyToMany relationship
}
