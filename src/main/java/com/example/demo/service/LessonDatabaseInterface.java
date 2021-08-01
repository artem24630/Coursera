package com.example.demo.service;

import com.example.demo.domain.Lesson;
import com.example.demo.dto.LessonDto;

import java.util.List;
import java.util.Optional;

public interface LessonDatabaseInterface {
    List<LessonDto> findAllForLessonIdWithoutText(long id);

    List<Lesson> findAll();

    Optional<Lesson> findById(long id);

    void save(Lesson lesson);

    void save(LessonDto lessonDto);

    void delete(long id);

    List<Lesson> findByTitleLike(String title);
}
