package by.alexander.security.student;

import by.alexander.security.entity.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {

    private static final List<Student> STUDENTS = List.of(
            new Student(1L, "James"),
            new Student(2L, "Maria"),
            new Student(3L, "Anna")
    );

    @GetMapping(path = "{id}")
    public Student getStudent(@PathVariable("id") Long id) {
        return STUDENTS
                .stream()
                .filter(student -> student.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Student with given ID does not exists! ID: " + id));
    }

}