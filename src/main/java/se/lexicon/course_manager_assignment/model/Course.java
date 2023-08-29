package se.lexicon.course_manager_assignment.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public class Course implements Serializable {
    private Integer id;
    private String courseName;
    private LocalDate startDate;
    private Integer weekDuration;
    private Collection<Student> students;

    // Constructors
    public Course() {
    }

    public Course(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Integer getWeekDuration() {
        return weekDuration;
    }

    public void setWeekDuration(Integer weekDuration) {
        this.weekDuration = weekDuration;
    }

    public Collection<Student> getStudents() {
        return students;
    }

    public void setStudents(Collection<Student> students) {
        this.students = students;
    }

    public boolean enrollStudent(Student student) {
        if (this.getStudents() != null) {
            boolean isStudentEnrolled = this.getStudents().stream()
                    .anyMatch(existingStudent -> existingStudent.getId().equals(id));
            if (isStudentEnrolled) {
                return false;
            }
            this.getStudents().add(student);
        }
        this.setStudents(Collections.singleton(student));
        return true;
    }
    public boolean unrollStudent(Student student) {
        if (this.getStudents() != null) {
            boolean isStudentEnrolled = this.getStudents().stream()
                    .anyMatch(existingStudent -> existingStudent.getId().equals(id));
            if (isStudentEnrolled) {
                this.getStudents().remove(student);  // todo : why is this not working?
            }
            return false;
        }
//        this.setStudents(Collections.singleton(student));
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return Objects.equals(id, course.id) && Objects.equals(courseName, course.courseName) && Objects.equals(startDate, course.startDate) && Objects.equals(weekDuration, course.weekDuration) && Objects.equals(students, course.students);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, courseName, startDate, weekDuration, students);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", courseName='" + courseName + '\'' +
                ", startDate=" + startDate +
                ", weekDuration=" + weekDuration +
                ", students=" + students +
                '}';
    }
}
