package hexlet.code.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import io.ebean.typequery.Generated;

import io.ebean.config.ModuleInfo;
import io.ebean.config.ModuleInfoLoader;

@Generated("io.ebean.querybean.generator")
@ModuleInfo(entities={"hexlet.code.domain.Url"})
public class _Ebean$ModuleInfo implements ModuleInfoLoader {

  /**
   * Register AttributeConverter etc
   */
  private List<Class<?>> otherClasses() {
    return Collections.emptyList();
  }

  /**
   * Entities with no @DbName
   */
  private List<Class<?>> defaultEntityClasses() {
    List<Class<?>> entities = new ArrayList<>();
    entities.add(hexlet.code.domain.Url.class);
    return entities;
  }

  private List<Class<?>> classesFor(String dbName) {
    return new ArrayList<>();
  }

  @Override
  public List<Class<?>> classesFor(String dbName, boolean defaultServer) {
    List<Class<?>> classes = classesFor(dbName);
    if (defaultServer) {
      classes.addAll(defaultEntityClasses());
    }
    return classes;
  }

}
