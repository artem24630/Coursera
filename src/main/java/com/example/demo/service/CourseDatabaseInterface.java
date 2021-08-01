package com.example.demo.service;

import com.example.demo.domain.Course;

import java.util.List;
import java.util.Optional;

public interface CourseDatabaseInterface {
    List<Course> findAll();

    Optional<Course> findById(long id);

    void save(Course course);

    void delete(long id);

    List<Course> findByTitleLike(String title);
}
