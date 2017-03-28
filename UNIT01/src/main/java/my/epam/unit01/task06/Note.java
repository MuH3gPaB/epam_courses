package my.epam.unit01.task06;


/**
 * Note class.
 *
 * Contains note as one String field.
 * Immutable.
 */

public class Note {
    private final String content;

    /**
     * Contains empty note
     */
    public static final Note EMPTY_NOTE = new Note(null);

    /**
     * Contains empty note content string value
     */
    public static final String EMPTY_NOTE_CONTENT = "<empty note>";

    /**
     * Create new note.
     * If content is null - creates empty note
     *
     * @param content Content string.
     */
    public Note(String content) {
        this.content = (content == null) ? EMPTY_NOTE_CONTENT : content;
    }

    public String getContent() {
        return content;
    }

    /**
     * @return Content string.
     */
    @Override
    public String toString() {
        return content;
    }
}
