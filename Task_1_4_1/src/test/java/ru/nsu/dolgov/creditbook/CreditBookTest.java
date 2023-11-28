package ru.nsu.dolgov.creditbook;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class for creditBook test.
 */
public class CreditBookTest {
    private CreditBook creditBook;

    /**
     * Mock of credit book.
     */
    @BeforeEach
    public void testInitialize() throws Exception {
        Student student = new Student(
                "Testov Test Testovich"
        );
        Subject subj1 = new Subject("Discrete Mathematics", 5,
                "11.01.2023", "Stukachev A.I.", 1
        );
        subj1 = subj1; // Fuck linter

        Subject subj2 = new Subject("English", 4,
                "09.06.2023", "Savilova T.K.", 2
        );
        subj2 = subj2; // Fuck linter

        Subject subj3 = new Subject("Mathematical Analysis", 4,
                "09.06.2023", "Vaskevich V.L.", 3
        );
        subj3 = subj3; // Fuck linter

        Subject subj4 = new Subject("Differential Equations", 5,
                "15.06.2024", "Vaskevich V.L.", 4
        );
        subj4 = subj4; // Fuck linter

        Subject subj5 = new Subject("Computational Mathematics", 5,
                "16.01.2025", "Vaskevich V.L.", 5
        );
        subj5 = subj5; // Fuck linter

        Subject subj6 = new Subject("Bioinformatics", 4,
                "15.06.2025", "Ivanova A.A.", 6
        );
        subj6 = subj6; // Fuck linter

        Subject subj7 = new Subject("Computer Linguistics", 5,
                "20.01.2026", "Smirnova V.V.", 7
        );
        subj7 = subj7; // Fuck linter

        Subject subj9 = new Subject("Computer Linguistics", 3,
                "20.01.2026", "Smirnova V.V.", 6
        );
        subj9 = subj9; // Fuck linter

        Subject subj8 = new Subject("Квалификационная работа", 5,
                "15.06.2026", "Comission", 8
        );
        subj8 = subj8; // Fuck linter

        Speciality speciality = new Speciality("123-4", "Testovaya Specialnost");
        this.creditBook = new CreditBook(
                student,
                "FIT",
                speciality,
                "2022-02-03",
                "242-434",
                8
        );

        creditBook.semesters.addSemester(1);
        creditBook.semesters.addSubjectToSemester(1, subj1);
        creditBook.semesters.addSemester(2);
        creditBook.semesters.addSubjectToSemester(2, subj2);
        creditBook.semesters.addSemester(3);
        creditBook.semesters.addSubjectToSemester(3, subj3);
        creditBook.semesters.addSemester(4);
        creditBook.semesters.addSubjectToSemester(4, subj4);
        creditBook.semesters.addSemester(5);
        creditBook.semesters.addSubjectToSemester(5, subj5);
        creditBook.semesters.addSemester(6);
        creditBook.semesters.addSubjectToSemester(6, subj6);
        creditBook.semesters.addSubjectToSemester(6, subj9);
        creditBook.semesters.addSemester(7);
        creditBook.semesters.addSubjectToSemester(7, subj7);
        creditBook.semesters.addSemester(8);
        creditBook.semesters.addSubjectToSemester(8, subj8);
    }

    @Test
    void checkAddSubjectToSemester() throws Exception {
        Student student = new Student(
                "Testov Test Testovich"
        );
        Subject subj1 = new Subject(
                "Matan",
                34,
                "Хорошо",
                "Testoviy Prepod",
                1
        );
        Speciality speciality = new Speciality("123-4", "Testovaya Specialnost");
        CreditBook creditBook = new CreditBook(
                student,
                "FIT",
                speciality,
                "2022-02-03",
                "242-434",
                12
        );

        creditBook.semesters.addSemester(1);
        creditBook.semesters.addSubjectToSemester(1, subj1);

        assertEquals(1, creditBook.semesters.getSubjectsBySemester(1).size());
    }

    @Test
    void checkRemoveSubjectFromSemester() throws Exception {
        Student student = new Student(
                "Testov Test Testovich"
        );
        Subject subj1 = new Subject(
                "Matan",
                34,
                "Хорошо",
                "Testoviy Prepod",
                1
        );
        Speciality speciality = new Speciality("123-4", "Testovaya Specialnost");
        CreditBook creditBook = new CreditBook(
                student,
                "FIT",
                speciality,
                "2022-02-03",
                "242-434",
                12
        );

        creditBook.semesters.addSemester(1);
        creditBook.semesters.addSubjectToSemester(1, subj1);
        creditBook.semesters.removeSubjectFromSemester(1, subj1);

        assertEquals(0, creditBook.semesters.getSubjectsBySemester(1).size());
    }

