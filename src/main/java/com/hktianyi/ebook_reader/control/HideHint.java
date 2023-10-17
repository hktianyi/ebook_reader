package com.hktianyi.ebook_reader.control;

import com.intellij.codeInsight.hint.HintManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

public class HideHint extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        HintManager.getInstance().hideAllHints();
    }
}
