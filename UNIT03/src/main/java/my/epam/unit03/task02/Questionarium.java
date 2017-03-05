package my.epam.unit03.task02;


import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Questions and answer application with i18n.
 * <p>
 * Simple one class swing application.
 * Switch questions, take answers, use locale change to switch language.
 */

public class Questionarium extends JFrame {
    private static final Questionarium instance = new Questionarium();
    private JPanel mainPanel;
    private JPanel questionsPanel;
    private JPanel answersPanel;
    private JPanel bottomPanel;

    private JTextArea answer;
    private JList<Question> questionsList;
    private Locale currentLocale = new Locale("en_US");
    private Question currentQuestion;

    private ResourceBundle questionsBundle = ResourceBundle.getBundle("my.epam.unit03.task02.properties.questions", currentLocale);
    private ResourceBundle menuBundle = ResourceBundle.getBundle("my.epam.unit03.task02.properties.menuItems", currentLocale);

    private Questionarium() {
        SwingUtilities.invokeLater(this::initializeGui);
    }

    public static Questionarium getInctanse() {
        return instance;
    }

    private void initializeGui() {
        setTitle(menuBundle.getString("appTitle"));
        URL iconUrl = getClass().getResource("/my/epam/unit03/task02/images/title_icon.png");
        setIconImage(new ImageIcon(iconUrl).getImage());
        setSize(new Dimension(640, 480));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        buildMainPanel();
        add(mainPanel);

        setVisible(true);
    }

    private void buildMainPanel() {
        buildAnswersPanel();
        buildQuestionsPanel();
        buildBottomPanel();
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(questionsPanel);
        mainPanel.add(answersPanel);
        mainPanel.add(bottomPanel);
    }

    private void buildBottomPanel() {
        bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(640, 30));
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));

        JComboBox<String> locales = new JComboBox<>();
        for (String localeName : getAvailableLocales()) {
            locales.addItem(menuBundle.getString(localeName));
        }
        locales.setSelectedItem(menuBundle.getString(currentLocale.getDisplayName()));

        locales.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                onLocaleChanged((String) locales.getSelectedItem());
            }
        });

        JLabel localesLabel = new JLabel(menuBundle.getString("localesLabel"));
        bottomPanel.add(localesLabel, BorderLayout.LINE_END);
        bottomPanel.add(locales, BorderLayout.LINE_END);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    private void onLocaleChanged(String selectedItem) {
        for (String localeName : getAvailableLocales()) {
            String localeTitle = menuBundle.getString(localeName);
            if (selectedItem.equals(localeTitle)) {
                currentLocale = new Locale(localeName);
                questionsBundle = ResourceBundle.getBundle("my.epam.unit03.task02.properties.questions", currentLocale);
                menuBundle = ResourceBundle.getBundle("my.epam.unit03.task02.properties.menuItems", currentLocale);
                refreshPanels();
            }
        }
    }

    private String[] getAvailableLocales() {
        return menuBundle.keySet().stream()
                .filter(s -> Pattern.matches("(^[a-zA-Z]{2}_[a-zA-Z]{2}$)", s))
                .collect(Collectors.toList())
                .toArray(new String[0]);
    }

    private void refreshPanels() {
        remove(mainPanel);
        buildMainPanel();
        add(mainPanel);
        setTitle(menuBundle.getString("appTitle"));
        revalidate();
    }

    private void buildAnswersPanel() {
        answersPanel = new JPanel();
        answersPanel.setPreferredSize(new Dimension(640, 300));
        answersPanel.setLayout(new BorderLayout());

        answer = new JTextArea();
        answer.setLineWrap(true);
        answer.setWrapStyleWord(true);

        answersPanel.add(getjScrollPane(answer), BorderLayout.CENTER);
    }

    private void buildQuestionsPanel() {
        questionsPanel = new JPanel();
        questionsPanel.setPreferredSize(new Dimension(640, 150));
        questionsPanel.setLayout(new BorderLayout());

        questionsList = new JList<>();
        questionsList.setListData(loadQuestions());
        questionsList.addListSelectionListener(e -> onSelected(questionsList.getSelectedValue()));
        questionsList.setSelectedValue(currentQuestion, true);

        questionsPanel.add(getjScrollPane(questionsList), BorderLayout.CENTER);
    }

    private Question[] loadQuestions() {
        return questionsBundle.keySet().stream()
                .filter((s -> s.startsWith("question")))
                .sorted()
                .map(s -> new Question(s, questionsBundle.getString(s), s.replace("question", "answer")))
                .collect(Collectors.toList())
                .toArray(new Question[0]);
    }

    private void onSelected(Question question) {
        currentQuestion = question;
        answer.setText(questionsBundle.getString(question.getAnswerKey()));
    }


    private JScrollPane getjScrollPane(JComponent answer) {
        JScrollPane scrollPane = new JScrollPane(answer);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        return scrollPane;
    }

    /**
     * Question class, associate question with answer by property key.
     * <p>
     * Only questionKey and answerKey take part to equality.
     * <p>
     * Question and answer text should depends on current locale.
     */

    private class Question {
        private String questionKey;
        private String questionText;
        private String answerKey;
        private String answerText;

        Question(String questionKey, String questionText, String answerKey) {
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


}
