package library.management.system.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDTO {
    @NotNull
    @NotBlank(message = "email is required")
    @Email
    private String email;
    @NotNull
    @NotNull
    @NotBlank(message = "password is required")
    private String password;
}
