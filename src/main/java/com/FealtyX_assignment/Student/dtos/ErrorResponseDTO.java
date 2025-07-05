package com.FealtyX_assignment.Student.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.FealtyX_assignment.Student.utils.Constants.DEFAULT_DATE_TIME;
import static com.FealtyX_assignment.Student.utils.Constants.DEFAULT_DATE_TIME_FORMAT;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDTO {
    private int status;
    private String message;
    private String details;
    private String path;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DEFAULT_DATE_TIME_FORMAT)
    @Builder.Default
    private LocalDateTime timestamp = DEFAULT_DATE_TIME;

    
}
