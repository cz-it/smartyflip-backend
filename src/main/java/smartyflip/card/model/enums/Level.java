/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.card.model.enums;

public enum Level {

    EASY, MEDIUM, HARD;

    public static Level fromString(String levelStr) {
        return Level.valueOf(levelStr.trim().toUpperCase());
    }
}
