package library.management.system.demo.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookCreationResponseDTO {

    private String title;
    private UUID userId;
}
