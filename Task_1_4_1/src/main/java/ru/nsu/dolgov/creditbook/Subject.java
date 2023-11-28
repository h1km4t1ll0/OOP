package ru.nsu.dolgov.creditbook;

/**
 * Class for subject representation.
 */
public class Subject {
    String name;
    Integer mark;
    String dateOfExam;
    String teacherCredentials;
    Integer semesterNumber;

    /**
     * Subject constructor.
     *
     * @param name               name of the subject.
     * @param mark               mark for the subject.
     * @param dateOfExam         date of exam.
     * @param teacherCredentials teachers' credentials.
     * @param semesterNumber     int representation of the semester e.g. 1, 2, etc.
     */
    Subject(
            String name,
            Integer mark,
            String dateOfExam,
            String teacherCredentials,
            Integer semesterNumber
    ) {
        this.name = name;
        this.mark = mark;
        this.dateOfExam = dateOfExam;
        this.teacherCredentials = teacherCredentials;
        this.semesterNumber = semesterNumber;
    }
}
