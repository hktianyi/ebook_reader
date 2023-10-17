package com.hktianyi.ebook_reader.setting;

import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

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
        modified |= mySettingsComponent.getLineWidthText() != settings.lineWidth;
        modified |= mySettingsComponent.getPageNoText() != settings.pageNo;
        return modified;
    }

    @Override
    public void apply() {
        AppSettingsState settings = AppSettingsState.getInstance();
        settings.bookPath = mySettingsComponent.getBookPathText();
        settings.lineWidth = mySettingsComponent.getLineWidthText();
        settings.pageNo = mySettingsComponent.getPageNoText();
    }

    @Override
    public void reset() {
        AppSettingsState settings = AppSettingsState.getInstance();
        mySettingsComponent.setBookPathText(settings.bookPath);
        mySettingsComponent.setLineWidthText(settings.lineWidth);
        mySettingsComponent.setPageNoText(settings.pageNo);
    }

    @Override
    public void disposeUIResources() {
        mySettingsComponent = null;
    }
}
