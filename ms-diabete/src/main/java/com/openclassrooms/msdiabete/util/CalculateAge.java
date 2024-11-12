package com.openclassrooms.msdiabete.util;

import java.time.LocalDate;
import java.time.Period;

public class CalculateAge {

    public int getAge(LocalDate birthdate) {
        return Period.between(birthdate, LocalDate.now()).getYears();
    }
}
