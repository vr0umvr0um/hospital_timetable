/**
 * @author 22106979 - Documentation & Implémentation
 */

package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Cette classe DAO permet la connexion à une base de données et d'effectuer des
 * requêtes CRUD sur cette dernière.
 */
public abstract class DAO<T> {

  // valeur de la connexion
  protected Connection connect;
  // valeur de la requête
  protected PreparedStatement stmt;

  /**
   * Constructeur par défaut.
   */
  public DAO() {
    open();
  }

  /**
   * Cette méthode permet la connexion à la base de données au travers de la
   * méthode SingleConnection.getInstance().
   */
  public void open() {
    try {
      connect = SingleConnection.getInstance();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      System.out.println(" === ERREUR OPEN DAO === ");
      e.printStackTrace();
    }
  }

  /**
   * Cette méthode ferme l'accès à la base de données.
   */
  public void close() {
    // on ferme l'acc�s � la BDD
    try {
      SingleConnection.close();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      System.out.println(" === ERREUR CLOSE DAO === ");
      e.printStackTrace();
    }
  }

  /**
   * Cette méthode permet d'insérer de nouvelles données dans la base de données,
   * par le biais de requêtes SQL.
   *
   * @param obj [T] valeur représentant un objet quelconque qui sera insérée dans
   *            sa table correspondante
   * @return [T]
   */
  public abstract T create(T obj);

  /**
   * Cette méthode permet de mettre à jour des données dans la base de données,
   * par le biais de requêtes SQL.
   *
   * @param obj [T] valeur représentant un objet quelconque qui sera insérée dans
   *            sa table correspondante
   * @return [T]
   */
  public abstract T update(T obj);

  /**
   * Cette méthode permet de supprimer des données dans la base de données, par le
   * biais de requêtes SQL.
   *
   * @param obj [T] valeur représentant un objet quelconque qui sera insérée dans
   *            sa table correspondante
   */
  public abstract void delete(T obj);

}
