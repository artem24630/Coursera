package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonDto {
    private Long id;

    private String title;

    private String text;

    private Long courseId;

    public LessonDto(Long courseId) {
        this.courseId = courseId;
    }

    public LessonDto(Long id, String title, Long courseId){
        this.id = id;
        this.title = title;
        this.courseId = courseId;
    }
}
