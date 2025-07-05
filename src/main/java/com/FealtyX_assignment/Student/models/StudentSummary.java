package com.FealtyX_assignment.Student.models;

import lombok.*;

import static com.FealtyX_assignment.Student.utils.Constants.DEFAULT_DATE_TIME;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class StudentSummary {
    private Integer studentId;
    private String summary;
    
    @Builder.Default
    private LocalDateTime generatedAt = DEFAULT_DATE_TIME;
}
