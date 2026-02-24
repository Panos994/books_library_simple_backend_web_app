package library.management.system.demo.services;

import library.management.system.demo.dto.UserRequestDTO;
import library.management.system.demo.entity.Role;
import library.management.system.demo.entity.User;
import library.management.system.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    //    @Autowired // ❌
    //    private UserRepository userRepository;
    private final UserRepository userRepository; //το final keyword θα μας κανει πιο ασφαλή το κώδικα μας, --> μας διασφαλίζει ότι το userRepository δεν πρόκειται να αλλάξει μετά την αρχικοποίηση του UserService, --> έτσι υπάρχει περισσότερη ασφάλεια αλλά και κώδικας είναι πιο κατανοητός (πχ καθώς δεν υπάρχει περίπτωση κάποια άλλη μέθοδος να κάνει κατά λάθος this.userRepository = null ή να το αντικαταστήσει).

    private final PasswordEncoder passwordEncoder;

    //Επίσης --> Με Field Injection (@Autowired στο πεδίο): Μπορείς να δημιουργήσεις ένα αντικείμενο new UserService() και το userRepository να είναι null, προκαλώντας crash στο runtime.
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public User registerUser(UserRequestDTO dto){
        User user = new User();
        String email = dto.getEmail();
        user.setEmail(email);
//        String password = dto.getPassword();
//        user.setPassword(password);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.ROLE_USER);

        return userRepository.save(user);
    }

    public Optional<User> findUserById(UUID userId){
//        Optional<User> user = userRepository.findById(userId);
//        return user;
        return userRepository.findById(userId);
    }

    public Optional<User> findUserByEmail(String email){
//        Optional<User> user = userRepository.findByEmail(email);
//        return user;
        return userRepository.findByEmail(email);
    }
}
