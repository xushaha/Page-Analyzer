package hexlet.code;

import io.ebean.annotation.Platform;
import io.ebean.dbmigration.DbMigration;

import java.io.IOException;

public class MigrationGenerator {

    public static void main(String[] args) throws IOException {
        // Создаём миграцию
        DbMigration dbMigration = DbMigration.create();

        // Указываем платформу
        dbMigration.addPlatform(Platform.H2, "h2");
        //dbMigration.addPlatform(Platform.POSTGRES, "postgres");

        // Генерируем миграцию
        dbMigration.generateMigration();
    }
}
