package com.example.demo.controller;

import com.example.demo.dto.LessonDto;
import com.example.demo.service.LessonDatabaseManager;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/lesson")
public class LessonController {
    private final LessonDatabaseManager lessonDatabaseManager;

    public LessonController(LessonDatabaseManager lessonDatabaseManager) {
        this.lessonDatabaseManager = lessonDatabaseManager;
    }

    @GetMapping("/new")
    public String lessonForm(Model model, @RequestParam("course_id") long courseId) {
        model.addAttribute("lesson", new LessonDto(courseId));
        return "lesson_form";
    }

    @PostMapping
    public String submitLessonForm(@Valid LessonDto lesson, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "lesson_form";
        }
        lessonDatabaseManager.save(lesson);
        return "redirect:/course";
    }

    @GetMapping("/{id}")
    public String lessonForm(Model model, @PathVariable("id") Long id) {
        model.addAttribute("lesson", lessonDatabaseManager.findById(id)
                .map(l ->  new LessonDto(l.getId(), l.getTitle(), l.getText(), l.getCourse().getId())));
        return "lesson_form";
    }

    @DeleteMapping("/{id}")
    public String deleteLesson(@PathVariable("id") Long id) {
        lessonDatabaseManager.delete(id);
        return "redirect:/course";
    }

    @ExceptionHandler
    public ModelAndView notFoundExceptionHandler(NotFoundException ex) {
        ModelAndView modelAndView = new ModelAndView("not_found");
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }
}
