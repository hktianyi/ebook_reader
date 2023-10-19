package com.hktianyi.ebook_reader.control;

import com.intellij.codeInsight.hint.HintManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class HideHint extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        HintManager.getInstance().hideAllHints();
    }
}
