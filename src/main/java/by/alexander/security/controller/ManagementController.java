package by.alexander.security.controller;

import by.alexander.security.entity.Student;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("management/api/v1/students")
public class ManagementController {

    private static final List<Student> STUDENTS = List.of(
            new Student(1L, "James"),
            new Student(2L, "Maria"),
            new Student(3L, "Anna")
    );

    @GetMapping
//    @PreAuthorize("hasAuthority('student:read')")
    public List<Student> getAllStudents() {
        return Collections.unmodifiableList(STUDENTS);
    }

    @PostMapping
//    @PreAuthorize("hasAuthority('student:write')")
    public void saveStudent(@RequestBody Student student) {
        System.out.println("Create: " + student);
    }

    @PostMapping(path = "{id}")
//    @PreAuthorize("hasAuthority('student:write')")
    public void deleteStudent(@PathVariable("id") Long id) {
        System.out.println(id);
    }
}
