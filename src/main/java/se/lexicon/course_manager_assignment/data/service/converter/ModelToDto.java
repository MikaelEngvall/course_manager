package se.lexicon.course_manager_assignment.data.service.converter;

import org.springframework.stereotype.Component;
import se.lexicon.course_manager_assignment.dto.views.CourseView;
import se.lexicon.course_manager_assignment.dto.views.StudentView;
import se.lexicon.course_manager_assignment.model.Course;
import se.lexicon.course_manager_assignment.model.Student;


import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ModelToDto implements Converters {
    @Override
    public StudentView studentToStudentView(Student student) {
        return new StudentView(
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getAddress());
    }

    @Override
    public CourseView courseToCourseView(Course course) {
        return new CourseView(course, studentsToStudentViews(course.getStudents()));
    }

    @Override
    public List<CourseView> coursesToCourseViews(Collection<Course> courses) {
        return courses.stream()
                .map(course -> new CourseView(course, studentsToStudentViews(course.getStudents())))
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentView> studentsToStudentViews(Collection<Student> students) {
        if (students == null) {
            return Collections.emptyList();
        }
        return students.stream()
                .map(student -> new StudentView(student.getId(), student.getName(), student.getEmail(), student.getAddress()))
                .collect(Collectors.toList());
    }
}
