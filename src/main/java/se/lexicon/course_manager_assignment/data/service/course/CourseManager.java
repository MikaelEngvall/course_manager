package se.lexicon.course_manager_assignment.data.service.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.lexicon.course_manager_assignment.data.dao.CourseDao;
import se.lexicon.course_manager_assignment.data.dao.StudentDao;
import se.lexicon.course_manager_assignment.data.service.converter.Converters;
import se.lexicon.course_manager_assignment.dto.forms.CreateCourseForm;
import se.lexicon.course_manager_assignment.dto.forms.UpdateCourseForm;
import se.lexicon.course_manager_assignment.dto.views.CourseView;
import se.lexicon.course_manager_assignment.model.Course;
import se.lexicon.course_manager_assignment.model.Student;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Service
public class CourseManager implements CourseService {

    private final CourseDao courseDao;
    private final StudentDao studentDao;
    private final Converters converters;

    @Autowired
    public CourseManager(CourseDao courseDao, StudentDao studentDao, Converters converters) {
        this.courseDao = courseDao;
        this.studentDao = studentDao;
        this.converters = converters;
    }

    @Override
    public CourseView create(CreateCourseForm form) {
        Course course = courseDao.createCourse(form.getCourseName(), form.getStartDate(), form.getWeekDuration());
        CourseView courseView = converters.courseToCourseView(course);
        return courseView;
    }

    @Override
    public CourseView update(UpdateCourseForm form) {  // Does the same as update in StudentManager but for courses instead
        Course course = courseDao.findById(form.getId());

        if (course != null) {
            if (form.getCourseName() != null) {
                course.setCourseName(form.getCourseName());
            }
            if (form.getStartDate() != null) {
                course.setStartDate(form.getStartDate());
            }
            if (form.getWeekDuration() != null) {
                course.setWeekDuration(form.getWeekDuration());
            }

            // Update the course in the repository
            Course updatedCourse = courseDao.updateCourse(course.getCourseName(), course.getStartDate(), course.getWeekDuration());
            if (updatedCourse != null) {
                return converters.courseToCourseView(updatedCourse);
            }
        }

        return null; // Return null if the course doesn't exist or update fails
    }


    @Override
    public List<CourseView> searchByCourseName(String courseName) {
        Collection<Course> course = courseDao.findByNameContains(courseName);
        return converters.coursesToCourseViews(course);
    }

    @Override
    public List<CourseView> searchByDateBefore(LocalDate end) {
        return converters.coursesToCourseViews(courseDao.findByDateBefore(end));
    }

    @Override
    public List<CourseView> searchByDateAfter(LocalDate start) {
        return converters.coursesToCourseViews(courseDao.findByDateAfter(start));
    }

    @Override
    public boolean addStudentToCourse(int courseId, int studentId) {
        Student student = studentDao.findById(studentId);
        Course course = courseDao.findById(courseId);
        return course.enrollStudent(student);
    }

    @Override
    public boolean removeStudentFromCourse(int courseId, int studentId) {
        Course course = courseDao.findById(courseId);
        Student student = studentDao.findById(studentId);

        if (course != null && student != null) {
            boolean removed = course.unrollStudent(student);
            if (removed) {
                courseDao.updateCourse(course.getCourseName(), course.getStartDate(), course.getWeekDuration());
            }
            return removed;
        }

        return false; // Return false if the course or student doesn't exist
    }


    @Override
    public CourseView findById(int id) {
        Course course = courseDao.findById(id);
        return converters.courseToCourseView(course);
    }

    @Override
    public List<CourseView> findAll() {
        Collection<Course> courses = courseDao.findAll();
        return converters.coursesToCourseViews(courses);
    }

    @Override
    public List<CourseView> findByStudentId(int studentId) { // todo Help
        return null;
    }

    @Override
    public boolean deleteCourse(int id) {
        Course course = courseDao.findById(id);
        if (course != null) {
            return courseDao.removeCourse(course);
        }
        return false;
    }
}
