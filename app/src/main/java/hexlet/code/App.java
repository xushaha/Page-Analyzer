package hexlet.code;

import io.javalin.Javalin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class App {

    public static Javalin getApp() {
        Javalin app = Javalin.create().start(7070);
        app.get("/", ctx -> ctx.result("Hello World"));
        return app;
    }

    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(App.class);
        logger.info("Hello World has been printed");
        Javalin app = getApp();
        app.start();
    }

}
