package hexlet.code.domain;


import io.ebean.Model;
import io.ebean.annotation.WhenCreated;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.Instant;
import java.util.List;
import javax.persistence.CascadeType;

@Entity
public class Url extends Model {

    // Указываем, что поле является первичным ключом
    // Значение будет генерироваться автоматически
    @Id //Specifies the primary key of an entity.
    private long id;

    private String name;

    @WhenCreated // Значение будет генерироваться автоматически
    private Instant createdAt;
}
