package com.openclassrooms.msclientui.service;

import com.openclassrooms.msclientui.exception.NoteNotFoundException;
import com.openclassrooms.msclientui.exception.PatientNotFoundException;
import com.openclassrooms.msclientui.model.Note;
import com.openclassrooms.msclientui.model.Patient;
import com.openclassrooms.msclientui.proxy.FeignClient;
import com.openclassrooms.msclientui.util.CustomPage;
import feign.FeignException;
import feign.Request;
import feign.RequestTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientUIServiceTest {

    @Mock
    private FeignClient feignClient;

    @InjectMocks
    private ClientUIService clientUIService;

    private Patient testPatient;
    private Note testNote;

    @BeforeEach
    public void setUp() {
        testPatient = new Patient();
        testPatient.setId(1L);
        testPatient.setFirstname("John");
        testPatient.setLastname("Doe");

        testNote = new Note();
        testNote.setId("1");
        testNote.setPatId(1L);
        testNote.setCreationDate(LocalDate.now());
        testNote.setUpdateDate(LocalDate.now());
        testNote.setNote("Sample note content.");
    }

    @Test
    public void testGetPatientsList_NormalPagination() {
        // Arrange
        List<Patient> patients = Arrays.asList(new Patient(), new Patient(), new Patient(), new Patient());
        when(feignClient.getPatientsList()).thenReturn(patients);

        int page = 0;
        int size = 2;

        // Act
        CustomPage<Patient> result = clientUIService.getPatientsList(page, size);

        // Assert
        assertEquals(2, result.getContent().size());
        assertEquals(2, result.getTotalPages());
        assertEquals(0, result.getCurrentPage());
    }

    @Test
    public void testGetPatientsList_PageExceedsContent() {
        // Arrange
        List<Patient> patients = Arrays.asList(new Patient(), new Patient());
        when(feignClient.getPatientsList()).thenReturn(patients);

        int page = 1;
        int size = 3;

        // Act
        CustomPage<Patient> result = clientUIService.getPatientsList(page, size);

        // Assert
        assertEquals(0, result.getContent().size());
        assertEquals(1, result.getTotalPages());
        assertEquals(1, result.getCurrentPage());
    }

    @Test
    public void testGetPatientsList_EmptyList() {
        // Arrange
        when(feignClient.getPatientsList()).thenReturn(Collections.emptyList());

        int page = 0;
        int size = 2;

        // Act
        CustomPage<Patient> result = clientUIService.getPatientsList(page, size);

        // Assert
        assertEquals(0, result.getContent().size());
        assertEquals(0, result.getTotalPages());
        assertEquals(0, result.getCurrentPage());
    }

    @Test
    public void testGetPatientsList_PageSizeExceedsTotalPatients() {
        // Arrange
        List<Patient> patients = Arrays.asList(new Patient(), new Patient());
        when(feignClient.getPatientsList()).thenReturn(patients);

        int page = 0;
        int size = 10;

        // Act
        CustomPage<Patient> result = clientUIService.getPatientsList(page, size);

        // Assert
        assertEquals(2, result.getContent().size());
        assertEquals(1, result.getTotalPages());
        assertEquals(0, result.getCurrentPage());
    }

    @Test
    public void testGetPatientById_PatientFound() {
        // Arrange
        Long patientId = 1L;
        Patient patient = new Patient();
        patient.setId(patientId);
        when(feignClient.getPatientById(patientId)).thenReturn(patient);

        // Act
        Patient result = clientUIService.getPatientById(patientId);

        // Assert
        assertEquals(patient, result);
    }

    @Test
    public void testGetPatientById_PatientNotFound() {
        // Arrange
        Long patientId = 1L;
        when(feignClient.getPatientById(patientId)).thenReturn(null);

        // Act & Assert
        assertThrows(PatientNotFoundException.class, () -> clientUIService.getPatientById(patientId));
    }

    @Test
    public void testGetPatientById_InternalServerError() {
        // Arrange
        Long patientId = 1L;
        FeignException.InternalServerError internalServerError = new FeignException.InternalServerError(
                "Internal Server Error",
                Request.create(
                        Request.HttpMethod.GET,
                        "/patients/" + patientId,
                        Collections.emptyMap(),
                        Request.Body.empty(),
                        new RequestTemplate()
                ),
                null,
                Collections.emptyMap()
        );
        when(feignClient.getPatientById(patientId)).thenThrow(internalServerError);

        // Act
        Patient result = clientUIService.getPatientById(patientId);

        // Assert
        assertNull(result);
    }

    @Test
    public void testGetPatientById_FeignException() {
        // Arrange
        Long patientId = 1L;
        FeignException feignException = new FeignException.BadRequest(
                "Bad Request",
                Request.create(
                        Request.HttpMethod.GET,
                        "/patients/" + patientId,
                        Collections.emptyMap(),
                        Request.Body.empty(),
                        new RequestTemplate()
                ),
                null,
                Collections.emptyMap()
        );
        when(feignClient.getPatientById(patientId)).thenThrow(feignException);

        // Act
        Patient result = clientUIService.getPatientById(patientId);

        // Assert
        assertNull(result);
    }

    @Test
    public void testGetPatientsList_Success() {
        // Arrange
        Patient patient1 = new Patient();
        patient1.setId(1L);
        patient1.setFirstname("John");
        patient1.setLastname("Doe");

        Patient patient2 = new Patient();
        patient2.setId(2L);
        patient2.setFirstname("Jane");
        patient2.setLastname("Smith");

        List<Patient> expectedPatients = Arrays.asList(patient1, patient2);
        when(feignClient.getPatientsList()).thenReturn(expectedPatients);

        // Act
        List<Patient> result = clientUIService.getPatientsList();

        // Assert
        assertEquals(expectedPatients, result);
        verify(feignClient, times(1)).getPatientsList();
    }

    @Test
    public void testGetPatientsList_Empty() {
        // Arrange
        List<Patient> expectedPatients = Arrays.asList();
        when(feignClient.getPatientsList()).thenReturn(expectedPatients);

        // Act
        List<Patient> result = clientUIService.getPatientsList();

        // Assert
        assertEquals(expectedPatients, result);
        verify(feignClient, times(1)).getPatientsList();
    }

    @Test
    public void testGetPatientsList_Exception() {
        // Arrange
        when(feignClient.getPatientsList()).thenThrow(new RuntimeException("Service Unavailable"));

        // Act & Assert
        try {
            clientUIService.getPatientsList();
        } catch (RuntimeException e) {
            assertEquals("Service Unavailable", e.getMessage());
        }
        verify(feignClient, times(1)).getPatientsList();
    }

    @Test
    public void testGetPatientById_OtherFeignException() {
        // Arrange
        Long patientId = 1L;
        FeignException.BadRequest badRequestException = new FeignException.BadRequest(
                "Bad Request",
                Request.create(
                        Request.HttpMethod.GET,
                        "/patients/" + patientId,
                        Collections.emptyMap(),
                        Request.Body.empty(),
                        new RequestTemplate()
                ),
                null,
                Collections.emptyMap()
        );
        when(feignClient.getPatientById(patientId)).thenThrow(badRequestException);

        // Act
        Patient result = clientUIService.getPatientById(patientId);

        // Assert
        assertNull(result);
        verify(feignClient, times(1)).getPatientById(patientId);
    }

    @Test
    public void testSavePatient_Success() {
        // Arrange
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setFirstname("John");
        patient.setLastname("Doe");

        when(feignClient.savePatient(patient)).thenReturn(patient);

        // Act
        Patient result = clientUIService.savePatient(patient);

        // Assert
        assertEquals(patient, result);
        verify(feignClient, times(1)).savePatient(patient);
    }

    @Test
    public void testSavePatient_BadRequest() {
        // Arrange
        Patient patient = new Patient();
        FeignException.BadRequest badRequestException = new FeignException.BadRequest(
                "Bad Request",
                Request.create(
                        Request.HttpMethod.POST,
                        "/patients",
                        Collections.emptyMap(),
                        Request.Body.empty(),
                        new RequestTemplate()
                ),
                null,
                Collections.emptyMap()
        );

        when(feignClient.savePatient(patient)).thenThrow(badRequestException);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> clientUIService.savePatient(patient));
        assertTrue(exception.getMessage().contains("Failed to save patient"));
        verify(feignClient, times(1)).savePatient(patient);
    }

    @Test
    public void testSavePatient_GeneralFeignException() {
        // Arrange
        Patient patient = new Patient();
        FeignException feignException = new FeignException.FeignClientException(
                500,
                "Internal Server Error",
                Request.create(
                        Request.HttpMethod.POST,
                        "/patients",
                        Collections.emptyMap(),
                        Request.Body.empty(),
                        new RequestTemplate()
                ),
                null,
                Collections.emptyMap()
        );

        when(feignClient.savePatient(patient)).thenThrow(feignException);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> clientUIService.savePatient(patient));
        assertTrue(exception.getMessage().contains("Failed to save patient"));
        verify(feignClient, times(1)).savePatient(patient);
    }

    @Test
    public void testSavePatient_GeneralException() {
        // Arrange
        Patient patient = new Patient();
        when(feignClient.savePatient(patient)).thenThrow(new RuntimeException("Database unavailable"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> clientUIService.savePatient(patient));
        assertTrue(exception.getMessage().contains("Failed to save patient"));
        verify(feignClient, times(1)).savePatient(patient);
    }

    @Test
    public void testDeletePatient_Success() {
        // Arrange
        Long patientId = 1L;
        when(feignClient.deletePatient(patientId)).thenReturn(true);

        // Act
        boolean result = clientUIService.deletePatient(patientId);

        // Assert
        assertTrue(result, "Expected deletePatient to return true when deletion is successful");
        verify(feignClient, times(1)).deletePatient(patientId);
    }

    @Test
    public void testDeletePatient_Failure() {
        // Arrange
        Long patientId = 1L;
        when(feignClient.deletePatient(patientId)).thenReturn(false);

        // Act
        boolean result = clientUIService.deletePatient(patientId);

        // Assert
        assertFalse(result, "Expected deletePatient to return false when deletion fails");
        verify(feignClient, times(1)).deletePatient(patientId);
    }

    @Test
    public void testGetAllNotes_Success() {
        // Arrange
        List<Note> expectedNotes = List.of(new Note(), new Note());
        when(feignClient.getNotesList()).thenReturn(expectedNotes);

        // Act
        List<Note> actualNotes = clientUIService.getAllNotes();

        // Assert
        assertEquals(expectedNotes, actualNotes, "Expected list of notes to match the list returned by Feign client");
        verify(feignClient, times(1)).getNotesList();
    }

    @Test
    public void testGetAllNotes_EmptyList() {
        // Arrange
        when(feignClient.getNotesList()).thenReturn(Collections.emptyList());

        // Act
        List<Note> actualNotes = clientUIService.getAllNotes();

        // Assert
        assertEquals(Collections.emptyList(), actualNotes, "Expected empty list when Feign client returns an empty list");
        verify(feignClient, times(1)).getNotesList();
    }

    @Test
    public void testGetNoteById_Found() {
        // Arrange
        String noteId = "1";
        Note expectedNote = new Note();
        expectedNote.setId(noteId);
        when(feignClient.getNoteById(noteId)).thenReturn(expectedNote);

        // Act
        Note actualNote = clientUIService.getNoteById(noteId);

        // Assert
        assertEquals(expectedNote, actualNote, "Expected note to match the note returned by Feign client");
        verify(feignClient, times(1)).getNoteById(noteId);
    }

    @Test
    public void testGetNoteById_NotFound() {
        // Arrange
        String noteId = "1";
        when(feignClient.getNoteById(noteId)).thenReturn(null);

        // Act & Assert
        assertThrows(NoteNotFoundException.class, () -> clientUIService.getNoteById(noteId), "Expected NoteNotFoundException when note is not found");
        verify(feignClient, times(1)).getNoteById(noteId);
    }

    @Test
    public void testGetNotesByPatientId_Found() {
        // Arrange
        Long patientId = 1L;
        List<Note> expectedNotes = List.of(new Note(), new Note());
        when(feignClient.getNotesByPatientId(patientId)).thenReturn(expectedNotes);

        // Act
        List<Note> actualNotes = clientUIService.getNotesByPatientId(patientId);

        // Assert
        assertEquals(expectedNotes, actualNotes, "Expected notes to match the notes returned by Feign client");
        verify(feignClient, times(1)).getNotesByPatientId(patientId);
    }

    @Test
    public void testGetNotesByPatientId_NotFound() {
        // Arrange
        Long patientId = 1L;
        when(feignClient.getNotesByPatientId(patientId)).thenThrow(FeignException.NotFound.class);

        // Act & Assert
        assertThrows(NoteNotFoundException.class, () -> clientUIService.getNotesByPatientId(patientId), "Expected NoteNotFoundException when notes are not found");
        verify(feignClient, times(1)).getNotesByPatientId(patientId);
    }

    @Test
    public void testSaveNote_Success() {
        // Arrange
        Note note = new Note();
        Note savedNote = new Note();
        savedNote.setCreationDate(LocalDate.now());
        savedNote.setUpdateDate(LocalDate.now());

        when(feignClient.saveNote(note)).thenReturn(savedNote);

        // Act
        Note result = clientUIService.saveNote(note);

        // Assert
        assertEquals(savedNote, result);
        verify(feignClient, times(1)).saveNote(note);
    }

    @Test
    public void testSaveNote_BadRequest() {
        // Arrange
        Note note = new Note();
        when(feignClient.saveNote(note)).thenThrow(FeignException.BadRequest.class);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> clientUIService.saveNote(note));
        assertEquals("Failed to save note. Error: null", exception.getMessage());
        verify(feignClient, times(1)).saveNote(note);
    }

    @Test
    public void testSaveNote_FeignException() {
        // Arrange
        Note note = new Note();
        when(feignClient.saveNote(note)).thenThrow(FeignException.class);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> clientUIService.saveNote(note));
        assertEquals("Failed to save note. Error: null", exception.getMessage());
        verify(feignClient, times(1)).saveNote(note);
    }

    @Test
    public void testSaveNote_GeneralException() {
        // Arrange
        Note note = new Note();
        when(feignClient.saveNote(note)).thenThrow(new RuntimeException("General error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> clientUIService.saveNote(note));
        assertEquals("Failed to save note. Error: General error", exception.getMessage());
        verify(feignClient, times(1)).saveNote(note);
    }

    @Test
    public void testUpdateNote_Success() {
        // Arrange
        String noteId = "123";
        Note note = new Note();
        Note updatedNote = new Note();
        updatedNote.setUpdateDate(LocalDate.now());

        when(feignClient.updateNote(noteId, note)).thenReturn(updatedNote);

        // Act
        Note result = clientUIService.updateNote(noteId, note);

        // Assert
        assertEquals(updatedNote, result);
        assertEquals(LocalDate.now(), note.getUpdateDate());
        verify(feignClient, times(1)).updateNote(noteId, note);
    }

    @Test
    public void testUpdateNote_Exception() {
        // Arrange
        String noteId = "123";
        Note note = new Note();
        when(feignClient.updateNote(noteId, note)).thenThrow(new RuntimeException("Update failed"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> clientUIService.updateNote(noteId, note));
        assertEquals("Failed to update note. Error: Update failed", exception.getMessage());
        verify(feignClient, times(1)).updateNote(noteId, note);
    }

    @Test
    public void testDeleteNoteById_Success() {
        // Arrange
        String noteId = "123";
        when(feignClient.deleteNoteById(noteId)).thenReturn(true);

        // Act
        boolean result = clientUIService.deleteNoteById(noteId);

        // Assert
        assertTrue(result);
        verify(feignClient, times(1)).deleteNoteById(noteId);
    }

    @Test
    public void testDeleteNoteById_Failure() {
        // Arrange
        String noteId = "123";
        when(feignClient.deleteNoteById(noteId)).thenReturn(false);

        // Act
        boolean result = clientUIService.deleteNoteById(noteId);

        // Assert
        assertFalse(result);
        verify(feignClient, times(1)).deleteNoteById(noteId);
    }

    @Test
    public void testDeleteNoteById_Exception() {
        // Arrange
        String noteId = "123";
        when(feignClient.deleteNoteById(noteId)).thenThrow(new RuntimeException("Deletion failed"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> clientUIService.deleteNoteById(noteId));
        assertEquals("Deletion failed", exception.getMessage());
        verify(feignClient, times(1)).deleteNoteById(noteId);
    }

    @Test
    public void testGetDiabetesRiskLevel_Success() {
        // Arrange
        Long patientId = 1L;
        String expectedRiskLevel = "IN_DANGER";
        when(feignClient.getDiabetesRiskLevel(patientId)).thenReturn(expectedRiskLevel);

        // Act
        String result = clientUIService.getDiabetesRiskLevel(patientId);

        // Assert
        assertEquals(expectedRiskLevel, result);
        verify(feignClient, times(1)).getDiabetesRiskLevel(patientId);
    }

    @Test
    public void testGetDiabetesRiskLevel_Null() {
        // Arrange
        Long patientId = 1L;
        when(feignClient.getDiabetesRiskLevel(patientId)).thenReturn(null);

        // Act
        String result = clientUIService.getDiabetesRiskLevel(patientId);

        // Assert
        assertNull(result);
        verify(feignClient, times(1)).getDiabetesRiskLevel(patientId);
    }

    @Test
    public void testGetDiabetesRiskLevel_Empty() {
        // Arrange
        Long patientId = 1L;
        when(feignClient.getDiabetesRiskLevel(patientId)).thenReturn("");

        // Act
        String result = clientUIService.getDiabetesRiskLevel(patientId);

        // Assert
        assertEquals("", result);
        verify(feignClient, times(1)).getDiabetesRiskLevel(patientId);
    }

    @Test
    public void testGetDiabetesRiskLevel_Exception() {
        // Arrange
        Long patientId = 1L;
        when(feignClient.getDiabetesRiskLevel(patientId)).thenThrow(new RuntimeException("Feign client error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> clientUIService.getDiabetesRiskLevel(patientId));
        assertEquals("Feign client error", exception.getMessage());
        verify(feignClient, times(1)).getDiabetesRiskLevel(patientId);
    }

}
