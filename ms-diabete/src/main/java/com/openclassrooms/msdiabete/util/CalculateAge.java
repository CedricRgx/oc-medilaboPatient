package com.openclassrooms.msdiabete.util;

import java.time.LocalDate;
import java.time.Period;

/**
 * Utility class for calculating a person's age based on their birthdate.
 */
public class CalculateAge {

    /**
     * Calculates the age in years from the given birthdate to the current date.
     *
     * @param birthdate the birthdate of the individual
     * @return the age in years
     */
    public int getAge(LocalDate birthdate) {
        return Period.between(birthdate, LocalDate.now()).getYears();
    }
}
