package com.openclassrooms.msdiabete.service;

import com.openclassrooms.msdiabete.util.DiabeteRiskLevel;

/**
 * Interface IDiabeteService
 * Provides methods for DiabeteService.
 */
public interface IDiabeteService {

    DiabeteRiskLevel evaluateDiabeteRiskLevel(Long patientId);

}
