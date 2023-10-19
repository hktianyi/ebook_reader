package com.hktianyi.ebook_reader.setting;

import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.TextBrowseFolderListener;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;
import nl.siegmann.epublib.domain.Book;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.IOException;

/**
 * 设置页面
 */
public class AppSettingsConfigurable implements Configurable {
    private AppSettingsComponent mySettingsComponent;

    // A default constructor with no arguments is required because this implementation
    // is registered as an applicationConfigurable EP

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Ebook Reader Setting";
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return mySettingsComponent.getPreferredFocusedComponent();
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        mySettingsComponent = new AppSettingsComponent();
        return mySettingsComponent.getPanel();
    }

    @Override
    public boolean isModified() {
        AppSettingsState settings = AppSettingsState.getInstance();
        boolean modified = !mySettingsComponent.getBookPathText().equals(settings.bookPath);
        modified |= !mySettingsComponent.getChapter().equals(settings.chapterName);
        modified |= mySettingsComponent.getLineWidthText() != settings.lineWidth;
        modified |= mySettingsComponent.getPageNoText() != settings.pageNo;

        // FIXME
        if (StringUtils.isNotBlank(mySettingsComponent.getBookPathText()) && !mySettingsComponent.getBookPathText().equals(settings.bookPath)) {
            mySettingsComponent.chapterSelect.removeAllItems();
            try {
                Book book = AppSettingsState.getBook(mySettingsComponent.getBookPathText());
                book.getTableOfContents().getTocReferences().forEach(each -> mySettingsComponent.chapterSelect.addItem(each.getTitle()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return modified;
    }

    @Override
    public void apply() {
        AppSettingsState settings = AppSettingsState.getInstance();
        settings.bookPath = mySettingsComponent.getBookPathText();
        settings.chapterName = mySettingsComponent.getChapter();
        settings.lineWidth = mySettingsComponent.getLineWidthText();
        settings.pageNo = mySettingsComponent.getPageNoText();
        settings.init();
    }

    @Override
    public void reset() {
        AppSettingsState settings = AppSettingsState.getInstance();
        mySettingsComponent.setBookPathText(settings.bookPath);
        mySettingsComponent.setChapter(settings.chapterName);
        mySettingsComponent.setLineWidthText(settings.lineWidth);
        mySettingsComponent.setPageNoText(settings.pageNo);
    }

    @Override
    public void disposeUIResources() {
        mySettingsComponent = null;
    }

    /**
     * 设置界面UI组件
     */
    static class AppSettingsComponent {
        private final JPanel mainPanel;
        private final TextFieldWithBrowseButton bookPathText = new TextFieldWithBrowseButton();
        private final ComboBox<String> chapterSelect = new ComboBox<>();
        private final JBTextField lineWidthText = new JBTextField();
        private final JBTextField pageNoText = new JBTextField();

        public AppSettingsComponent() {
            bookPathText.addBrowseFolderListener(new TextBrowseFolderListener(new FileChooserDescriptor(true, false, false, false, false, false)
                    .withFileFilter(f -> f.getName().toLowerCase().endsWith(".epub"))));

            /*bookPathText.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent evt) {
                    System.out.println("keyTyped -> " + evt.paramString());
                }

                @Override
                public void keyPressed(KeyEvent evt) {
                    System.out.println("keyPressed -> " + evt.paramString());
                }

                @Override
                public void keyReleased(KeyEvent evt) {
                    System.out.println("keyReleased -> " + evt.paramString());
                }
            });
            bookPathText.addVetoableChangeListener(evt -> {
                System.out.println("addVetoableChangeListener -> " + evt.getOldValue() + "paramString -> " + evt.getNewValue() + ", getSource -> " + evt.getSource());
            });
            bookPathText.addInputMethodListener(new InputMethodListener() {
                @Override
                public void inputMethodTextChanged(InputMethodEvent evt) {
                    System.out.println("inputMethodTextChanged -> " + evt.getText() + ", paramString -> " + evt.paramString() + ", getSource -> " + evt.getSource());
                }

                @Override
                public void caretPositionChanged(InputMethodEvent evt) {
                    System.out.println("caretPositionChanged -> " + evt.getText() + ", paramString -> " + evt.paramString() + ", getSource -> " + evt.getSource());
                }
            });*/

            mainPanel = FormBuilder.createFormBuilder()
                    .addLabeledComponent(new JBLabel("Enter book path: "), bookPathText, 1, false)
                    .addLabeledComponent(new JBLabel("Chapter select: "), chapterSelect, 1, false)
                    .addLabeledComponent(new JBLabel("Line width: "), lineWidthText, 1, false)
                    .addLabeledComponent(new JBLabel("PageNo: "), pageNoText, 1, false)
                    .addComponentFillVertically(new JPanel(), 0)
                    .getPanel();


//            chapterSelect.addItemListener(e -> {
//                if (e.getStateChange() == ItemEvent.SELECTED) {
//                    String selectedOption = (String) e.getItem();
//                    System.out.println(e.getID() + " --- " + selectedOption);
//                }
//            });
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
            chapterSelect.removeAllItems();
            if (StringUtils.isBlank(newText)) return;
            try {
                Book book = AppSettingsState.getBook(newText);
                book.getTableOfContents().getTocReferences().forEach(each -> chapterSelect.addItem(each.getTitle()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @NotNull
        public String getChapter() {
            return (String) chapterSelect.getSelectedItem();
        }

        public void setChapter(@NotNull String newText) {
            chapterSelect.setSelectedItem(newText);
        }

        @NotNull
        public int getLineWidthText() {
            return StringUtils.isBlank(lineWidthText.getText()) ? 0 : Integer.parseInt(lineWidthText.getText());
        }

        public void setLineWidthText(@NotNull int newText) {
            lineWidthText.setText(String.valueOf(newText));
        }

        @NotNull
        public int getPageNoText() {
            return StringUtils.isBlank(pageNoText.getText()) ? 0 : Integer.parseInt(pageNoText.getText());
        }

        public void setPageNoText(@NotNull int newText) {
            pageNoText.setText(String.valueOf(newText));
        }

    }
}


