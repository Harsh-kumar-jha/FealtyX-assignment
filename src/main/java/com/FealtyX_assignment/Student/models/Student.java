package com.FealtyX_assignment.Student.models;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Student {
    private Integer id;
    private String name;
    private Integer age;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
