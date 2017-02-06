package my.epam.task6;

import java.util.NoSuchElementException;

/**
 * NotePad for storing Notes.
 */

public class NotePad {
    private Note[] notes;
    private int size;

    /**
     * Create empty notepad
     */
    public NotePad() {
        this.notes = new Note[0];
    }

    /**
     * Create notepad filled by notes from array
     *
     * @param notes Initial notes array.
     */
    public NotePad(Note[] notes) {
        this();
        ensureCapacity(notes.length);
        for (int i = 0; i < notes.length; i++) {
            this.notes[i] = notes[i];
        }
        this.size = notes.length;
    }

    /**
     * Add note to notepad.
     *
     * @throws IllegalArgumentException if note is null.
     * @param note Note to be added, should not be null.
     */
    public void add(Note note) {
        if (note == null) throw new IllegalArgumentException();
        ensureCapacity(size + 1);
        notes[size] = note;
        size++;
    }

    /**
     * Create new note by string, and add it to notepad.
     *
     * If contains is null - creates empty note.
     * @param contains String to be added as new note, can be null.
     */
    public void add(String contains) {
        ensureCapacity(size + 1);
        notes[size] = new Note(contains);
        size++;
    }

    /**
     * Get note at index
     *
     * @param index Note index
     * @throws NoSuchElementException if no such index in notepad
     * @return Note at index.
     */
    public Note get(int index) {
        if (index < 0 || index >= size) throw new NoSuchElementException();
        return notes[index];
    }

    /**
     * Remove note from notepad by value.
     *
     * Find first note == noteRmv in notepad, and removes it.
     *
     * @param noteRmv Note to be removed
     */
    public void remove(Note noteRmv) {
        if (noteRmv != null) {
            for (int i = 0; i < size; i++) {
                if (noteRmv == notes[i]) {
                    remove(i);
                }
            }
        }
    }

    /**
     * Remove note by index.
     *
     * @throws NoSuchElementException if no such index in notepad.
     * @param index Index of note to be removed
     */
    public void remove(int index) {
        if (index < 0 || index >= size) throw new NoSuchElementException();
        size--;
        for (int i = index; i < size; i++) {
            notes[i] = notes[i + 1];
        }
        notes[size] = Note.EMPTY_NOTE;
    }

    /**
     * Create a new note by string value, and place it at index position with
     * replacing existing value.
     *
     * @throws NoSuchElementException if no such index in notepad.
     *
     * @param index Index of note to be modified
     * @param newValue New value of note content
     */
    public void modify(int index, String newValue) {
        if (index < 0 || index >= size) throw new NoSuchElementException();
        notes[index] = new Note(newValue);
    }

    /**
     * Return all notes from notepad as Note array.
     *
     * @return Array of notes.
     */
    public Note[] getAll() {
        Note[] allNotes = new Note[size];
        for (int i = 0; i < size; i++) {
            allNotes[i] = notes[i];
        }
        return allNotes;
    }

    public int getSize() {
        return size;
    }

    private void ensureCapacity(int capacity) {
        if (getCapacity() < capacity) {
            Note[] newNotes = new Note[Math.max(getCapacity() * 3 / 2, capacity)];
            for (int i = 0; i < notes.length; i++) {
                newNotes[i] = notes[i];
            }
            notes = newNotes;
        }
    }

    private int getCapacity() {
        return notes.length;
    }

}
