package ru.nsu.dolgov.creditbook;

import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;


/**
 * Class for the credit book representation.
 */
public class CreditBook {
    Student student;
    String faculty;
    Speciality speciality;
    String dateOfCredit;
    String orderNumber;
    Semesters semesters;

    /**
     * Base constructor.
     *
     * @param student      owner of the credit book.
     * @param faculty      faculty of the owner.
     * @param speciality   speciality of the owner.
     * @param dateOfCredit date of issue.
     * @param orderNumber  order number of the credit book.
     */
    CreditBook(
            Student student,
            String faculty,
            Speciality speciality,
            String dateOfCredit,
            String orderNumber,
            Integer maxSemesterNumber
    ) {
        this.student = student;
        this.faculty = faculty;
        this.speciality = speciality;
        this.dateOfCredit = dateOfCredit;
        this.orderNumber = orderNumber;
        this.semesters = new Semesters(maxSemesterNumber);
    }

    /**
     * Method for calculating the average point of the student.
     *
     * @return average point.
     */
    public double calculateAveragePoint() {
        List<Subject> lastSubjects = this.semesters.getSubjectNames()
                .stream()
                .map(
                        subjectName -> this.semesters.getSubjectsByName(subjectName)
                                .stream()
                                .sorted(Comparator.comparing(subject -> subject.semesterNumber))
                                .toList())
                .toList()
                .stream()
                .map(each -> each.get(each.size() - 1))
                .toList();

        IntStream def = lastSubjects.stream().mapToInt((Subject subject) -> subject.mark);
        this.semesters.getStreamOfAllSubjects();
        double totalPoints = def.sum();
        int totalMarks = lastSubjects.size();

        return totalMarks > 0 ? totalPoints / totalMarks : 0;
    }

    /**
     * Method to calculate whether student can get upper scholarship or not.
     *
     * @param currentSemester int representation of the semester e.g. 1, 2, etc.
     * @return boolean value.
     */
    boolean canGetUpperScholarship(Integer currentSemester) {
        return this.semesters.getSubjectsBySemester(currentSemester)
                .stream()
                .noneMatch(
                        (Subject subject) -> subject.mark <= 3
                );
    }

    /**
     * Method to add or change subject mark.
     *
     * @param subjectName        subject name.
     * @param mark               mark.
     * @param dateOfExam         date of exam.
     * @param semester           int representation of the semester e.g. 1, 2, etc.
     * @param teacherCredentials teachers' credentials.
     */
    public void addOrChangeMarkForSubject(
            String subjectName,
            Integer mark,
            String dateOfExam,
            String teacherCredentials,
            Integer semester
    ) throws Exception {
        if (mark > 5 || mark < 2) {
            throw new Exception("Incorrect mark!");
        }

        boolean subjectFoundFlag = false;
        for (Subject existingGrade : this.semesters.getSubjectsBySemester(semester)) {
            if (existingGrade.name.equals(subjectName)
                    && existingGrade.semesterNumber.equals(semester)) {
                existingGrade.mark = mark;
                existingGrade.dateOfExam = dateOfExam;
                existingGrade.teacherCredentials = teacherCredentials;
                subjectFoundFlag = true;
                break;
            }
        }

        if (!subjectFoundFlag) {
            Subject newSubject = new Subject(
                    subjectName,
                    mark,
                    dateOfExam,
                    teacherCredentials,
                    semester
            );
            this.semesters.addSubjectToSemester(semester, newSubject);
        }
    }

    /**
     * Method to get list of subjects with its' marks.
     *
     * @param subjectName subject name.
     * @return list of subjects
     */
    public List<Subject> getSubjectMarks(String subjectName) {
        return this.semesters.getStreamOfAllSubjects().filter(
                (Subject subject) -> subject.name.equals(subjectName)
        ).toList();
    }

    /**
     * Method to determine whether student can achieve red diploma or not.
     */
    public boolean hasRedDiploma() {
        int totalSubjects = this.semesters.getAllSubjects().size();
        int excellentCount = (int) this.semesters.getStreamOfAllSubjects().filter(
                (Subject subject) -> subject.mark.equals(5)
        ).count();
        int satisfactoryCount = (int) this.semesters.getStreamOfAllSubjects().filter(
                (Subject subject) -> subject.mark.equals(3)
        ).count();
        boolean hasExcellentQualificationWork = this.semesters.getStreamOfAllSubjects().anyMatch(
                (Subject subject) -> subject.name.equals("Квалификационная работа")
                        && subject.mark.equals(5)
        );

        double percentageExcellent = ((double) excellentCount / totalSubjects) * 100;
        boolean hasExcellentGrades = percentageExcellent >= 75;
        boolean hasSatisfactoryFinalGrades = satisfactoryCount > 0;

        return hasExcellentGrades && hasExcellentQualificationWork && !hasSatisfactoryFinalGrades;
    }
}
