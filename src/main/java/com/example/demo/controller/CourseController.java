package com.example.demo.controller;

import com.example.demo.domain.Course;
import com.example.demo.dto.LessonDto;
import com.example.demo.service.CourseDatabaseManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Collections;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/course")
public class CourseController {
    private final CourseDatabaseManager courseDatabaseManager;

    @Autowired
    public CourseController(CourseDatabaseManager courseDatabaseManager) {
        this.courseDatabaseManager = courseDatabaseManager;
    }

    @GetMapping
    public String courseTable(Model model, @RequestParam(name = "titlePrefix", required = false) String titlePrefix) {
        model.addAttribute("courses", courseDatabaseManager.findByTitleLike(((titlePrefix == null) ? "" : titlePrefix) + "%"));
        model.addAttribute("activePage", "courses");
        return "course_table";
    }

    @GetMapping("/{id}")
    public String courseForm(Model model, @PathVariable("id") Long id) {
        Course currentCourse = courseDatabaseManager.findById(id)
                .orElseThrow(NotFoundException::new);
        model.addAttribute("course", currentCourse);
        model.addAttribute("lessons", currentCourse.getLessons().stream().map(l -> new LessonDto(l.getId(), l.getTitle(), l.getText(), l.getCourse().getId())).collect(Collectors.toList()));
        return "course_form";
    }

    @PostMapping
    public String submitCourseForm(@Valid Course course, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "course_form";
        }
        courseDatabaseManager.save(course);
        return "redirect:/course";
    }

    @GetMapping("/new")
    public String courseForm(Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("lessons", Collections.emptyList());
        return "course_form";
    }

    @DeleteMapping("/{id}")
    public String deleteCourse(@PathVariable("id") Long id) {
        courseDatabaseManager.delete(id);
        return "redirect:/course";
    }

    @ExceptionHandler
    public ModelAndView notFoundExceptionHandler(NotFoundException ex) {
        ModelAndView modelAndView = new ModelAndView("not_found");
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }
}