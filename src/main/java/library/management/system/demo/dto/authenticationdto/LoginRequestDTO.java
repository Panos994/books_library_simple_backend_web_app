package library.management.system.demo.dto.authenticationdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class LoginRequestDTO {
    private String email;
    private String password;
}
