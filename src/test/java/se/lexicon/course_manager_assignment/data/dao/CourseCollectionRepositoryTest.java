package se.lexicon.course_manager_assignment.data.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.lexicon.course_manager_assignment.data.sequencers.CourseSequencer;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(classes = {CourseCollectionRepository.class})
public class CourseCollectionRepositoryTest {

    private static final String MOCKED_NAME = "Java for dummies";
    private static final LocalDate MOCKED_STARTDATE = LocalDate.parse("2022-01-01");
    private static final int MOCKED_WEEKDURATION = 12;

    @Autowired
    private CourseDao sut;

    @Test
    @DisplayName("Test context successfully setup")
    void context_loads() {
        assertFalse(sut == null);
    }

    @Test
    void createCourse_whenCourseDetailsArePassed_thenCourseIsCreated() {
        assertThat(sut.createCourse(MOCKED_NAME, MOCKED_STARTDATE, MOCKED_WEEKDURATION)).satisfies(createdCourse -> {
            assertThat(createdCourse.getCourseName()).isEqualTo(MOCKED_NAME);
            assertThat(createdCourse.getStartDate()).isEqualTo(MOCKED_STARTDATE);
            assertThat(createdCourse.getWeekDuration()).isEqualTo(MOCKED_WEEKDURATION);
            assertThat(createdCourse.getId()).isEqualTo(1);
        }); // This test confines testable method response instead of like I did in StudentCollectionRepositoryTest
    }


    @AfterEach
    void tearDown() {
        sut.clear();
        CourseSequencer.setCourseSequencer(0);
    }
}
