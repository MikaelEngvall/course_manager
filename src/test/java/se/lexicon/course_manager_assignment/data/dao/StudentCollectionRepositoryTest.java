package se.lexicon.course_manager_assignment.data.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.lexicon.course_manager_assignment.data.sequencers.StudentSequencer;
import se.lexicon.course_manager_assignment.model.Student;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(classes = {StudentCollectionRepository.class})
class StudentCollectionRepositoryTest {

    private static final String MOCKED_NAME = "mike";
    private static final String MOCKED_EMAIL = "mltr@gmail.com";
    private static final String MOCKED_ADDRESS = "home";

    @Autowired
    private StudentDao sut;

    @Test
    @DisplayName("Test context successfully setup")
    void context_loads() {
        assertFalse(sut == null);
    }

    //Write your tests here
    @Test
    @DisplayName("")
    void createStudent_whenStudentDetailsPassed_thenStudentIsCreated() {
        Student student = sut.createStudent(MOCKED_NAME, MOCKED_EMAIL, MOCKED_ADDRESS);
        assertEquals(MOCKED_NAME, student.getName());
        assertEquals(MOCKED_EMAIL, student.getEmail());
        assertEquals(MOCKED_ADDRESS, student.getAddress());
        assertEquals(1, student.getId());
    }


    @AfterEach
    void tearDown() {
        sut.clear();
        StudentSequencer.setStudentSequencer(0);
    }
}
