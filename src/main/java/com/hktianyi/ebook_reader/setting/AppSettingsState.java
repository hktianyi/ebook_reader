package com.hktianyi.ebook_reader.setting;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import com.intellij.util.xmlb.annotations.Transient;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.domain.TOCReference;
import nl.siegmann.epublib.domain.TableOfContents;
import nl.siegmann.epublib.epub.EpubReader;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Optional;

@State(
        name = "com.hktianyi.ebook_reader.setting.AppSettingsState",
        storages = @Storage("EbookReader.xml")
)
public class AppSettingsState implements PersistentStateComponent<AppSettingsState> {

    public String bookPath = "";
    public int lineWidth = 100;
    //    1 动态宽度，尽量按标点分页，页宽尽量一致
//    2 固定宽度
//    public int lineWidthType = 1;
    public String chapterName = "";
    public int pageNo = -1;
    @Transient
    public int pageMaxNo = -1;
    @Transient
    public LinkedList<String> lines = new LinkedList<>();

    public static AppSettingsState getInstance() {
        return ApplicationManager.getApplication().getService(AppSettingsState.class);
    }

    @Override
    public @Nullable AppSettingsState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull AppSettingsState state) {
        XmlSerializerUtil.copyBean(state, this);
        if (StringUtils.isNotBlank(bookPath)) {
            init();
        }
    }

    public void init() {
        try {
            Book book = getBook(bookPath);
            lines.clear();
            TableOfContents tableOfContents = book.getTableOfContents();
            Optional<Resource> resourceOptional = tableOfContents.getTocReferences().stream()
                    .filter(each -> each.getTitle().equals(chapterName))
                    .findFirst()
                    .map(TOCReference::getResource);
            if (resourceOptional.isEmpty()) {
                return;
            }
            String html = new String(resourceOptional.get().getData());

            Document doc = Jsoup.parse(html);
            Elements elements = doc.select("p");
            for (Element element : elements) {

                String text = element.text();
                if (text.length() <= lineWidth * 1.2) {
                    lines.add(text);
                    continue;
                }
                int start = 0;
                String sub;
                do {
                    sub = text.substring(start, Math.min(start += lineWidth, text.length()));
                    lines.add(sub);
                } while (start < text.length());
            }
            pageMaxNo = lines.size() - 1;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Book getBook(String path) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(path)) {
            EpubReader epubReader = new EpubReader();
            return epubReader.readEpub(inputStream);
        }
    }
}
