package hexlet.code.domain.query;

import hexlet.code.domain.UrlCheck;
import hexlet.code.domain.query.assoc.QAssocUrl;
import io.ebean.Database;
import io.ebean.FetchGroup;
import io.ebean.Query;
import io.ebean.Transaction;
import io.ebean.typequery.Generated;
import io.ebean.typequery.PInstant;
import io.ebean.typequery.PInteger;
import io.ebean.typequery.PLong;
import io.ebean.typequery.PString;
import io.ebean.typequery.TQRootBean;
import io.ebean.typequery.TypeQueryBean;

/**
 * Query bean for UrlCheck.
 * 
 * THIS IS A GENERATED OBJECT, DO NOT MODIFY THIS CLASS.
 */
@Generated("io.ebean.querybean.generator")
@TypeQueryBean("v1")
public class QUrlCheck extends TQRootBean<UrlCheck,QUrlCheck> {

  private static final QUrlCheck _alias = new QUrlCheck(true);

  /**
   * Return the shared 'Alias' instance used to provide properties to 
   * <code>select()</code> and <code>fetch()</code> 
   */
  public static QUrlCheck alias() {
    return _alias;
  }

  public PLong<QUrlCheck> id;
  public PInstant<QUrlCheck> createdAt;
  public PInteger<QUrlCheck> statusCode;
  public PString<QUrlCheck> title;
  public PString<QUrlCheck> h1;
  public PString<QUrlCheck> description;
  public QAssocUrl<QUrlCheck> url;


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
  public static QUrlCheck forFetchGroup() {
    return new QUrlCheck(FetchGroup.queryFor(UrlCheck.class));
  }

  /**
   * Construct using the default Database.
   */
  public QUrlCheck() {
    super(UrlCheck.class);
  }

  /**
   * Construct with a given transaction.
   */
  public QUrlCheck(Transaction transaction) {
    super(UrlCheck.class, transaction);
  }

  /**
   * Construct with a given Database.
   */
  public QUrlCheck(Database database) {
    super(UrlCheck.class, database);
  }


  /**
   * Construct for Alias.
   */
  private QUrlCheck(boolean dummy) {
    super(dummy);
  }

  /**
   * Private constructor for FetchGroup building.
   */
  private QUrlCheck(Query<UrlCheck> fetchGroupQuery) {
    super(fetchGroupQuery);
  }

  /**
   * Provides static properties to use in <em> select() and fetch() </em>
   * clauses of a query. Typically referenced via static imports. 
   */
  public static class Alias {
    public static PLong<QUrlCheck> id = _alias.id;
    public static PInstant<QUrlCheck> createdAt = _alias.createdAt;
    public static PInteger<QUrlCheck> statusCode = _alias.statusCode;
    public static PString<QUrlCheck> title = _alias.title;
    public static PString<QUrlCheck> h1 = _alias.h1;
    public static PString<QUrlCheck> description = _alias.description;
    public static QAssocUrl<QUrlCheck> url = _alias.url;
  }
}
