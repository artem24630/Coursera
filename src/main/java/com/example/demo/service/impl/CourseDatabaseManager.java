package com.example.demo.service.impl;

import com.example.demo.dao.CourseRepository;
import com.example.demo.domain.Course;
import com.example.demo.service.CourseDatabaseInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseDatabaseManager implements CourseDatabaseInterface {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseDatabaseManager(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }


    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public Optional<Course> findById(long id) {
        return courseRepository.findById(id);
    }

    public void save(Course course) {
        courseRepository.save(course);
    }

    public void delete(long id) {
        courseRepository.deleteById(id);
    }

    public Course getOne(long id) {
        return courseRepository.getOne(id);
    }

    public List<Course> findByTitleLike(String title) {
        return courseRepository.findByTitleLike(title);
    }
}
