package com.example.demo.service.impl;

import com.example.demo.dao.LessonRepository;
import com.example.demo.domain.Course;
import com.example.demo.domain.Lesson;
import com.example.demo.dto.LessonDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LessonDatabaseManager implements com.example.demo.service.LessonDatabaseInterface {
    private final LessonRepository lessonRepository;

    @Autowired
    private CourseDatabaseManager courseDatabaseManager;

    @Autowired
    public LessonDatabaseManager(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @Override
    public List<LessonDto> findAllForLessonIdWithoutText(long id) {
        return lessonRepository.findAllForLessonIdWithoutText(id);
    }


    @Override
    public List<Lesson> findAll() {
        return lessonRepository.findAll();
    }

    @Override
    public Optional<Lesson> findById(long id) {
        return lessonRepository.findById(id);
    }

    @Override
    public void save(Lesson lesson) {
        lessonRepository.save(lesson);
    }

    @Override
    public void save(LessonDto lessonDto) {
        Course course = courseDatabaseManager.findById(lessonDto.getCourseId()).get();
        Lesson lesson = new Lesson(
                lessonDto.getId(),
                lessonDto.getTitle(),
                lessonDto.getText(),
                course
        );
        lessonRepository.save(lesson);
    }

    @Override
    public void delete(long id) {
        lessonRepository.deleteById(id);
    }

    @Override
    public List<Lesson> findByTitleLike(String title) {
        return lessonRepository.findByTitleLike(title);
    }
}
