package ru.nsu.dolgov.creditbook;

import java.util.*;


/**
 * Class for the credit book representation.
 */
public class CreditBook {
    Student student;
    String faculty;
    Speciality speciality;
    String dateOfCredit;
    String orderNumber;
    Map<Integer, List<Subject>> semesters;
    Integer maxSemesterNumber;

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
        this.semesters = new HashMap<>();
        this.maxSemesterNumber = maxSemesterNumber;
    }

    /**
     * Method to add a subject to the semester.
     *
     * @param semester int representation of the semester e.g. 1, 2, etc.
     * @param subject  subject instance to be added.
     */
    void addSubjectToSemester(Integer semester, Subject subject) {
        this.semesters.get(semester).add(subject);
    }

    /**
     * Method to remove a subject from the semester.
     *
     * @param semester int representation of the semester e.g. 1, 2, etc.
     * @param subject  subject instance to be removed.
     */
    void removeSubjectFromSemester(Integer semester, Subject subject) {
        this.semesters.get(semester).remove(subject);
    }

    /**
     * Method to add a new semester.
     *
     * @param semesterNumber int representation of the semester e.g. 1, 2, etc.
     */
    void addSemester(Integer semesterNumber) throws Exception {
        if (this.semesters.containsKey(semesterNumber) || semesterNumber > maxSemesterNumber) {
            throw new Exception("Semester already exists!");
        }

        this.semesters.put(semesterNumber, new ArrayList<>());
    }

    /**
     * Method to remove a semester.
     *
     * @param semesterNumber int representation of the semester e.g. 1, 2, etc.
     */
    void removeSemester(Integer semesterNumber) throws Exception {
        if (!this.semesters.containsKey(semesterNumber)) {
            throw new Exception("Can't find a semester by provided semester number!");
        }
        this.semesters.remove(semesterNumber);
    }

    /**
     * Method for calculating the average point of the student.
     *
     * @return average point.
     */
    public double calculateAveragePoint() {
        Set<Integer> keys = this.semesters.keySet();
        List<Subject> allSubjects = new ArrayList<>();
        for (Integer key : keys) {
            allSubjects.addAll(this.semesters.get(key));
        }

        List<Subject> lastSubjects = new ArrayList<>();

        for (Subject subject : allSubjects) {
            boolean subjectFound = false;
            for (int i = lastSubjects.size() - 1; i >= 0; i--) {
                if (lastSubjects.get(i).name.equals(subject.name)
                        && lastSubjects.get(i).semesterNumber.equals(subject.semesterNumber)) {
                    lastSubjects.set(i, subject);
                    subjectFound = true;
                    break;
                }
            }
            if (!subjectFound) {
                lastSubjects.add(subject);
            }
        }

        double totalPoints = 0;
        int totalMarks = 0;

        for (Subject subject : lastSubjects) {
            Integer mark = subject.mark;
            totalPoints += mark;
            totalMarks += 1;
        }

        return totalMarks > 0 ? totalPoints / totalMarks : 0;
    }

    /**
     * Method to calculate whether student can get upper scholarship or not.
     *
     * @param currentSemester int representation of the semester e.g. 1, 2, etc.
     * @return boolean value.
     */
    boolean canGetUpperScholarship(Integer currentSemester) {
        List<Subject> currentSubjects = this.semesters.get(currentSemester);

        for (Subject subject : currentSubjects) {
            if (subject.mark <= 3) {
                return false;
            }
        }

        return true;
    }

    /**
     * Method to add
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
        for (Subject existingGrade : this.semesters.get(semester)) {
            if (existingGrade.name.equals(subjectName) &&
                    existingGrade.semesterNumber.equals(semester)) {
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
            this.semesters.get(semester).add(newSubject);
        }
    }

    /**
     * Method to get list of subjects with its' marks.
     *
     * @param subjectName subject name.
     * @return list of subjects
     */
    public List<Subject> getSubjectMarks(String subjectName) {
        List<Subject> subjectGrades = new ArrayList<>();

        Set<Integer> keys = this.semesters.keySet();
        for (Integer key : keys) {
            for (Subject subject : this.semesters.get(key)) {
                if (subject.name.equals(subjectName)) {
                    subjectGrades.add(subject);
                }
            }
        }

        return subjectGrades;
    }

    /**
     * Method to determine whether student can achieve red diploma or not.
     * @return true if red diploma available else false.
     */
    public boolean hasRedDiploma() {
        Set<Integer> keys = this.semesters.keySet();
        List<Subject> allSubjects = new ArrayList<>();
        for (Integer key : keys) {
            allSubjects.addAll(this.semesters.get(key));
        }

        int excellentCount = 0;
        int satisfactoryCount = 0;
        boolean hasExcellentQualificationWork = false;
        int totalSubjects = 0;

        for (Subject subject : allSubjects) {
            if (subject.mark.equals(5)) {
                excellentCount++;
            } else if (subject.mark.equals(3)) {
                satisfactoryCount++;
            }

            if (subject.name.equals("Квалификационная работа") && subject.mark.equals(5)) {
                hasExcellentQualificationWork = true;
            }

            totalSubjects++;
        }

        double percentageExcellent = ((double) excellentCount / totalSubjects) * 100;
        boolean hasExcellentGrades = percentageExcellent >= 75;
        boolean hasSatisfactoryFinalGrades = satisfactoryCount > 0;

        return hasExcellentGrades && hasExcellentQualificationWork && !hasSatisfactoryFinalGrades;
    }
}
