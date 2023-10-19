package com.hktianyi.ebook_reader.control;

import com.hktianyi.ebook_reader.service.PageService;
import com.intellij.codeInsight.hint.HintManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class NextPage extends AnAction implements PageService {


    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        page(e, nextContent());
    }
}
