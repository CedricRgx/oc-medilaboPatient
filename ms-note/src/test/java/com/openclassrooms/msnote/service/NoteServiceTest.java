package com.openclassrooms.msnote.service;

import com.openclassrooms.msnote.model.Note;
import com.openclassrooms.msnote.proxy.FeignClient;
import com.openclassrooms.msnote.repository.NoteRepository;
import com.openclassrooms.msnote.service.impl.NoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @Mock
    private FeignClient feignClient;

    @InjectMocks
    private NoteService noteService;

    private Note note1;
    private Note note2;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        note1 = new Note();
        note1.setId("1");
        note1.setPatId(101L);
        note1.setNote("The patient is healthy.");

        note2 = new Note();
        note2.setId("2");
        note2.setPatId(102L);
        note2.setNote("The patient is sick.");
    }

    @Test
    public void testGetAllNotes() {
        // Arrange
        List<Note> notes = Arrays.asList(note1, note2);
        when(noteRepository.findAll()).thenReturn(notes);

        // Act
        List<Note> result = noteService.getAllNotes();

        // Assert
        assertEquals(2, result.size());
        assertEquals(notes, result);
        verify(noteRepository, times(1)).findAll();
    }

    @Test
    public void testGetNoteById_Success() {
        // Arrange
        when(noteRepository.findById("1")).thenReturn(Optional.of(note1));

        // Act
        Optional<Note> result = noteService.getNoteById("1");

        // Assert
        assertTrue(result.isPresent());
        assertEquals(note1, result.get());
        verify(noteRepository, times(1)).findById("1");
    }

    @Test
    public void testGetNoteById_NotFound() {
        // Arrange
        when(noteRepository.findById("3")).thenReturn(Optional.empty());

        // Act
        Optional<Note> result = noteService.getNoteById("3");

        // Assert
        assertFalse(result.isPresent());
        verify(noteRepository, times(1)).findById("3");
    }

    @Test
    public void testGetNoteById_InvalidId() {
        // Arrange
        when(noteRepository.findById("invalid-id")).thenThrow(new IllegalArgumentException());

        // Act
        Optional<Note> result = noteService.getNoteById("invalid-id");

        // Assert
        assertFalse(result.isPresent());
        verify(noteRepository, times(1)).findById("invalid-id");
    }

    @Test
    public void testGetNotesByPatId_Success() {
        // Arrange
        List<Note> notes = Arrays.asList(note1);
        when(noteRepository.getNotesByPatId(101L)).thenReturn(notes);

        // Act
        List<Note> result = noteService.getNotesByPatId(101L);

        // Assert
        assertEquals(1, result.size());
        assertEquals(notes, result);
        verify(noteRepository, times(1)).getNotesByPatId(101L);
    }

    @Test
    public void testGetNotesByPatId_NoNotes() {
        // Arrange
        when(noteRepository.getNotesByPatId(103L)).thenReturn(Arrays.asList());

        // Act
        List<Note> result = noteService.getNotesByPatId(103L);

        // Assert
        assertTrue(result.isEmpty());
        verify(noteRepository, times(1)).getNotesByPatId(103L);
    }

    @Test
    public void testSaveNote_Success() {
        // Arrange
        Note newNote = new Note();
        newNote.setPatId(104L);
        newNote.setNote("New note for the patient.");

        when(noteRepository.save(newNote)).thenReturn(newNote);

        when(feignClient.isExist(104L)).thenReturn(true);

        // Act
        Note result = noteService.saveNote(newNote);

        // Assert
        assertNotNull(result);
        assertEquals(newNote, result);
        verify(noteRepository, times(1)).save(newNote);

        verify(feignClient, times(1)).isExist(104L);
    }

    @Test
    public void testSaveNote_PatientNotFound() {
        // Arrange
        Note newNote = new Note();
        newNote.setPatId(105L);
        newNote.setNote("An other new note for the patient.");
        when(feignClient.isExist(105L)).thenReturn(false);

        // Act
        Note result = noteService.saveNote(newNote);

        // Assert
        assertNull(result);
        verify(feignClient, times(1)).isExist(105L);
        verify(noteRepository, never()).save(any(Note.class));
    }

    @Test
    public void testDeleteNoteById_Success() {
        // Arrange
        String noteId = "1";
        when(noteRepository.findById(noteId)).thenReturn(Optional.of(note1));
        doNothing().when(noteRepository).deleteById(noteId);

        // Act
        boolean result = noteService.deleteNoteById(noteId);

        // Assert
        assertTrue(result);
        verify(noteRepository, times(1)).findById(noteId);
        verify(noteRepository, times(1)).deleteById(noteId);
    }

    @Test
    public void testDeleteNoteById_NoteNotFound() {
        // Arrange
        String noteId = "1";
        when(noteRepository.findById(noteId)).thenReturn(Optional.empty());

        // Act
        boolean result = noteService.deleteNoteById(noteId);

        // Assert
        assertFalse(result);
        verify(noteRepository, times(1)).findById(noteId);
        verify(noteRepository, never()).deleteById(anyString());
    }

    @Test
    public void testDeleteNoteById_Exception() {
        // Arrange
        String noteId = "1";
        when(noteRepository.findById(noteId)).thenReturn(Optional.of(note1));
        doThrow(new RuntimeException("Database error")).when(noteRepository).deleteById(noteId);

        // Act
        boolean result = noteService.deleteNoteById(noteId);

        // Assert
        assertFalse(result);
        verify(noteRepository, times(1)).findById(noteId);
        verify(noteRepository, times(1)).deleteById(noteId);
    }



}