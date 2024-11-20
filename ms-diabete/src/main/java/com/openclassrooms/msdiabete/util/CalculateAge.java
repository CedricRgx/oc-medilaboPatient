package com.openclassrooms.msdiabete.util;

import java.time.LocalDate;
import java.time.Period;

/**
 * Utility class for calculating a person's age based on their birthdate.
 */
public class CalculateAge {

    /**
     * Checks if a person is older than 30 years old.
     *
     * @param birthdate The person's birthdate.
     * @return true if the person is older than 30, false otherwise.
     */
    public boolean isOlderThan30(LocalDate birthdate) {
        boolean isOlderThan30 = false;
        if (Period.between(birthdate, LocalDate.now()).getYears() > 30) {
            isOlderThan30 = true;
        }
        return isOlderThan30;
    }
}
