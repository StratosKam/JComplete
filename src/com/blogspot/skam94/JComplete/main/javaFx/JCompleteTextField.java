/*
 * This file is part of JComplete.
 *
 * JComplete is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JComplete is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JComplete.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright 2015 Stratos Kamadanis
 */
package com.blogspot.skam94.JComplete.main.javaFx;

import com.blogspot.skam94.JComplete.main.JComplete;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.List;

/**
 * A javafx TextField making suggestions based on a JComplete object.
 */
public class JCompleteTextField extends TextField {

    private JComplete autocomplete;
    private List<String> suggestions;
    private int selectedSuggestion;
    private int originalCaretPos;
    private Tooltip tooltip;
    private boolean showTooltip;

    public JCompleteTextField(JComplete autocomplete) {
        super();
        this.autocomplete = autocomplete;
        this.tooltip = new Tooltip();
        tooltip.setStyle("-fx-background-color: transparent;");
        setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                handleReleasedKey(ke);
            }
        });
    }

    private void handleReleasedKey(KeyEvent ke) {
        if (ke.getCode().isLetterKey() || ke.getCode().isDigitKey()) {
            initSuggestions();
            setSuggestion();
        } else if (ke.getCode() == KeyCode.UP) {
            if (selectedSuggestion != 0) {
                --selectedSuggestion;
                setSuggestion();
            }
        } else if (ke.getCode() == KeyCode.DOWN) {
            if (selectedSuggestion != suggestions.size() - 1) {
                ++selectedSuggestion;
                setSuggestion();
            }
        } else if (ke.getCode() == KeyCode.BACK_SPACE) {
            String text = getText();
            if (text.length() != 0) {
                setText(text.substring(0, text.length() - 1));
                --originalCaretPos;
            } else {
                setText("");
                originalCaretPos = 0;
            }
            positionCaret(originalCaretPos);
            initSuggestions();
        }
    }

    private void setSuggestion() {
        if (!suggestions.isEmpty() && suggestions.size() > selectedSuggestion) {
            setText(suggestions.get(selectedSuggestion));
            requestFocus();
            positionCaret(originalCaretPos);
            selectEnd();
        }
    }

    private void setToolTip() {
        if (!showTooltip) {
            tooltip.hide();
            return;
        }
        if (suggestions.size() <= 1 || suggestions.size() >= 6) {
            tooltip.hide();
            return;
        }
        StringBuilder sb = new StringBuilder();
        suggestions.stream().forEach(string -> sb.append(string).append("\n"));
        tooltip.hide();
        tooltip.setText(sb.toString());

        tooltip.show(this,
                localToScene(0.0, 0.0).getX() + getScene().getX() + getScene().getWindow().getX(),
                localToScene(0.0, 0.0).getY() + getScene().getY() + getScene().getWindow().getY() + getHeight());
    }

    private void initSuggestions() {
        suggestions = autocomplete.getMatchStartsWith(getText());
        selectedSuggestion = 0;
        originalCaretPos = getCaretPosition();
        setToolTip();
    }

    public void setShowTooltip(boolean showTooltip) {
        this.showTooltip = showTooltip;
        setToolTip();
    }
}
