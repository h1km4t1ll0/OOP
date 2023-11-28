package ru.nsu.dolgov.creditbook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Class for semesters implementation.
 */
public class Semesters {
    Map<Integer, List<Subject>> semestersMap;
    Integer maxSemesterNumber;

    Semesters(Integer maxSemesterNumber) {
        this.semestersMap = new HashMap<>();
        this.maxSemesterNumber = maxSemesterNumber;
    }

    /**
     * Method to add a subject to the semester.
     *
     * @param semester int representation of the semester e.g. 1, 2, etc.
     * @param subject  subject instance to be added.
     */
    void addSubjectToSemester(Integer semester, Subject subject) {
        this.semestersMap.get(semester).add(subject);
    }

    /**
     * Method to remove a subject from the semester.
     *
     * @param semester int representation of the semester e.g. 1, 2, etc.
     * @param subject  subject instance to be removed.
     */
    void removeSubjectFromSemester(Integer semester, Subject subject) {
        this.semestersMap.get(semester).remove(subject);
    }

    /**
     * Method to add a new semester.
     *
     * @param semesterNumber int representation of the semester e.g. 1, 2, etc.
     */
    void addSemester(Integer semesterNumber) throws Exception {
        if (this.semestersMap.containsKey(semesterNumber)
                || semesterNumber > this.maxSemesterNumber) {
            throw new Exception("Semester already exists!");
        }

        this.semestersMap.put(semesterNumber, new ArrayList<>());
    }

    /**
     * Method to remove a semester.
     *
     * @param semesterNumber int representation of the semester e.g. 1, 2, etc.
     */
    void removeSemester(Integer semesterNumber) throws Exception {
        if (!this.semestersMap.containsKey(semesterNumber)) {
            throw new Exception("Can't find a semester by provided semester number!");
        }
        this.semestersMap.remove(semesterNumber);
    }

    /**
     * Get all subjects.
     *
     * @return list of all subjects.
     */
    List<Subject> getAllSubjects() {
        Set<Integer> keys = this.semestersMap.keySet();
        List<Subject> allSubjects = new ArrayList<>();
        for (Integer key : keys) {
            allSubjects.addAll(this.semestersMap.get(key));
        }
        return allSubjects;
    }

    /**
     * Get stream of all subjects.
     *
     * @return stream of all subjects.
     */
    Stream<Subject> getStreamOfAllSubjects() {
        return this.getAllSubjects().stream();
    }

    /**
     * Getter for subjects.
     *
     * @param semester int representation of the semester e.g. 1, 2, etc.
     * @return list of all subjects in provided semester.
     */
    List<Subject> getSubjectsBySemester(Integer semester) {
        return this.semestersMap.get(semester);
    }

    /**
     * Getter for subjects.
     *
     * @param subjectName name of the subject.
     * @return list of all subjects with provided name.
     */
    List<Subject> getSubjectsByName(String subjectName) {
        return this.getStreamOfAllSubjects().filter(subject -> subject.name.equals(subjectName)).toList();
    }

    /**
     * Getter for subjects names.
     *
     * @return list of all distinct subject names.
     */
    List<String> getSubjectNames() {
        return this.getStreamOfAllSubjects().map(subject -> subject.name).distinct().toList();
    }
}
