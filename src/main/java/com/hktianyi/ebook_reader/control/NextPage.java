package com.hktianyi.ebook_reader.control;

import com.hktianyi.ebook_reader.setting.AppSettingsState;
import com.intellij.codeInsight.hint.HintManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;

public class NextPage extends AnAction {


    @Override
    public void actionPerformed(AnActionEvent e) {
        AppSettingsState state = AppSettingsState.getInstance();
        if (state.pageNo >= state.pageMaxNo) {
            HintManager.getInstance().hideAllHints();
            return;
        }
        HintManager.getInstance()
                .showInformationHint(e.getRequiredData(CommonDataKeys.EDITOR),
                        state.lines.get(++state.pageNo) + "(" + state.pageNo + "/" + state.lines.size() + ")");
    }
}
