package hexlet.code;

import hexlet.code.domain.Url;
import hexlet.code.domain.query.QUrl;
import io.ebean.DB;
import io.ebean.Database;
import io.ebean.Transaction;
import io.javalin.Javalin;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AppTest {


    @Test
    void testInit() {
        assertThat(true).isEqualTo(true);
    }

    private static Javalin app;
    private static String baseUrl;
    private static Transaction transaction;
    private static Url existingUrl;
    private static Database database;

    // Метод выполнится перед всеми тестами в классе
    @BeforeAll
    public static void beforeAll() {
        // Получаем инстанс приложения
        app = App.getApp();
        // Запускаем приложение на рандомном порту
        app.start(0);
        // Получаем порт, на котором запустилось приложение
        int port = app.port();
        // Формируем базовый URL
        baseUrl = "http://localhost:" + port;
        database = DB.getDefault();
    }

    // Метод выполнится после окончания всех тестов в классе
    @AfterAll
    public static void afterAll() {
        // Останавливаем приложение
        app.stop();
    }

    // хорошей практикой будет возвращать базу данных между тестами в исходное состояние
    @BeforeEach
    void beforeEach() {
        database.script().run("/truncate.sql");
        database.script().run("/seed-test-db.sql");
    }


    @Test
    void testAddUrl() {

        // Выполняем POST запрос при помощи агента Unirest
        HttpResponse<String> responsePost = Unirest
                // POST запрос на URL
                .post(baseUrl + "/urls")
                // Устанавливаем значения полей
                .field("name", "https://ya.ru/")
                // Выполняем запрос и получаем тело ответ с телом в виде строки
                .asString();

        // Проверяем статус ответа
        assertThat(responsePost.getStatus()).isEqualTo(302);

        // Проверяем, что компания добавлена в БД
        Url actualUrl = new QUrl()
                .name.equalTo("https://ya.ru/")
                .findOne();

        assertThat(actualUrl).isNotNull();

        // И что её данные соответствуют переданным
        assertThat(actualUrl.getName()).isEqualTo("https://ya.ru/");
    }



    @Test
    void testListUrls() {
        // Выполняем GET запрос на адрес http://localhost:port/urls
        HttpResponse<String> response = Unirest
                .get(baseUrl + "/urls")
                .asString();
        // Получаем тело ответа
        String content = response.getBody();

        // Проверяем код ответа
        assertThat(response.getStatus()).isEqualTo(200);
        // Проверяем, что страница содержит определенный текст
        assertThat(response.getBody()).contains("https://vk.com/");
        assertThat(response.getBody()).contains("https://github.com/");
    }


    @Test
    void testShowUrl() {
        // Выполняем GET запрос на адрес http://localhost:port/users
        HttpResponse<String> response = Unirest
                .get(baseUrl + "/urls/1")
                .asString();
        // Проверяем код ответа
        assertThat(response.getStatus()).isEqualTo(200);
        // Проверяем, что страница содержит определенный текст
        assertThat(response.getBody()).contains("https://github.com/");
    }



}


