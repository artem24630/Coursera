package com.example.demo.service;

import com.example.demo.dao.LessonRepository;
import com.example.demo.domain.Course;
import com.example.demo.domain.Lesson;
import com.example.demo.dto.LessonDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LessonDatabaseManager {
    private final LessonRepository lessonRepository;

    @Autowired
    private CourseDatabaseManager courseDatabaseManager;

    @Autowired
    public LessonDatabaseManager(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    public List<LessonDto> findAllForLessonIdWithoutText(long id){
        return lessonRepository.findAllForLessonIdWithoutText(id);
    }


    public List<Lesson> findAll() {
        return lessonRepository.findAll();
    }

    public Optional<Lesson> findById(long id) {
        return lessonRepository.findById(id);
    }

    public void save(Lesson lesson) {
        lessonRepository.save(lesson);
    }

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

    public void delete(long id) {
        lessonRepository.deleteById(id);
    }

    public List<Lesson> findByTitleLike(String title) {
        return lessonRepository.findByTitleLike(title);
    }
}
