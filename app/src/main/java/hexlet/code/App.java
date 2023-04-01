package hexlet.code;

import hexlet.code.controllers.RootController;
import hexlet.code.controllers.UrlController;
import io.javalin.Javalin;
import io.javalin.plugin.rendering.template.JavalinThymeleaf;
import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.post;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    private static final String PORT = "8080";
    private static final String DEVELOPMENT = "development";
    private static final String PRODUCTION = "production";

    public static void main(String[] args) {
        Javalin app = getApp();
        app.start();
        logger.info("Page loaded");
    }

    private static String getMode() {
        return System.getenv().getOrDefault("APP_ENV", DEVELOPMENT);
    }

    private static int getPort() {
        String port = System.getenv().getOrDefault("PORT", PORT);
        return Integer.parseInt(port);
    }

    private static boolean isProduction() {
        return getMode().equals(PRODUCTION);
    }

    public static Javalin getApp() {

        Javalin app = Javalin.create(config -> {
            if (!isProduction()) {
                config.enableDevLogging();
            }
            config.enableWebjars();
            JavalinThymeleaf.configure(getTemplateEngine());
        });

        addRoutes(app);

        app.before(ctx -> {
            ctx.attribute("ctx", ctx);
        });

        return app;
    }

    private static void addRoutes(Javalin app) {
        app.get("/", RootController.welcome);

        app.routes(() -> {
            path("urls", () -> {
                post(UrlController.addUrl);
                get(UrlController.listUrls);
                path("{id}", () -> {
                    get(UrlController.showUrl);
                    path("checks", () -> {
                        post(UrlController.checkUrl);
                    });
                });
            });
        });
    }




    private static TemplateEngine getTemplateEngine() {
        // Создаём инстанс движка шаблонизатора
        TemplateEngine templateEngine = new TemplateEngine();
        // Добавляем к нему диалекты
        templateEngine.addDialect(new LayoutDialect());
        templateEngine.addDialect(new Java8TimeDialect());
        // Настраиваем преобразователь шаблонов, так, чтобы обрабатывались
        // шаблоны в директории /templates/
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("/templates/");
        // Добавляем преобразователь шаблонов к движку шаблонизатора
        templateEngine.addTemplateResolver(templateResolver);

        return templateEngine;
    }


}
