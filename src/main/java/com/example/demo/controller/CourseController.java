package com.example.demo.controller;

import com.example.demo.domain.Course;
import com.example.demo.domain.User;
import com.example.demo.service.CourseDatabaseInterface;
import com.example.demo.service.LessonDatabaseInterface;
import com.example.demo.service.UserDatabaseInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/course")
public class CourseController {

    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);
    private final CourseDatabaseInterface courseDatabaseManager;
    private final UserDatabaseInterface userDatabaseInterface;
    private final LessonDatabaseInterface lessonDatabaseManager;

    @Autowired
    public CourseController(CourseDatabaseInterface courseDatabaseManager, UserDatabaseInterface userDatabaseManager, LessonDatabaseInterface lessonDatabaseManager) {
        this.courseDatabaseManager = courseDatabaseManager;
        this.userDatabaseInterface = userDatabaseManager;
        this.lessonDatabaseManager = lessonDatabaseManager;
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
        model.addAttribute("lessons", lessonDatabaseManager.findAllForLessonIdWithoutText(currentCourse.getId()));
        model.addAttribute("users", currentCourse.getUsers());
        return "course_form";
    }

    @PostMapping
    public String submitCourseForm(@Valid Course course, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "course_form";
        }
        courseDatabaseManager.save(course);
        userDatabaseInterface.save(new User(course.getAuthor()));
        return "redirect:/course";
    }

    @GetMapping("/new")
    public String courseForm(Model model) {
        model.addAttribute("course", new Course());
        return "course_form";
    }

    @DeleteMapping("/{id}")
    public String deleteCourse(@PathVariable("id") Long id) {
        courseDatabaseManager.delete(id);
        return "redirect:/course";
    }

    @GetMapping("/{id}/assign")
    public String assignUserToCourse(Model model, @PathVariable("id") Long id) {
        model.addAttribute("courseId", id);
        model.addAttribute("users", userDatabaseInterface.findAll());
        return "course_assign";
    }

    @PostMapping("/{courseId}/assign")
    public String assignUserForCourse(@PathVariable("courseId") Long courseId,
                                      @RequestParam("userId") Long id) {
        User user = userDatabaseInterface.findById(id).get();
        Course course = courseDatabaseManager.findById(courseId).get();
        course.getUsers().add(user);
        user.getCourses().add(course);
        courseDatabaseManager.save(course);
        return "redirect:/course";
    }

    @DeleteMapping("/{courseId}/unassign")
    public String unassignUserFromCourse(@PathVariable("courseId") Long courseId,
                                         @RequestParam("userId") Long userId) {
        User user = userDatabaseInterface.findById(userId).orElseThrow(NotFoundException::new);
        Course course = courseDatabaseManager.findById(courseId)
                .orElseThrow(NotFoundException::new);
        user.getCourses().remove(course);
        course.getUsers().remove(user);
        courseDatabaseManager.save(course);
        return "redirect:/course/" + courseId;
    }

    @ExceptionHandler
    public ModelAndView notFoundExceptionHandler(NotFoundException ex) {
        ModelAndView modelAndView = new ModelAndView("not_found");
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }
}