/**
 * Cette classe fournit une connexion unique à la base de données.
 *
 * @author 22106979 - Documentation & Implémentation
 */

package dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.mysql.cj.jdbc.MysqlDataSource;

/**
 * Cette classe permet la connexion à la base de données.
 */
public class SingleConnection {

  // valeur de la connexion
  private static Connection connect;

  /**
   * Constructeur par défaut. Cette méthode permet la connexion à la base de
   * données.
   *
   * @throws ClassNotFoundException Si un des paramètres ne fonctionne pas
   * @throws SQLException Si une erreur survient lors de l'exécution de
   *                      l'instruction SQL.
   */
  private SingleConnection(String serverName, String dbName, String login, String password)
      throws ClassNotFoundException, SQLException {
    // Parametres de connexion : url, login, mdp
    String url = "jdbc:mysql://" + serverName + ":3306/" + dbName + "?serverTimezone=UTC";
    // port mysql avec USBWebserver:3307
    // port mysq avec xampp 3306

    // Creation d'une connexion avec MysqlDataSource
    MysqlDataSource mysqlDS = new MysqlDataSource();
    mysqlDS.setURL(url);
    mysqlDS.setUser(login);
    mysqlDS.setPassword(password);

    connect = mysqlDS.getConnection();
  }

  /**
   * Renvoie la connexion actuelle à la base de données. Si aucune connexion
   * n'existe, une nouvelle est établie avec la base de données par défaut
   * (absences).
   *
   * @return connect sinon null - valeur représentant la connexion établit à la
   *         base de données
   * @throws ClassNotFoundException Si un des paramètres ne fonctionne pas
   * @throws SQLException Si une erreur survient lors de l'exécution de
   *                      l'instruction SQL.
   */
  public static Connection getInstance() throws ClassNotFoundException, SQLException {
    if (connect == null) {
      new SingleConnection("127.0.0.1", "planninghospitalier", "root", "");
    }
    return connect;
  }

  /**
   * Etablit une connexion à la base de données spécifiée en utilisant les
   * paramètres fournis.
   *
   * @param serverName [String] valeur du nom du serveur de la base de données
   * @param dbName     [String] valeur du nom de la base de données
   * @param login      [String] valeur du nom de l'identifiant de connexion
   * @param password   [String] valeur du mot de passe de l'identifiant de
   *                   connexion
   * @return [Connexion] si connexion fonctionne
   * @throws ClassNotFoundException Si
   * @throws SQLException Si une erreur survient lors de l'exécution de
   *                      l'instruction SQL.
   */
  public static Connection getInstance(
      String serverName,
      String dbName,
      String login,
      String password
  )
      throws ClassNotFoundException, SQLException {
    if (connect == null) {
      new SingleConnection(serverName, dbName, login, password);
    }
    return connect;
  }

  /**
   * Ferme la connexion actuelle à la base de données.
   */
  public static void close() {
    try {
      connect.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}