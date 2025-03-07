package eu.qped.java.checkers.style.config;

import eu.qped.framework.CheckLevel;
import eu.qped.java.checkers.mass.QfStyleSettings;
import eu.qped.java.utils.SupportedLanguages;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;

import java.util.EnumSet;
import java.util.List;

/**
 * Style checker configs manager.
 * The class manage all the settings for the style checker and create a Setting Object to control the checker.
 *
 * @author Basel Alaktaa
 * @version 1.1
 * @since 01.06.2021
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StyleConfigurationReader {


    private QfStyleSettings qfStyleSettings;

    public StyleSettings getStyleSettings() {
        StyleSettings styleSettings = new StyleSettings();
        try {
            styleSettings.setLanguage(
                    qfStyleSettings.getLanguage() != null
                            ? qfStyleSettings.getLanguage()
                            : SupportedLanguages.ENGLISH
            );
            styleSettings.setIsCorrection(
                    qfStyleSettings.getIsCorrection() != null
                            ? qfStyleSettings.getIsCorrection()
                            : false
            );
            styleSettings.setTaskSpecificFeedbacks(
                    qfStyleSettings.getTaskSpecificFeedbacks() != null
                            ? qfStyleSettings.getTaskSpecificFeedbacks()
                            : null
            );
            styleSettings.setCheckLevel(
                    qfStyleSettings.getCheckLevel() != null && EnumSet.allOf(CheckLevel.class).contains(CheckLevel.valueOf(qfStyleSettings.getCheckLevel()))
                            ? CheckLevel.valueOf(qfStyleSettings.getCheckLevel())
                            : CheckLevel.BEGINNER
            );
            styleSettings.setMethodParameterNamesRegEx(
                    qfStyleSettings.getMethodParameterNamePattern() != null
                            ? qfStyleSettings.getMethodParameterNamePattern()
                            : "undefined"
            );
            styleSettings.setMaxClassLength(
                    qfStyleSettings.getClassLength() != null
                            ? Integer.parseInt(qfStyleSettings.getClassLength())
                            : -1
            );
            styleSettings.setMaxMethodLength(
                    qfStyleSettings.getMethodLength() != null
                            ? Integer.parseInt(qfStyleSettings.getMethodLength())
                            : -1
            );
            styleSettings.setMaxFieldsCount(
                    qfStyleSettings.getFieldsCount() != null
                            ? Integer.parseInt(qfStyleSettings.getFieldsCount())
                            : -1
            );
            styleSettings.setMaxCycloComplexity(
                    qfStyleSettings.getCyclomaticComplexity() != null
                            ? Integer.parseInt(qfStyleSettings.getCyclomaticComplexity())
                            : -1
            );
            styleSettings.setVarNamesRegEx(
                    qfStyleSettings.getVariableNamePattern() != null
                            ? qfStyleSettings.getVariableNamePattern()
                            : "undefined"
            );
            styleSettings.setMethodNamesRegEx(
                    qfStyleSettings.getMethodNamePattern() != null
                            ? qfStyleSettings.getMethodNamePattern()
                            : "undefined"
            );
            styleSettings.setClassNameRegEx(
                    qfStyleSettings.getClassNamePattern() != null
                            ? qfStyleSettings.getClassNamePattern()
                            : "undefined"
            );
            styleSettings.setNamesLevel(
                    isCheckLevel(qfStyleSettings.getNamesLevel())
                            ? (CheckLevel.valueOf(qfStyleSettings.getNamesLevel()))
                            : CheckLevel.BEGINNER
            );
            styleSettings.setComplexityLevel(
                    isCheckLevel(qfStyleSettings.getComplexityLevel())
                            ? (CheckLevel.valueOf(qfStyleSettings.getComplexityLevel()))
                            : CheckLevel.BEGINNER
            );
            styleSettings.setBasisLevel(
                    isCheckLevel(qfStyleSettings.getBasisLevel())
                            ? (CheckLevel.valueOf(qfStyleSettings.getBasisLevel()))
                            : CheckLevel.BEGINNER
            );
        } catch (IllegalArgumentException | NullPointerException e) {
            LogManager.getLogger(getClass()).warn(e.getMessage());
        }
        return styleSettings;
    }

    private boolean isCheckLevel(String checkLevel) {
        try {
            List<CheckLevel> checkLevels = List.of(CheckLevel.BEGINNER, CheckLevel.INTERMEDIATE, CheckLevel.ADVANCED);
            return checkLevels.contains(CheckLevel.valueOf(checkLevel));
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}

