package com.FealtyX_assignment.Student.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.FealtyX_assignment.Student.utils.Constants.FALSE;

import jakarta.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OllamaRequestDTO {
    @NotBlank(message = "Model name is required")
    private String model;
    
    @NotBlank(message = "Prompt is required")
    private String prompt;

    @Builder.Default
    private Boolean stream = FALSE;

    private Options options;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Options {
        private Double temperature;
        private Integer topK;
        private Double topP;
    }
}
