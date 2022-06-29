package eu.qped.java.checkers.mass;

import eu.qped.framework.Checker;
import eu.qped.framework.FileInfo;
import eu.qped.framework.QfProperty;
import eu.qped.framework.qf.QfObject;
import eu.qped.java.checkers.semantics.SemanticChecker;
import eu.qped.java.checkers.semantics.SemanticFeedback;
import eu.qped.java.checkers.style.StyleChecker;
import eu.qped.java.checkers.style.StyleFeedback;
import eu.qped.java.checkers.syntax.SyntaxChecker;
import eu.qped.java.feedback.syntax.SyntaxFeedback;

import java.util.ArrayList;
import java.util.List;


public class Mass implements Checker {

    @QfProperty
    private FileInfo file;

    @QfProperty
    private QFMainSettings mainSettings;

    @QfProperty
    private QfMass mass;

    private final static String NEW_LINE = "\n" + "\n";

    @Override
    public void check(QfObject qfObject) throws Exception {

        MainSettings mainSettings = new MainSettings(this.mainSettings);

        // Syntax Checker
        SyntaxChecker syntaxChecker = SyntaxChecker.builder().build();
        if (file != null) {
            syntaxChecker.setTargetProject(file.getUnzipped().getPath());
        } else {
            syntaxChecker.setStringAnswer(qfObject.getAnswer());
        }
        // Style Checker

        StyleChecker styleChecker = StyleChecker.builder().qfStyleSettings(mass.getStyle()).build();

        // Semantic Checker

        SemanticChecker semanticChecker = SemanticChecker.builder().feedbacks(new ArrayList<>()).qfSemSettings(mass.getSemantic()).build();

        //Mass
        MassExecutor massExecutor = new MassExecutor(styleChecker, semanticChecker, syntaxChecker, mainSettings);
        massExecutor.execute();

        /*
         feedbacks
         */
        List<StyleFeedback> styleFeedbacks;
        styleFeedbacks = massExecutor.getStyleFeedbacks();

        List<SyntaxFeedback> syntaxFeedbacks;
        syntaxFeedbacks = massExecutor.getSyntaxFeedbacks();

        List<SemanticFeedback> semanticFeedbacks;
        semanticFeedbacks = massExecutor.getSemanticFeedbacks();


        String[] result = new String[styleFeedbacks.size() + semanticFeedbacks.size() + syntaxFeedbacks.size() + 100];

        int i = 0;

        for (StyleFeedback styleFeedback : styleFeedbacks) {
            result[i] = "style Feedback";
            result[i + 1] =
                    styleFeedback.getFile()
                            + NEW_LINE
                            + styleFeedback.getDesc()
                            + NEW_LINE
                            + styleFeedback.getContent()
                            + NEW_LINE
                            + styleFeedback.getLine()
                            + NEW_LINE
                            + styleFeedback.getExample()
                            + NEW_LINE
                            + "------------------------------------------------------------------------------";
            i = i + 2;
        }

        for (SemanticFeedback semanticFeedback : semanticFeedbacks) {
            result[i] = "semantic Feedback";
            result[i + 1] = semanticFeedback.getBody() + NEW_LINE
                    + "--------------------------------------------------";
            i = i + 2;
        }

        for (SyntaxFeedback syntax : syntaxFeedbacks) {
            result[i + 1] = ""
                    + syntax.toString()
                    + NEW_LINE
                    + "--------------------------------------------------"
            ;
            i = i + 2;
        }


        qfObject.setFeedback(result);
    }

}