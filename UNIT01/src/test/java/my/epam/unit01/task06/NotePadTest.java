package my.epam.unit01.task06;

import my.epam.unit01.task02.Task2Test;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class NotePadTest extends Assert {
    private static final Logger logger = Logger.getLogger(Task2Test.class);

    @BeforeClass
    public static void logStart() {
        logger.info("NotePad test started...");
    }


    @Test
    public void add() throws Exception {
        logger.info("   Testing NotePad.add...");
        NotePad notePad = new NotePad();
        String[] notes = new String[]{"Note 1", "Note 2", Note.EMPTY_NOTE_CONTENT};
        notePad.add(notes[0]);
        notePad.add(new Note(notes[1]));
        notePad.add(new Note(null));

        for (int i = 0; i < notePad.getSize(); i++) {
            assertEquals("Wrong note content after adding.", notes[i], notePad.getAll()[i].toString());
        }
    }

    @Test
    public void remove() throws Exception {
        logger.info("   Testing NotePad.remove...");
        NotePad notePad = new NotePad();
        Note[] notes = new Note[]{new Note("Note 1"), new Note("Note 2"), Note.EMPTY_NOTE, new Note("Note 4")};
        for (Note note : notes) {
            notePad.add(note);
        }

        notePad.remove(notes[0]);
        for (Note note : notePad.getAll()) {
            assertNotEquals("Note remove fails on note = " + note, note, notes[0]);
        }

        notePad.remove(0);
        for (Note note : notePad.getAll()) {
            assertNotEquals("Note remove fails on note = " + note, note, notes[1]);
        }
    }

    @Test
    public void modify() throws Exception {
        logger.info("   Testing NotePad.modify...");
        NotePad notePad = new NotePad();
        Note[] notes = new Note[]{new Note("Note 1"), new Note("Note 2"), Note.EMPTY_NOTE, new Note("Note 4")};
        for (Note note : notes) {
            notePad.add(note);
        }

        notePad.modify(1, "Modified note.");
        assertEquals("Modify fails", "Modified note.", notePad.get(1).getContent());

    }

    @Test
    public void getAll() throws Exception {
        logger.info("   Testing NotePad.getAll...");
        NotePad notePad = new NotePad();
        assertEquals("Empty getAll fails", 0, notePad.getAll().length);
        Note[] notes = new Note[]{new Note("Note 1"), new Note("Note 2"), Note.EMPTY_NOTE, new Note("Note 4")};
        for (Note note : notes) {
            notePad.add(note);
        }

        for (int i = 0; i < notePad.getSize(); i++) {
            assertEquals("GetAll fails", notes[i], notePad.getAll()[i]);
        }
    }

    @Test
    public void getSize() throws Exception {
        logger.info("   Testing NotePad.getSize...");
        NotePad notePad = new NotePad();
        assertEquals("Zero getSize fails", 0, notePad.getSize());
        Note[] notes = new Note[]{new Note("Note 1"), new Note("Note 2"), Note.EMPTY_NOTE, new Note("Note 4")};
        notePad = new NotePad(notes);
        assertEquals("GetSize fails", notes.length, notePad.getSize());
    }

}