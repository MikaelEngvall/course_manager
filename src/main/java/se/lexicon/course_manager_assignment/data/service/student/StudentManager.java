package se.lexicon.course_manager_assignment.data.service.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.lexicon.course_manager_assignment.data.dao.CourseDao;
import se.lexicon.course_manager_assignment.data.dao.StudentDao;
import se.lexicon.course_manager_assignment.data.service.converter.Converters;
import se.lexicon.course_manager_assignment.dto.forms.CreateStudentForm;
import se.lexicon.course_manager_assignment.dto.forms.UpdateStudentForm;
import se.lexicon.course_manager_assignment.dto.views.StudentView;
import se.lexicon.course_manager_assignment.model.Student;


import java.util.Collection;
import java.util.List;

@Service
public class StudentManager implements StudentService {

    private final StudentDao studentDao;
    private final CourseDao courseDao;
    private final Converters converters;

    @Autowired
    public StudentManager(StudentDao studentDao, CourseDao courseDao, Converters converters) {
        this.studentDao = studentDao;
        this.courseDao = courseDao;
        this.converters = converters;
    }

    @Override
    public StudentView create(CreateStudentForm form) {
        Student createdStudent = studentDao.createStudent(form.getName(), form.getEmail(), form.getAddress());
        StudentView dtoResponse = converters.studentToStudentView(createdStudent);
        return dtoResponse;
    }

    @Override
    public StudentView update(UpdateStudentForm form) {
        Student existingStudent = studentDao.findById(form.getId());

        if (existingStudent != null) {   //Updates the name if student exists and name provided by the user isn't empty
            if (form.getName() != null) {
                existingStudent.setName(form.getName());
            }
            if (form.getEmail() != null) {  //Updates the email address
                existingStudent.setEmail(form.getEmail());
            }
            if (form.getAddress() != null) { //Updates the address
                existingStudent.setAddress(form.getAddress());
            }
            studentDao.updateStudent(existingStudent.getName(), existingStudent.getEmail(), existingStudent.getAddress()); // Creates the updated student
            return converters.studentToStudentView(existingStudent);
        }

        return null; // Return null if the student with the given ID doesn't exist
    }


    @Override
    public StudentView findById(int id) {
        Student student = studentDao.findById(id);
        if (student != null) return converters.studentToStudentView(student);
        return null;
    }

    @Override
    public StudentView searchByEmail(String email) {
        Student student = studentDao.findByEmailIgnoreCase(email);
        return converters.studentToStudentView(student);
    }

    @Override
    public List<StudentView> searchByName(String name) {
        Collection<Student> student = studentDao.findByNameContains(name);
        return converters.studentsToStudentViews(student);
    }

    @Override
    public List<StudentView> findAll() {
        Collection<Student> students = studentDao.findAll();
        return converters.studentsToStudentViews(students);
    }

    @Override
    public boolean deleteStudent(int id) {
        Student student = studentDao.findById(id);
        if (student != null) {
            return studentDao.removeStudent(student);
        }
        return false;
    }
}
