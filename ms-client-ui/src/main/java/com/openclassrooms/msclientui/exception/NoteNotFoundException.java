package com.openclassrooms.msclientui.exception;

/**
 * Custom exception thrown when a requested note is not found in the application's data storage.
 */
public class NoteNotFoundException extends RuntimeException {

    /**
     * Constructs a new NoteNotFoundException with the specified detail message.
     *
     * @param message the detail message
     */
    public NoteNotFoundException(String message) {
        super(message);
    }

}