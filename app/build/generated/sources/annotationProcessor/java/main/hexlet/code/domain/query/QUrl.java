package hexlet.code.domain.query;

import hexlet.code.domain.Url;
import io.ebean.Database;
import io.ebean.FetchGroup;
import io.ebean.Query;
import io.ebean.Transaction;
import io.ebean.typequery.Generated;
import io.ebean.typequery.PInstant;
import io.ebean.typequery.PLong;
import io.ebean.typequery.PString;
import io.ebean.typequery.TQRootBean;
import io.ebean.typequery.TypeQueryBean;

/**
 * Query bean for Url.
 * 
 * THIS IS A GENERATED OBJECT, DO NOT MODIFY THIS CLASS.
 */
@Generated("io.ebean.querybean.generator")
@TypeQueryBean("v1")
public class QUrl extends TQRootBean<Url,QUrl> {

  private static final QUrl _alias = new QUrl(true);

  /**
   * Return the shared 'Alias' instance used to provide properties to 
   * <code>select()</code> and <code>fetch()</code> 
   */
  public static QUrl alias() {
    return _alias;
  }

  public PLong<QUrl> id;
  public PString<QUrl> name;
  public PInstant<QUrl> createdAt;


  /**
   * Return a query bean used to build a FetchGroup.
   * <p>
   * FetchGroups are immutable and threadsafe and can be used by many
   * concurrent queries. We typically stored FetchGroup as a static final field.
   * <p>
   * Example creating and using a FetchGroup.
   * <pre>{@code
   * 
   * static final FetchGroup<Customer> fetchGroup = 
   *   QCustomer.forFetchGroup()
   *     .shippingAddress.fetch()
   *     .contacts.fetch()
   *     .buildFetchGroup();
   * 
   * List<Customer> customers = new QCustomer()
   *   .select(fetchGroup)
   *   .findList();
   * 
   * }</pre>
   */
  public static QUrl forFetchGroup() {
    return new QUrl(FetchGroup.queryFor(Url.class));
  }

  /**
   * Construct using the default Database.
   */
  public QUrl() {
    super(Url.class);
  }

  /**
   * Construct with a given transaction.
   */
  public QUrl(Transaction transaction) {
    super(Url.class, transaction);
  }

  /**
   * Construct with a given Database.
   */
  public QUrl(Database database) {
    super(Url.class, database);
  }


  /**
   * Construct for Alias.
   */
  private QUrl(boolean dummy) {
    super(dummy);
  }

  /**
   * Private constructor for FetchGroup building.
   */
  private QUrl(Query<Url> fetchGroupQuery) {
    super(fetchGroupQuery);
  }

  /**
   * Provides static properties to use in <em> select() and fetch() </em>
   * clauses of a query. Typically referenced via static imports. 
   */
  public static class Alias {
    public static PLong<QUrl> id = _alias.id;
    public static PString<QUrl> name = _alias.name;
    public static PInstant<QUrl> createdAt = _alias.createdAt;
  }
}
