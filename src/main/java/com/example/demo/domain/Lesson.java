package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lessons")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotBlank(message = "Title of the lesson has to be filled")
    private String title;

    @Lob
    @Column
    @NotBlank(message = "Text of the lesson has to be filled")
    private String text;

    @ManyToOne(optional = false)// optional=false не должно быть возможности добавить урок, который не относится ни к одному из курсов.
    private Course course;
}
