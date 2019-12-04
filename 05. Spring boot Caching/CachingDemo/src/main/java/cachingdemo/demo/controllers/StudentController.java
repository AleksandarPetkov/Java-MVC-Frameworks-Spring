package cachingdemo.demo.controllers;

import cachingdemo.demo.models.Student;
import cachingdemo.demo.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/student/{id}")
    private Student getStudentById (@PathVariable String id){
        System.out.println("Searching by ID  : " + id);

        return studentService.getStudentById(id);
    }
}
