package com.hktianyi.ebook_reader.service;

import com.hktianyi.ebook_reader.setting.AppSettingsState;
import com.intellij.codeInsight.hint.HintManager;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import org.jetbrains.annotations.NotNull;

public interface PageService {

    default void page(@NotNull AnActionEvent e, String content) {
        if (content == null) {
            HintManager.getInstance().hideAllHints();
            return;
        }
        HintManager.getInstance().showInformationHint(e.getRequiredData(CommonDataKeys.EDITOR), content);
    }

    default String preContent() {
        AppSettingsState state = AppSettingsState.getInstance();
        if (state.pageNo < 0) {
            return null;
        }
        state.pageNo--;
        return String.format("%s (%d/%d)", state.lines.get(state.pageNo), state.pageNo, state.pageMaxNo);
    }

    default String nextContent() {
        AppSettingsState state = AppSettingsState.getInstance();
        if (state.pageNo > state.pageMaxNo) {
            return null;
        }
        state.pageNo++;
        return String.format("%s (%d/%d)", state.lines.get(state.pageNo), state.pageNo, state.pageMaxNo);
    }
}
