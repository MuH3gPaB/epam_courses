package my.epam.unit03.task02;

public class Question {
    private String questionKey;
    private String questionText;
    private String answerKey;
    private String answerText;

    public Question(String questionKey, String questionText, String answerKey) {
        this.questionKey = questionKey;
        this.questionText = questionText;
        this.answerKey = answerKey;
    }

    public String getQuestionKey() {
        return questionKey;
    }

    public void setQuestionKey(String questionKey) {
        this.questionKey = questionKey;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getAnswerKey() {
        return answerKey;
    }

    public void setAnswerKey(String answerKey) {
        this.answerKey = answerKey;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    @Override
    public String toString() {
        return questionText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question = (Question) o;

        if (questionKey != null ? !questionKey.equals(question.questionKey) : question.questionKey != null)
            return false;
        return answerKey != null ? answerKey.equals(question.answerKey) : question.answerKey == null;

    }

    @Override
    public int hashCode() {
        int result = questionKey != null ? questionKey.hashCode() : 0;
        result = 31 * result + (answerKey != null ? answerKey.hashCode() : 0);
        return result;
    }
}
