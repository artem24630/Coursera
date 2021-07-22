package com.example.demo.dao;

import com.example.demo.domain.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findByTitleLike(String title);
}