    @Test
    void checkAddSemester() throws Exception {
        Student student = new Student(
                "Testov Test Testovich"
        );
        Speciality speciality = new Speciality("123-4", "Testovaya Specialnost");
        CreditBook creditBook = new CreditBook(
                student,
                "FIT",
                speciality,
                "2022-02-03",
                "242-434",
                12
        );
        creditBook.semesters.addSemester(1);
        assertEquals(0, creditBook.semesters.getSubjectsBySemester(1).size());
    }

    @Test
    void checkRemoveSemester() throws Exception {
        Student student = new Student(
                "Testov Test Testovich"
        );
        Speciality speciality = new Speciality("123-4", "Testovaya Specialnost");
        CreditBook creditBook = new CreditBook(
                student,
                "FIT",
                speciality,
                "2022-02-03",
                "242-434",
                12
        );
        creditBook.semesters.addSemester(1);
        creditBook.semesters.removeSemester(1);
        assertEquals(0, creditBook.semesters.semestersMap.size());
    }

    @Test
    public void testSubject() {
        assertEquals("Discrete Mathematics", this.creditBook.semesters.getSubjectsBySemester(1).get(0).name);
        assertEquals(5, this.creditBook.semesters.getSubjectsBySemester(1).get(0).mark);
        assertEquals("11.01.2023", this.creditBook.semesters.getSubjectsBySemester(1).get(0).dateOfExam);
        assertEquals(1, this.creditBook.semesters.getSubjectsBySemester(1).get(0).semesterNumber);
        assertEquals("Stukachev A.I.", this.creditBook.semesters.getSubjectsBySemester(1).get(0).teacherCredentials);
    }

    @Test
    public void testAveragePoint() {
        double averagePoint = this.creditBook.calculateAveragePoint();
        double expectedAveragePoint = ((5.0 + 4.0 + 4.0 + 5.0 + 5.0 + 4.0 + 5.0 + 5.0) / 8);
        assertEquals(expectedAveragePoint, averagePoint);
    }

    @Test
    public void testAddOrChangeMarkForSubject() throws Exception {
        this.creditBook.addOrChangeMarkForSubject("Discrete Mathematics", 4,
                "11.01.2023", "Stukachev A.I.", 1
        );

        assertEquals(4, this.creditBook.semesters.getSubjectsBySemester(1).get(0).mark);
    }

    @Test
    public void testAddOrChangeMarkForSubjectThrowsException() {
        assertThrows(Exception.class, () -> this.creditBook.addOrChangeMarkForSubject(
                "Discrete Mathematics",
                6,
                "11.01.2023",
                "Stukachev A.I.",
                1
        ));
    }

    @Test
    public void testHasRedDiplomaFalse() {
        assertFalse(this.creditBook.hasRedDiploma());
    }

    @Test
    public void testHasRedDiploma() throws Exception {
        this.creditBook.addOrChangeMarkForSubject(
                "Discrete Mathematics",
                5,
                "11.01.2023",
                "Stukachev A.I.",
                1
        );

        this.creditBook.addOrChangeMarkForSubject(
                "English",
                5,
                "09.06.2023",
                "Savilova T.K.",
                2
        );
        this.creditBook.addOrChangeMarkForSubject(
                "Mathematical Analysis",
                5,
                "09.06.2023",
                "Vaskevich V.L.",
                3
        );
        this.creditBook.addOrChangeMarkForSubject(
                "Computer Linguistics",
                5,
                "20.01.2026",
                "Smirnova V.V.",
                6
        );

        assertTrue(this.creditBook.hasRedDiploma());
    }

    @Test
    public void testSubjectMarksList() throws Exception {
        this.creditBook.addOrChangeMarkForSubject(
                "Mathematical Analysis",
                5,
                "09.06.2023",
                "Vaskevich V.L.",
                5
        );

        assertEquals(2, this.creditBook.getSubjectMarks("Mathematical Analysis").size());
    }

    @Test
    public void testCanGetUpperScholarship() {
        assertTrue(this.creditBook.canGetUpperScholarship(1));
    }

    @Test
    public void testCantGetUpperScholarship() {
        assertTrue(this.creditBook.canGetUpperScholarship(3));
    }
}
