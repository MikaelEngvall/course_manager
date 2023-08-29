package se.lexicon.course_manager_assignment.data.dao;


import se.lexicon.course_manager_assignment.model.Student;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

import static se.lexicon.course_manager_assignment.data.sequencers.StudentSequencer.nextStudentId;


public class StudentCollectionRepository implements StudentDao {

    private Collection<Student> students;

    public StudentCollectionRepository(Collection<Student> students) {
        this.students = students;
    }

    @Override
    public Student createStudent(String name, String email, String address) {
        validateEmailIsUnique(email);
//      Student student = new Student(StudentSequencer.nextStudentId());// Class StudentSequencer imported (not static)
        Student student = new Student(nextStudentId());                 // This imports only one method (static)
        student.setName(name);
        student.setEmail(email);
        student.setAddress(address);
        students.add(student);
        return student;
    }
    @Override
    public Student updateStudent(String name, String email, String address) {
        Student existingStudent = findByEmailIgnoreCase(email);

        if (existingStudent != null) {
            existingStudent.setName(name);
            existingStudent.setEmail(email);
            existingStudent.setAddress(address);
        }

        return existingStudent;
    }


    @Override
    public Student findByEmailIgnoreCase(String email) {
        return students.stream()
                .filter(student -> student.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Collection<Student> findByNameContains(String name) {
        return students.stream()
                .filter(student -> student.getName().contains(name))
                .collect(Collectors.toList());
    }

    @Override
    public Student findById(int id) {// Using stream to manipulate (by using filter i.ex) data to get the output i need
        return students.stream()
                .filter(student -> student.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Collection<Student> findAll() {
        return students;
    }

    @Override
    public boolean removeStudent(Student student) {
        boolean removed = students.remove(student);

//        if (removed) {
//            // Remove the student from all courses they are enrolled in
//            courses.forEach(course -> course.unrollStudent(student));
//        }

        return removed;
    }


    @Override
    public void clear() {
        this.students = new HashSet<>();
    }

    private void validateEmailIsUnique(String email) {
        boolean emailAlreadyExists = students.stream().anyMatch(student -> student.getEmail().equals(email));
        if (emailAlreadyExists) {
            throw new RuntimeException("email not found");
        }
    }
}
