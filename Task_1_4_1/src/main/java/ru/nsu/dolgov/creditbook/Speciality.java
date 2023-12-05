package ru.nsu.dolgov.creditbook;

/**
 * Class for speciality representation.
 */
public class Speciality {
    String specialityCode;
    String specialityName;

    /**
     * Speciality constructor.
     *
     * @param specialityCode speciality code.
     * @param specialityName speciality name.
     */
    Speciality(
            String specialityCode,
            String specialityName
    ) {
        this.specialityCode = specialityCode;
        this.specialityName = specialityName;
    }
}
