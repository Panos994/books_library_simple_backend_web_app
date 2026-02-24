package library.management.system.demo.dto.ExceptionDTO;

import java.time.LocalDateTime;

public record ErrorResponse(
    String message,
    int status,
    LocalDateTime timeStamp)
{}
