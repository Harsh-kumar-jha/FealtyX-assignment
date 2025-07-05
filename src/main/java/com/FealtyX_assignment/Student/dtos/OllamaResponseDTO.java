package com.FealtyX_assignment.Student.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OllamaResponseDTO {
    private String model;
    private String response;
    private Boolean done;
    private Long totalDuration;
    private Long loadDuration;
    private Long promptEvalDuration;
    private Long evalDuration;
    private Integer promptEvalCount;
    private Integer evalCount;
}
