package com.example.school.service;

import com.example.school.model.Student;
import com.example.school.model.StudentRowMapper;
import com.example.school.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentH2Service implements StudentRepository {

    @Autowired
    private JdbcTemplate db;

    @Override
    public ArrayList<Student> getStudents() {
        List<Student> studentCollection = db.query("SELECT * FROM STUDENT", new StudentRowMapper());
        return new ArrayList<>(studentCollection);
    }

    @Override
    public Student getStudentById(int studentId) {
        try {
            return db.queryForObject("SELECT * FROM STUDENT WHERE studentid = ?", new StudentRowMapper(), studentId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Student addStudent(Student student) {
        db.update("INSERT INTO STUDENT(studentName, gender, standard) VALUES (?, ?, ?)",
                student.getStudentName(), student.getGender(), student.getStandard());

        return db.queryForObject(
                "SELECT * FROM STUDENT WHERE studentName = ? AND gender = ? AND standard = ?",
                new StudentRowMapper(), student.getStudentName(), student.getGender(), student.getStandard());
    }

    @Override
    public Student updateStudent(int studentId, Student student) {
        if (student.getStudentName() != null) {
            db.update("UPDATE STUDENT SET studentName = ? WHERE studentid = ?", student.getStudentName(), studentId);
        }
        if (student.getGender() != null) {
            db.update("UPDATE STUDENT SET gender = ? WHERE studentid = ?", student.getGender(), studentId);
        }
        if (student.getStandard() != 0) {
            db.update("UPDATE STUDENT SET standard = ? WHERE studentid = ?", student.getStandard(), studentId);
        }
        return getStudentById(studentId);
    }

    @Override
    public void deleteStudent(int studentId) {
        db.update("DELETE FROM STUDENT WHERE studentid = ?", studentId);
    }

    @Override
    public String addStudentsInBulk(ArrayList<Student> students) {
        for (Student student : students) {
            addStudent(student);
        }
        return "Successfully added " + students.size() + " students";
    }
}
