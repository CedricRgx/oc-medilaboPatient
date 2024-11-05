package com.openclassrooms.msnote.service;

import com.openclassrooms.msnote.model.Note;

import java.util.List;

public interface INoteService {

    List<Note> getNotesByPatId(Long id);
}
