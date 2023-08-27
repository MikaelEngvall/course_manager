package se.lexicon.course_manager_assignment.data.dao;

import se.lexicon.course_manager_assignment.model.Course;
import se.lexicon.course_manager_assignment.model.Student;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

import static se.lexicon.course_manager_assignment.data.sequencers.CourseSequencer.nextCourseId;

public class CourseCollectionRepository implements CourseDao{

    private Collection<Course> courses;
    private final StudentCollectionRepository studentRepository; // For finding student in courses

    public CourseCollectionRepository(Collection<Course> courses, StudentCollectionRepository studentRepository) {
        this.courses = courses;
        this.studentRepository = studentRepository;
    }

    @Override
    public Course createCourse(String courseName, LocalDate startDate, int weekDuration) {
        Course course = new Course(nextCourseId());
        course.setCourseName(courseName);
        course.setStartDate(startDate);
        course.setWeekDuration(weekDuration);
        courses.add(course);
        return course;
    }

    @Override
    public Course findById(int id) { // Using stream to manipulate (by using filter i.ex) data to get the output i need
        return courses.stream()
                .filter(course -> course.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Collection<Course> findByNameContains(String name) {
        return courses.stream()
                .filter(course -> course.getCourseName().contains(name))
                .collect(Collectors.toList()); //
    }

    @Override
    // method name doesnt clearly state what is the expected output.
    // I could expect it to return me only ongoing courses ie
    // startDate + weekDuration is before endDate
    // but by name in controller(by case not documentation) it
    // states it expect that courseStartIsBefore endDate
    public Collection<Course> findByDateBefore(LocalDate end) {
        return courses.stream()
                .filter(course -> course.getStartDate().isBefore(end))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Course> findByDateAfter(LocalDate start) {
        return courses.stream()
                .filter(course -> course.getStartDate().isAfter(start))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Course> findAll() {
        return courses;
    }

    @Override
    public Collection<Course> findByStudentId(int studentId) {
        Student student = studentRepository.findById(studentId);
        return courses.stream()
                .filter(course -> course.getStudents().contains(student))
                .collect(Collectors.toList());
    }

    @Override
    public boolean removeCourse(Course course) {
        return courses.remove(course);
    }

    @Override
    public void clear() {
        this.courses = new HashSet<>();
    }
}
