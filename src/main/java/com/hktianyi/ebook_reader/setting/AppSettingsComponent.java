package com.hktianyi.ebook_reader.setting;

import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class AppSettingsComponent {
    private final JPanel mainPanel;
    private final JBTextField bookPathText = new JBTextField();
    private final JBTextField lineWidthText = new JBTextField();
    private final JBTextField pageNoText = new JBTextField();

    public AppSettingsComponent() {
        mainPanel = FormBuilder.createFormBuilder()
                .addLabeledComponent(new JBLabel("Enter book path: "), bookPathText, 1, false)
                .addLabeledComponent(new JBLabel("Line width: "), lineWidthText, 1, false)
                .addLabeledComponent(new JBLabel("PageNo: "), pageNoText, 1, false)
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();
    }

    public JPanel getPanel() {
        return mainPanel;
    }

    public JComponent getPreferredFocusedComponent() {
        return bookPathText;
    }

    @NotNull
    public String getBookPathText() {
        return bookPathText.getText();
    }

    public void setBookPathText(@NotNull String newText) {
        bookPathText.setText(newText);
    }

    @NotNull
    public int getLineWidthText() {
        return Integer.parseInt(lineWidthText.getText());
    }

    public void setLineWidthText(@NotNull int newText) {
        lineWidthText.setText(String.valueOf(newText));
    }

    @NotNull
    public int getPageNoText() {
        return Integer.parseInt(pageNoText.getText());
    }

    public void setPageNoText(@NotNull int newText) {
        pageNoText.setText(String.valueOf(newText));
    }

}
