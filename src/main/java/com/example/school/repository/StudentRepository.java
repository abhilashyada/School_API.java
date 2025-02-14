package com.example.school.repository;

import java.util.ArrayList;
import com.example.school.model.Student;

public interface StudentRepository {
    ArrayList<Student> getStudents();
    Student getStudentById(int studentId);
    Student addStudent(Student student);
    Student updateStudent(int studentId, Student student);
    void deleteStudent(int studentId);
    String addStudentsInBulk(ArrayList<Student> students);
}
