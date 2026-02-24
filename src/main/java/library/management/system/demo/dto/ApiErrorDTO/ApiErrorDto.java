package library.management.system.demo.dto.ApiErrorDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ApiErrorDto {
    private String code;
    private String message;
    private int status;
    private LocalDateTime timeStamp;
    private String path;

}
