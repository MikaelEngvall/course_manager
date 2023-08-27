package se.lexicon.course_manager_assignment.data.dao;



import se.lexicon.course_manager_assignment.model.Student;

import java.util.Collection;

public interface StudentDao {

    Student createStudent(String name, String email, String address);
    Student updateStudent(String name, String email, String address); //todo had to add this right?
    Student findByEmailIgnoreCase(String email);
    Collection<Student> findByNameContains(String name);
    Student findById(int id);
    Collection<Student> findAll();
    boolean removeStudent(Student student);
    void clear();

}
