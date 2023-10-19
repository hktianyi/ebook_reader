package com.hktianyi.ebook_reader.control;

import com.hktianyi.ebook_reader.service.PageService;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class PrePage extends AnAction implements PageService {


    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        page(e, preContent());
    }
}
