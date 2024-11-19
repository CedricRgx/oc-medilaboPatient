package com.openclassrooms.msnote.controller;

import com.openclassrooms.msnote.model.Note;
import com.openclassrooms.msnote.service.impl.NoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NoteControllerTest {

    @Mock
    private NoteService noteService;

    @InjectMocks
    private NoteController noteController;

    private Note note1;
    private Note note2;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        note1 = new Note();
        note1.setId("1");
        note1.setPatId(101L);
        note1.setNote("Patient is healthy.");

        note2 = new Note();
        note2.setId("2");
        note2.setPatId(102L);
        note2.setNote("Patient has a cold.");
    }

    @Test
    public void testGetPatients_Success() {
        // Arrange
        List<Note> notes = Arrays.asList(note1, note2);
        when(noteService.getAllNotes()).thenReturn(notes);

        // Act
        ResponseEntity<List<Note>> response = noteController.getPatients();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(notes, response.getBody());
        verify(noteService, times(1)).getAllNotes();
    }

    @Test
    public void testGetPatients_NotFound() {
        // Arrange
        when(noteService.getAllNotes()).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<Note>> response = noteController.getPatients();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
        verify(noteService, times(1)).getAllNotes();
    }

    @Test
    public void testGetNoteById_Success() {
        // Arrange
        when(noteService.getNoteById("1")).thenReturn(Optional.of(note1));

        // Act
        ResponseEntity<Note> response = noteController.getNoteById("1");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(note1, response.getBody());
        verify(noteService, times(1)).getNoteById("1");
    }

    @Test
    public void testGetNoteById_NotFound() {
        // Arrange
        when(noteService.getNoteById("3")).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Note> response = noteController.getNoteById("3");

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(noteService, times(1)).getNoteById("3");
    }

    @Test
    public void testGetNotesByPatientId_Success() {
        // Arrange
        List<Note> notes = Arrays.asList(note1);
        when(noteService.getNotesByPatId(101L)).thenReturn(notes);

        // Act
        ResponseEntity<List<Note>> response = noteController.getNotesByPatientId(101L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(notes, response.getBody());
        verify(noteService, times(1)).getNotesByPatId(101L);
    }

    @Test
    public void testGetNotesByPatientId_NotFound() {
        // Arrange
        when(noteService.getNotesByPatId(103L)).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<Note>> response = noteController.getNotesByPatientId(103L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
        verify(noteService, times(1)).getNotesByPatId(103L);
    }

    @Test
    public void testSaveNote_Success() {
        // Arrange
        Note newNote = new Note();
        newNote.setPatId(104L);
        newNote.setNote("New patient note.");

        when(noteService.saveNote(newNote)).thenReturn(newNote);

        // Act
        ResponseEntity<Note> response = noteController.saveNote(newNote);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(newNote, response.getBody());
        verify(noteService, times(1)).saveNote(newNote);
    }

    @Test
    public void testSaveNote_Failure() {
        // Arrange
        Note newNote = new Note();
        when(noteService.saveNote(newNote)).thenReturn(null);

        // Act
        ResponseEntity<Note> response = noteController.saveNote(newNote);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(noteService, times(1)).saveNote(newNote);
    }

    @Test
    public void testUpdateNote_Success() {
        // Arrange
        Note updatedNote = new Note();
        updatedNote.setNote("Updated note content.");

        when(noteService.getNoteById("1")).thenReturn(Optional.of(note1));
        when(noteService.saveNote(any(Note.class))).thenReturn(note1);

        // Act
        ResponseEntity<Note> response = noteController.updateNote("1", updatedNote);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(note1, response.getBody());
        assertEquals("Updated note content.", note1.getNote());
        verify(noteService, times(1)).getNoteById("1");
        verify(noteService, times(1)).saveNote(note1);
    }

    @Test
    public void testUpdateNote_NotFound() {
        // Arrange
        Note updatedNote = new Note();
        when(noteService.getNoteById("3")).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Note> response = noteController.updateNote("3", updatedNote);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(noteService, times(1)).getNoteById("3");
        verify(noteService, times(0)).saveNote(any(Note.class));
    }

    @Test
    public void testDeleteNoteById_Success() {
        // Arrange
        when(noteService.deleteNoteById("1")).thenReturn(true);

        // Act
        ResponseEntity<Boolean> response = noteController.deleteNoteById("1");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody());
        verify(noteService, times(1)).deleteNoteById("1");
    }

    @Test
    public void testDeleteNoteById_NotFound() {
        // Arrange
        when(noteService.deleteNoteById("3")).thenReturn(false);

        // Act
        ResponseEntity<Boolean> response = noteController.deleteNoteById("3");

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertFalse(response.getBody());
        verify(noteService, times(1)).deleteNoteById("3");
    }
}
