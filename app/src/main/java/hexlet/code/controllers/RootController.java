package hexlet.code.controllers;

import hexlet.code.domain.Url;
import hexlet.code.domain.query.QUrl;
import io.ebean.PagedList;
import io.javalin.http.Handler;

import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.ebean.DB.save;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RootController {
    public static Handler welcome = ctx -> {
        ctx.render("index.html");
    };

    public static Handler addUrl = ctx -> {
        Logger logger = LoggerFactory.getLogger(RootController.class);
        logger.info("Page loaded");

        String name = ctx.formParam("url");

        if (name.isEmpty() || !(name.startsWith("http")) || !(name.startsWith("https"))) {
           ctx.sessionAttribute("flash", "Ссылка некорректная");
           ctx.sessionAttribute("flash-type", "danger");
           ctx.redirect("/");
        }


        URL url = new URL(name);
        String normalizedUrl = url.getProtocol() + "://" + url.getAuthority();
        logger.debug("Получение URL {}", normalizedUrl);

        Url urlForCheck = new QUrl()
                .name.equalTo(normalizedUrl)
                .findOne();

        if (urlForCheck != null) {
            ctx.sessionAttribute("flash", "Ссылка уже существует");
            ctx.sessionAttribute("flash-type", "info");
            ctx.redirect("/");
        }

        Url urlForSave = new Url(normalizedUrl);
        save(urlForSave);
        ctx.sessionAttribute("flash", "Ссылка успешно добавлена");
        ctx.sessionAttribute("flash-type", "success");

        ctx.redirect("/urls");
    };

    public static Handler listUrls = ctx -> {
        int page = ctx.queryParamAsClass("page", Integer.class).getOrDefault(1) - 1;
        int rowsPerPage = 10;

        PagedList<Url> pagedUrls = new QUrl()
                .setFirstRow(page * rowsPerPage)
                .setMaxRows(rowsPerPage)
                .orderBy()
                .id.asc()
                .findPagedList();

        List<Url> urls = pagedUrls.getList();

        int lastPage = pagedUrls.getTotalPageCount() + 1;
        int currentPage = pagedUrls.getPageIndex() + 1;

        List<Integer> pages = IntStream
                .range(1, lastPage)
                .boxed()
                .collect(Collectors.toList());

        ctx.attribute("urls", urls);
        ctx.attribute("pages", pages);
        ctx.attribute("currentPage", currentPage);
        ctx.render("urls/index.html");
    };

    public static Handler showUrl = ctx -> {
        long id = ctx.pathParamAsClass("id", Long.class).getOrDefault(null);

        Url url = new QUrl()
                .id.equalTo(id)
                .findOne();
        if (url == null) {
            ctx.sessionAttribute("flash-type", "info");
            ctx.redirect("/");
        }
        ctx.attribute("url", url);
        ctx.render("urls/show.html");
    };



}
