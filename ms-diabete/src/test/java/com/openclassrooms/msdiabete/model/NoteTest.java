package com.openclassrooms.msdiabete.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class NoteTest {

    @InjectMocks
    private Note note;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        note = new Note();
    }

    @Test
    public void testGetAndSetId() {
        // Arrange
        String id = "12345";

        // Act
        note.setId(id);
        String result = note.getId();

        // Assert
        assertEquals(id, result);
    }

    @Test
    public void testGetAndSetPatId() {
        // Arrange
        Long patId = 101L;

        // Act
        note.setPatId(patId);
        Long result = note.getPatId();

        // Assert
        assertEquals(patId, result);
    }

    @Test
    public void testGetAndSetNoteContent() {
        // Arrange
        String content = "The patient is healthy.";

        // Act
        note.setNote(content);
        String result = note.getNote();

        // Assert
        assertEquals(content, result);
    }

    @Test
    public void testGetAndSetCreationDate() {
        // Arrange
        LocalDate creationDate = LocalDate.of(2023, 10, 1);

        // Act
        note.setCreationDate(creationDate);
        LocalDate result = note.getCreationDate();

        // Assert
        assertEquals(creationDate, result);
    }

    @Test
    public void testGetAndSetUpdateDate() {
        // Arrange
        LocalDate updateDate = LocalDate.of(2023, 10, 15);

        // Act
        note.setUpdateDate(updateDate);
        LocalDate result = note.getUpdateDate();

        // Assert
        assertEquals(updateDate, result);
    }

    @Test
    public void testAllArgsConstructor() {
        // Arrange
        String id = "12345";
        Long patId = 101L;
        String content = "The patient is healthy.";
        LocalDate creationDate = LocalDate.of(2023, 10, 1);
        LocalDate updateDate = LocalDate.of(2023, 10, 15);

        // Act
        Note note = new Note();
        note.setId(id);
        note.setPatId(patId);
        note.setNote(content);
        note.setCreationDate(creationDate);
        note.setUpdateDate(updateDate);

        // Assert
        assertEquals(id, note.getId());
        assertEquals(patId, note.getPatId());
        assertEquals(content, note.getNote());
        assertEquals(creationDate, note.getCreationDate());
        assertEquals(updateDate, note.getUpdateDate());
    }

}
