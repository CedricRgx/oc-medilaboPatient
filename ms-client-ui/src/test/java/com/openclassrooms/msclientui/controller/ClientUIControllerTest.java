package com.openclassrooms.msclientui.controller;

import com.openclassrooms.msclientui.exception.NoteNotFoundException;
import com.openclassrooms.msclientui.model.Note;
import com.openclassrooms.msclientui.model.Patient;
import com.openclassrooms.msclientui.service.ClientUIService;
import com.openclassrooms.msclientui.util.CustomPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientUIControllerTest {

    @Mock
    private ClientUIService clientUIService;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private ClientUIController clientUIController;

    @Test
    public void testGetAllPatients() {
        // Arrange
        int page = 0;
        int size = 5;
        List<Patient> patientsList = List.of(new Patient(), new Patient());
        CustomPage<Patient> patientsPage = new CustomPage<>(patientsList, 2, 1);

        when(clientUIService.getPatientsList(page, size)).thenReturn(patientsPage);

        // Act
        String result = clientUIController.getAllPatients(model, page, size);

        // Assert
        assertEquals("home", result);
        verify(model).addAttribute("patientslist", patientsPage.getContent());
        verify(model).addAttribute("pages", patientsPage.getTotalPages());
        verify(model).addAttribute("currentPage", patientsPage.getCurrentPage());
        verify(model).addAttribute("pageSize", size);
    }

    @Test
    public void testShowPatientsList() {
        // Act
        String result = clientUIController.showPatientsList();

        // Assert
        assertEquals("redirect:/home", result);
    }

    @Test
    public void testGetPatientById_PatientExists() {
        // Arrange
        Long id = 1L;
        Patient patient = new Patient();
        patient.setId(id);
        List<Note> notes = List.of(new Note(), new Note());
        when(clientUIService.getPatientById(id)).thenReturn(patient);
        when(clientUIService.getNotesByPatientId(id)).thenReturn(notes);

        // Act
        String result = clientUIController.getPatientById(id, model, redirectAttributes);

        // Assert
        assertEquals("patient", result);
        verify(model).addAttribute("patient", patient);
        verify(model).addAttribute("notes", notes);
    }

    @Test
    public void testGetPatientById_PatientNotFound() {
        // Arrange
        Long id = 1L;
        when(clientUIService.getPatientById(id)).thenReturn(null);

        // Act
        String result = clientUIController.getPatientById(id, model, redirectAttributes);

        // Assert
        assertEquals("redirect:/home", result);
        verify(redirectAttributes).addFlashAttribute("errorFoundPatientMessage", "This patient doesn't exist.");
    }

    @Test
    public void testAddPatientForm() {
        // Act
        String result = clientUIController.addPatientForm(model);

        // Assert
        assertEquals("addpatient", result);
        verify(model).addAttribute(eq("patient"), any(Patient.class));
    }

    @Test
    public void testSavePatient_Success() {
        // Arrange
        Patient patient = new Patient();
        patient.setId(1L);
        when(clientUIService.savePatient(patient)).thenReturn(patient);
        when(bindingResult.hasErrors()).thenReturn(false);

        // Act
        String result = clientUIController.savePatient(patient, bindingResult, model, redirectAttributes);

        // Assert
        assertEquals("redirect:/patient/" + patient.getId(), result);
        verify(redirectAttributes).addFlashAttribute("successAddPatientMessage", "Success to add the patient.");
    }

    @Test
    public void testSavePatient_ValidationError() {
        // Arrange
        Patient patient = new Patient();
        when(bindingResult.hasErrors()).thenReturn(true);

        // Act
        String result = clientUIController.savePatient(patient, bindingResult, model, redirectAttributes);

        // Assert
        assertEquals("addpatient", result);
        verify(model).addAttribute("patient", patient);
    }

    @Test
    public void testDeletePatient_Success() {
        // Arrange
        Long id = 1L;
        when(clientUIService.deletePatient(id)).thenReturn(true);

        // Act
        String result = clientUIController.deletePatient(id, redirectAttributes);

        // Assert
        assertEquals("redirect:/home", result);
        verify(redirectAttributes).addFlashAttribute("successDeletePatientMessage", "Success to delete the patient.");
    }

    @Test
    public void testDeletePatient_Failure() {
        // Arrange
        Long id = 1L;
        when(clientUIService.deletePatient(id)).thenReturn(false);

        // Act
        String result = clientUIController.deletePatient(id, redirectAttributes);

        // Assert
        assertEquals("redirect:/home", result);
        verify(redirectAttributes).addFlashAttribute("errorDeletePatientMessage", "Unable to delete the patient. Please try again.");
    }

    @Test
    public void testUpdatePatient_ValidationErrors() {
        // Arrange
        Patient patient = new Patient();
        when(bindingResult.hasErrors()).thenReturn(true);

        // Act
        String result = clientUIController.updatePatient(patient, bindingResult, model, redirectAttributes);

        // Assert
        assertEquals("editpatient", result);
        verify(model).addAttribute("patient", patient);
        verifyNoInteractions(clientUIService);
    }

    @Test
    public void testUpdatePatient_Success() {
        // Arrange
        Patient patient = new Patient();
        patient.setId(1L);
        when(bindingResult.hasErrors()).thenReturn(false);
        when(clientUIService.savePatient(patient)).thenReturn(patient);

        // Act
        String result = clientUIController.updatePatient(patient, bindingResult, model, redirectAttributes);

        // Assert
        assertEquals("redirect:/patient/" + patient.getId(), result);
        verify(clientUIService).savePatient(patient);
        verify(redirectAttributes).addFlashAttribute("successUpdatePatientMessage", "Success to update the patient.");
    }

    @Test
    public void testUpdatePatient_Exception() {
        // Arrange
        Patient patient = new Patient();
        patient.setId(1L);
        when(bindingResult.hasErrors()).thenReturn(false);
        when(clientUIService.savePatient(patient)).thenThrow(new RuntimeException("Error during save"));

        // Act
        String result = clientUIController.updatePatient(patient, bindingResult, model, redirectAttributes);

        // Assert
        assertEquals("redirect:/patient/" + patient.getId(), result);
        verify(clientUIService).savePatient(patient);
        verify(redirectAttributes).addFlashAttribute("errorUpdatePatientMessage", "Failed to update the patient.");
    }

    @Test
    public void testEditPatientForm_PatientFound() {
        // Arrange
        Long patientId = 1L;
        Patient patient = new Patient();
        patient.setId(patientId);
        when(clientUIService.getPatientById(patientId)).thenReturn(patient);

        // Act
        String result = clientUIController.editPatientForm(patientId, model);

        // Assert
        assertEquals("editpatient", result);
        verify(model).addAttribute("patient", patient);
    }

    @Test
    public void testEditPatientForm_PatientNotFound() {
        // Arrange
        Long patientId = 1L;
        when(clientUIService.getPatientById(patientId)).thenReturn(null);

        // Act
        String result = clientUIController.editPatientForm(patientId, model);

        // Assert
        assertEquals("redirect:/home", result);
        verify(model, never()).addAttribute(anyString(), any());
    }

    @Test
    public void testSavePatient_Exception() {
        // Arrange
        Patient patient = new Patient();
        when(bindingResult.hasErrors()).thenReturn(false);
        when(clientUIService.savePatient(patient)).thenThrow(new RuntimeException("Error during save"));

        // Act
        String result = clientUIController.savePatient(patient, bindingResult, model, redirectAttributes);

        // Assert
        assertEquals("redirect:/home", result);
        verify(clientUIService).savePatient(patient);
        verify(redirectAttributes).addFlashAttribute("errorAddPatientMessage", "Failed to add the patient.");
    }

    @Test
    public void testGetPatientById_NoNotes() {
        // Arrange
        Long patientId = 1L;
        Patient patient = new Patient();
        patient.setId(patientId);
        when(clientUIService.getPatientById(patientId)).thenReturn(patient);
        when(clientUIService.getNotesByPatientId(patientId)).thenReturn(Collections.emptyList());

        // Act
        String result = clientUIController.getPatientById(patientId, model, redirectAttributes);

        // Assert
        assertEquals("patient", result);
        verify(model).addAttribute("patient", patient);
        verify(model, never()).addAttribute(eq("notes"), anyList());
        verify(clientUIService).getNotesByPatientId(patientId);
    }

    @Test
    public void testGetPatientById_NotesNotFoundException() {
        // Arrange
        Long patientId = 1L;
        Patient patient = new Patient();
        patient.setId(patientId);
        when(clientUIService.getPatientById(patientId)).thenReturn(patient);
        when(clientUIService.getNotesByPatientId(patientId)).thenThrow(new NoteNotFoundException("No notes found"));

        // Act
        String result = clientUIController.getPatientById(patientId, model, redirectAttributes);

        // Assert
        assertEquals("patient", result);
        verify(model).addAttribute("patient", patient);
        verify(clientUIService).getNotesByPatientId(patientId);
    }

    @Test
    public void testGetPatientById_DiabetesRiskLevelPresent() {
        // Arrange
        Long patientId = 1L;
        Patient patient = new Patient();
        patient.setId(patientId);
        String diabetesRiskLevel = "IN_DANGER";
        when(clientUIService.getPatientById(patientId)).thenReturn(patient);
        when(clientUIService.getNotesByPatientId(patientId)).thenReturn(Collections.emptyList());
        when(clientUIService.getDiabetesRiskLevel(patientId)).thenReturn(diabetesRiskLevel);

        // Act
        String result = clientUIController.getPatientById(patientId, model, redirectAttributes);

        // Assert
        assertEquals("patient", result);
        verify(model).addAttribute("patient", patient);
        verify(model).addAttribute("diabetesRiskLevel", diabetesRiskLevel);
    }

    @Test
    public void testGetPatientById_DiabetesRiskLevelAbsent() {
        // Arrange
        Long patientId = 1L;
        Patient patient = new Patient();
        patient.setId(patientId);
        when(clientUIService.getPatientById(patientId)).thenReturn(patient);
        when(clientUIService.getNotesByPatientId(patientId)).thenReturn(Collections.emptyList());
        when(clientUIService.getDiabetesRiskLevel(patientId)).thenReturn("");

        // Act
        String result = clientUIController.getPatientById(patientId, model, redirectAttributes);

        // Assert
        assertEquals("patient", result);
        verify(model).addAttribute("patient", patient);
        verify(model, never()).addAttribute(eq("diabetesRiskLevel"), anyString());
    }

    @Test
    public void testGetPatientById_DiabetesRiskLevelException() {
        // Arrange
        Long patientId = 1L;
        Patient patient = new Patient();
        patient.setId(patientId);
        when(clientUIService.getPatientById(patientId)).thenReturn(patient);
        when(clientUIService.getNotesByPatientId(patientId)).thenReturn(Collections.emptyList());
        when(clientUIService.getDiabetesRiskLevel(patientId)).thenThrow(new RuntimeException("Error during diabetes risk retrieval"));

        // Act
        String result = clientUIController.getPatientById(patientId, model, redirectAttributes);

        // Assert
        assertEquals("patient", result);
        verify(model).addAttribute("patient", patient);
        verify(clientUIService).getDiabetesRiskLevel(patientId);
    }

    @Test
    public void testAddNoteForm_PatientFound() {
        // Arrange
        Long patientId = 1L;
        Patient patient = new Patient();
        patient.setId(patientId);
        when(clientUIService.getPatientById(patientId)).thenReturn(patient);

        // Act
        String result = clientUIController.addNoteForm(patientId, model);

        // Assert
        assertEquals("addnote", result);
        verify(model).addAttribute("patient", patient);
        verify(model).addAttribute(eq("note"), any(Note.class));
    }

    @Test
    public void testAddNoteForm_PatientNotFound() {
        // Arrange
        Long patientId = 1L;
        when(clientUIService.getPatientById(patientId)).thenReturn(null);

        // Act
        String result = clientUIController.addNoteForm(patientId, model);

        // Assert
        assertEquals("addnote", result);
        verify(model).addAttribute("patient", null);
        verify(model).addAttribute(eq("note"), any(Note.class));
    }

    @Test
    public void testSaveNote_Success() {
        // Arrange
        Note note = new Note();
        note.setPatId(1L);
        when(clientUIService.saveNote(note)).thenReturn(note);

        // Act
        String result = clientUIController.saveNote(note, redirectAttributes);

        // Assert
        assertEquals("redirect:/patient/" + note.getPatId(), result);
        verify(clientUIService).saveNote(note);
        verify(redirectAttributes).addFlashAttribute("successAddNoteMessage", "Success to add the note.");
    }

    @Test
    public void testSaveNote_Exception() {
        // Arrange
        Note note = new Note();
        note.setPatId(1L);
        when(clientUIService.saveNote(note)).thenThrow(new RuntimeException("Error during save"));

        // Act
        String result = clientUIController.saveNote(note, redirectAttributes);

        // Assert
        assertEquals("redirect:/patient/" + note.getPatId(), result);
        verify(clientUIService).saveNote(note);
        verify(redirectAttributes).addFlashAttribute("errorAddNoteMessage", "Failed to add the note.");
    }

    @Test
    public void testEditNoteForm_NoteFound() {
        // Arrange
        String noteId = "1";
        Note note = new Note();
        note.setId(noteId);
        when(clientUIService.getNoteById(noteId)).thenReturn(note);

        // Act
        String result = clientUIController.editNoteForm(noteId, model);

        // Assert
        assertEquals("editnote", result);
        verify(model).addAttribute("note", note);
    }

    @Test
    public void testEditNoteForm_NoteNotFound() {
        // Arrange
        String noteId = "1";
        when(clientUIService.getNoteById(noteId)).thenReturn(null);

        // Act
        String result = clientUIController.editNoteForm(noteId, model);

        // Assert
        assertEquals("redirect:/home", result);
        verify(model, never()).addAttribute(eq("note"), any(Note.class));
    }

    @Test
    public void testUpdateNote_Success() {
        // Arrange
        String noteId = "1";
        Note note = new Note();
        note.setPatId(1L);
        when(clientUIService.updateNote(noteId, note)).thenReturn(note);

        // Act
        String result = clientUIController.updateNote(noteId, note, redirectAttributes);

        // Assert
        assertEquals("redirect:/patient/" + note.getPatId(), result);
        verify(clientUIService).updateNote(noteId, note);
        verify(redirectAttributes).addFlashAttribute("successUpdateNoteMessage", "Successfully updated the note.");
    }

    @Test
    public void testUpdateNote_Exception() {
        // Arrange
        String noteId = "1";
        Note note = new Note();
        note.setPatId(1L);
        when(clientUIService.updateNote(noteId, note)).thenThrow(new RuntimeException("Error during update"));

        // Act
        String result = clientUIController.updateNote(noteId, note, redirectAttributes);

        // Assert
        assertEquals("redirect:/patient/" + note.getPatId(), result);
        verify(clientUIService).updateNote(noteId, note);
        verify(redirectAttributes).addFlashAttribute("errorUpdateNoteMessage", "Failed to update the note.");
    }

    @Test
    public void testDeleteNote_Success() {
        // Arrange
        String noteId = "1";
        Note note = new Note();
        note.setPatId(1L);
        when(clientUIService.getNoteById(noteId)).thenReturn(note);
        when(clientUIService.deleteNoteById(noteId)).thenReturn(true);

        // Act
        String result = clientUIController.deleteNote(noteId, redirectAttributes);

        // Assert
        assertEquals("redirect:/patient/" + note.getPatId(), result);
        verify(clientUIService).deleteNoteById(noteId);
        verify(redirectAttributes).addFlashAttribute("successDeleteNoteMessage", "Success to delete the note.");
    }

    @Test
    public void testDeleteNote_Failure() {
        // Arrange
        String noteId = "1";
        Note note = new Note();
        note.setPatId(1L);
        when(clientUIService.getNoteById(noteId)).thenReturn(note);
        when(clientUIService.deleteNoteById(noteId)).thenReturn(false);

        // Act
        String result = clientUIController.deleteNote(noteId, redirectAttributes);

        // Assert
        assertEquals("redirect:/patient/" + note.getPatId(), result);
        verify(clientUIService).deleteNoteById(noteId);
        verify(redirectAttributes).addFlashAttribute("errorDeleteNoteMessage", "Unable to delete the note. Please try again.");
    }

    @Test
    public void testDeleteNote_NoteNotFound() {
        // Arrange
        String noteId = "1";
        when(clientUIService.getNoteById(noteId)).thenReturn(null);

        // Act
        String result = clientUIController.deleteNote(noteId, redirectAttributes);

        // Assert
        assertEquals("redirect:/home", result);
        verify(redirectAttributes).addFlashAttribute("errorDeleteNoteMessage", "Note not found. Unable to delete.");
        verify(clientUIService, never()).deleteNoteById(anyString());
    }

}
