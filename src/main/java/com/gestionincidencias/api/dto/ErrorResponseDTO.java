package com.gestionincidencias.api.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponseDTO {

    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String message;
    private List<String> messages;
    private String path;
}