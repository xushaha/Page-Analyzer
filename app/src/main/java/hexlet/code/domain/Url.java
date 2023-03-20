package hexlet.code.domain;


import io.ebean.Model;
import io.ebean.annotation.WhenCreated;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.Instant;
import java.util.List;


@Entity
public final class Url extends Model {

    // Указываем, что поле является первичным ключом
    // Значение будет генерироваться автоматически
    @Id //Specifies the primary key of an entity.
    private long id;
    private String name;
    @WhenCreated // Значение будет генерироваться автоматически
    private Instant createdAt;
    @OneToMany
    private List<UrlCheck> urlChecks;

    public Url(String name) {
        this.name = name;
    }


    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public List<UrlCheck> getUrlChecks() {
        return urlChecks;
    }
}
