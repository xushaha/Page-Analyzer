package hexlet.code.controllers;

import hexlet.code.App;
import hexlet.code.domain.Url;
import hexlet.code.domain.UrlCheck;
import hexlet.code.domain.query.QUrl;
import io.ebean.PagedList;
import io.javalin.http.Handler;
import io.javalin.http.NotFoundResponse;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.ebean.DB.save;

public class UrlController {
    private static Logger logger = LoggerFactory.getLogger(App.class);

    public static Handler addUrl = ctx -> {
        String name = ctx.formParam("url");

        URL url;

        try {
            logger.debug("Trying to get link {}", name);
            url = new URL(Objects.requireNonNull(name));
        } catch (MalformedURLException e) {
            logger.debug("Invalid link");
            ctx.sessionAttribute("flash", "Ссылка некорректная");
            ctx.sessionAttribute("flash-type", "danger");
            ctx.redirect("/");
            return;
        }

        String normalizedUrl = url.getProtocol() + "://" + url.getAuthority();

        logger.debug("Check if the link {} already exists", normalizedUrl);
        Url urlFromDb = new QUrl()
                .name.equalTo(normalizedUrl)
                .findOne();

        if (urlFromDb != null) {
            logger.debug("Link {} already exists", normalizedUrl);
            ctx.sessionAttribute("flash", "Ссылка уже добавлена");
            ctx.sessionAttribute("flash-type", "info");
            ctx.redirect("/urls");
            return;
        }

        Url urlForSave = new Url(normalizedUrl);
        save(urlForSave);
        logger.info("Link added");
        ctx.sessionAttribute("flash", "Страница успешно добавлена");
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
            logger.debug("Link is not found");
            ctx.sessionAttribute("flash", "Страница не найдена");
            ctx.sessionAttribute("flash-type", "info");
            ctx.redirect("/");
        }
        ctx.attribute("url", url);
        ctx.render("urls/show.html");
    };


    public static Handler checkUrl = ctx -> {
        long id = ctx.pathParamAsClass("id", Long.class).getOrDefault(null);

        Url url = new QUrl()
                .id.equalTo(id)
                .findOne();

        if (url == null) {
            logger.debug("Url is not found");
            throw new NotFoundResponse(String.format("Url with id=%d is not found", id));
        }

        try {
            HttpResponse<String> response = Unirest.get(url.getName()).asString();
            Document doc = Jsoup.parse(response.getBody());

            int statusCode = response.getStatus();
            String title = doc.title();

            Element head1 = doc.selectFirst("h1");
            Element descr = doc.selectFirst("meta[name=description]");
            String h1 = (head1 != null) ? head1.text() : "";
            String description = (descr != null) ? descr.attr("content") : "";

            UrlCheck urlCheck = new UrlCheck(statusCode, title, h1, description, url);
            urlCheck.save();

            logger.info("The page has been checked");
            ctx.sessionAttribute("flash", "Страница успешно проверена");
            ctx.sessionAttribute("flash-type", "success");

        } catch (UnirestException e) {
            logger.debug("The page has not been checked");
            ctx.sessionAttribute("flash", "Не удалось проверить страницу");
            ctx.sessionAttribute("flash-type", "danger");
        }

        ctx.redirect("/urls/" + id);
    };

}
