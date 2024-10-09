/**
 * @author 22208371 Cailleau Dylan - Ecriture de la classe et documentation
 */
package controleur;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import dao.CreneauDAO;
import dao.SingleConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import metier.Creneau;

/**
 * Cette classe permet d'intéragir avec la classe et les données des créneaux :
 *
 * @param dateFocus ZonedDateTime représente la date avec l'heure écrit sous
 *                  cette forme : 2007-12-03T10:15:30+01:00
 * @param today     ZonedDateTime permet de récupérer la date du système
 * @param stage     Stage
 * @param scene     Scene
 * @param root      Parent
 * @param year      Text
 * @param month     Text
 * @param calendar  FlowPane représente la zone du FXML où doit être afficher
 *                  les créneaux
 *
 */
public class ControleurCreneau implements Initializable {

	/**
	 * Ces attributs permettent d'initialiser le tableau table qui affiche des
	 * créneaux, et prends donc en argument les colonnes et spécifications suivantes
	 * :
	 *
	 * @param date     TableColumn qui représente l'attribut date(Date) d'un créneau
	 * @param heuredeb TableColumn qui représente l'attribut heureDeb(Time) d'un
	 *                 créneau
	 * @param heurefin TableColumn qui représente l'attribut heureFin(Time) d'un
	 *                 créneau
	 * @param idC      TableColumn qui représente l'attribut idC(int) d'un créneau
	 * @param table    TableView est le nom du tableau qui affiche les créneaux à
	 *                 partir des colonnes créée précédement
	 */
	@FXML
	private TableColumn<Creneau, Date> date;

	@FXML
	private TableColumn<Creneau, Time> heureDeb;

	@FXML
	private TableColumn<Creneau, Time> heureFin;

	@FXML
	private TableColumn<Creneau, Integer> idC;

	@FXML
	private TableView<Creneau> table;

	/**
	 * Il s'agit des attributs qui permettent d'afficher le mois et l'année actuel
	 *
	 * @param year  Text représente l'année
	 * @param month Text représente le mois
	 */

	@FXML
	private Text year;

	@FXML
	private Text month;

	/**
	 * calendar est le nom de la zone dans le fxml qui permet d'afficher les
	 * argument générée dans la méthode drawcalendar() afin d'y afficher un
	 * calendrier au mois
	 *
	 * @param calendar FlowPane
	 */

	@FXML
	private FlowPane calendar;

	/**
	 * Déclaration des dates qui vont indiquer la date actuel
	 *
	 * @param dateFocus ZonedDateTime
	 * @param today     ZonedDateTime
	 */

	ZonedDateTime dateFocus;
	ZonedDateTime today;

	/**
	 * Déclaration de l'objet créneau et de son DAO
	 *
	 * @param creneauDAO CreneauDAO
	 * @param creneau    Creneau
	 */

	private CreneauDAO creneauDAO;
	Creneau creneau = null;

	/**
	 * Déclaration des arguments utilisées dans les switchs
	 *
	 * @param stage  Stage
	 * @param scene  Scene
	 * @param Parent root
	 */

	private Stage stage;
	private Scene scene;
	private Parent root;

	/**
	 * Déclaration des arguments utilisées dans les requête sql
	 *
	 * @param query             String
	 * @param preparedStatement PreparedStatement
	 * @param resultSet         ResultSet
	 * @param connect           Connection
	 */
	String query = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	Connection connect = null;

	/**
	 * Initialisation de la vue du tableau
	 *
	 * @param Creneaulist ObservableList de type Creneau, qui va se voir affecté les
	 *                    créneau récupéré dans les requêtes sql
	 */

	ObservableList<Creneau> Creneaulist = FXCollections.observableArrayList();

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {

		creneauDAO = new CreneauDAO();

		loadDate();

		// Initialisation des dates à celles d'aujourd'hui
		dateFocus = ZonedDateTime.now();
		today = ZonedDateTime.now();

		drawCalendar();

		// Il s'agit d'un écouteur qui se déclenche dès que l'on clic sur une case du
		// tableau
		table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				// Appel de la méthode pour gérer le clic sur la ligne
				handleRowClick(newSelection);
			}
		});
	}

	private void handleRowClick(Creneau selectedCreneau) {

		openDetailsWindow(selectedCreneau);
	}

	/**
	 * Permet d'ouvrir la page CreneauDetail en lui passant les arguments du créneau
	 * selectionné précédement via le tableau
	 *
	 * @param creneau Creneau
	 */
	private void openDetailsWindow(Creneau creneau) {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/vues/CreneauDetail.fxml"));
			Parent parent = loader.load();

			// Déclaration du controleur
			ControleurDetailsCreneau detailsController = loader.getController();

			// Initialisation du controleur avec les attributs du créneau selectionné
			detailsController.setCreneauDetails(creneau);
			detailsController.miseEnPlaceListeViolations();

			Scene scene = new Scene(parent);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.initStyle(StageStyle.TRANSPARENT);
			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Permet de changer le mois actuel en le décrémentant
	 *
	 * @param event Action event généralement un bouton qui indique la pression de
	 *              ce dernier
	 */
	@FXML
	void backOneMonth(ActionEvent event) {
		dateFocus = dateFocus.minusMonths(1);
		calendar.getChildren().clear();
		drawCalendar();
	}

	/**
	 * Permet de changer le mois actuel en l'incrémentant
	 *
	 * @param event Action event généralement un bouton qui indique la pression de
	 *              ce dernier
	 */
	@FXML
	void forwardOneMonth(ActionEvent event) {
		dateFocus = dateFocus.plusMonths(1);
		calendar.getChildren().clear();
		drawCalendar();
	}

	/**
	 * Permet de fermer la fenêtre actuellement ouverte
	 *
	 * @param event Action event généralement un bouton qui indique la pression de
	 *              ce dernier
	 */
	@FXML
	private void close(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
	}

	/**
	 * Permet de réduire la fenêtre et de la mettre dans la barre des tâches
	 *
	 * @param event Action event généralement un bouton qui indique la pression de
	 *              ce dernier
	 */
	@FXML
	private void min(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setIconified(true);
	}

	/**
	 * Permet d'agrandir l'appplication pour qu'elle soit afficher sur tout l'écran
	 *
	 * @param event Action event généralement un bouton qui indique la pression de
	 *              ce dernier
	 */
	@FXML
	private void max(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setFullScreen(true);
	}

	/**
	 * Permet de switch vers la page Creneau en récupérant la page actuelle et en la
	 * remplaçant
	 *
	 * @param event Action event généralement un bouton qui indique la pression de
	 *              ce dernier
	 * @throws IOException renvoit un message en cas d'erreur
	 */
	public void switchToCreneau(ActionEvent event) throws IOException {

		root = FXMLLoader.load(getClass().getResource("/vues/Creneau.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);

		stage.show();
		stage.centerOnScreen();
	}

	/**
	 * Permet de switch vers la page PersonnelEDTChoix en récupérant la page
	 * actuelle et en la remplaçant
	 *
	 * @param event Action event généralement un bouton qui indique la pression de
	 *              ce dernier
	 * @throws IOException renvoit un message en cas d'erreur
	 */

	public void switchToPersonnelEDT(ActionEvent event) throws IOException {

		root = FXMLLoader.load(getClass().getResource("/vues/PersonnelEDTChoix.fxml"));
		stage = new Stage();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.initStyle(StageStyle.TRANSPARENT);

		stage.show();
		stage.centerOnScreen();
	}

	/**
	 * Permet de switch vers la page ListePersonnels2 en récupérant la page actuelle
	 * et en la remplaçant
	 *
	 * @param event Action event généralement un bouton qui indique la pression de
	 *              ce dernier
	 * @throws IOException renvoit un message en cas d'erreur
	 */

	public void switchToPersonnel(ActionEvent event) throws IOException {

		root = FXMLLoader.load(getClass().getResource("/vues/ListePersonnels2.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);

		stage.show();
		stage.centerOnScreen();
	}

	/**
	 * la méthode switchToAddCreneau permet de changer la scène actuelle vers la vue
	 * addCreneau.
	 *
	 * @param event : L'événement déclencheur de l'action de changement de scène (un
	 *              bouton)
	 * @throws IOException : En cas d'erreur lors du chargement de la vue
	 *                     addCreneau.fxml
	 */
	public void switchToAddCreneau(final ActionEvent event) throws IOException {

		root = FXMLLoader.load(getClass().getResource("/vues/addCreneau.fxml"));
		stage = new Stage();
		scene = new Scene(root);
		stage.setScene(scene);

		stage.show();
		stage.centerOnScreen();
	}

	/**
	 * Permet de switch vers la page afficheFonction en récupérant la page actuelle
	 * et en la remplaçant
	 *
	 * @param event Action event généralement un bouton qui indique la pression de
	 *              ce dernier
	 * @throws IOException renvoit un message en cas d'erreur
	 */

	public void switchToFonction(ActionEvent event) throws IOException {

		root = FXMLLoader.load(getClass().getResource("/vues/afficheFonction.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);

		stage.show();
		stage.centerOnScreen();
	}

	/**
	 * Permet de switch vers la page ConsulterSpecialites en récupérant la page
	 * actuelle et en la remplaçant
	 *
	 * @param event Action event généralement un bouton qui indique la pression de
	 *              ce dernier
	 * @throws IOException renvoit un message en cas d'erreur
	 */
	public void switchToSpecialite(ActionEvent event) throws IOException {

		root = FXMLLoader.load(getClass().getResource("/vues/ConsulterSpecialites.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);

		stage.show();
		stage.centerOnScreen();
	}

	/**
	 * Permet de switch vers la page Probleme en récupérant la page actuelle et en
	 * la remplaçant
	 *
	 * @param event Action event généralement un bouton qui indique la pression de
	 *              ce dernier
	 * @throws IOException renvoit un message en cas d'erreur
	 */
	public void switchToProbleme(ActionEvent event) throws IOException {

		root = FXMLLoader.load(getClass().getResource("/vues/Probleme.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);

		stage.show();
		stage.centerOnScreen();
	}

	/**
	 * Permet de switch vers la page Contrainte en récupérant la page actuelle et en
	 * la remplaçant
	 *
	 * @param event Action event généralement un bouton qui indique la pression de
	 *              ce dernier
	 * @throws IOException renvoit un message en cas d'erreur
	 */
	public void switchToContrainte(ActionEvent event) throws IOException {

		root = FXMLLoader.load(getClass().getResource("/vues/Contrainte.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);

		stage.show();
		stage.centerOnScreen();
	}

	/**
	 * Permet d'afficher la page addCreneau par dessus la page actuellement ouverte
	 *
	 * @param event Action event généralement un bouton qui indique la pression de
	 *              ce dernier
	 * @throws IOException renvoit un message en cas d'erreur
	 */
	@FXML
	private void getAddView(ActionEvent event) {
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("/vues/addCreneau.fxml"));
			Scene scene = new Scene(parent);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.initStyle(StageStyle.TRANSPARENT);
			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Il s'agit de la méthode qui incorpore les attributs du/des créneau(x)
	 * récupéré(s) à chaque colonne correspondante pour ensuite les ajouter au
	 * tableau
	 */
	private void loadDate() {

		try {
			connect = SingleConnection.getInstance();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		refreshTable();

		idC.setCellValueFactory(new PropertyValueFactory<>("idC"));
		date.setCellValueFactory(new PropertyValueFactory<>("date"));
		heureDeb.setCellValueFactory(new PropertyValueFactory<>("heureDeb"));
		heureFin.setCellValueFactory(new PropertyValueFactory<>("heureFin"));

		table.setItems(Creneaulist);

	}

	/**
	 * Cette méthode permet de recharger la table en récupérant tout les créneaux de
	 * la table
	 *
	 * @throws IOException renvoit un message en cas d'erreur
	 */
	@FXML
	private void refreshTable() {

		try {
			Creneaulist.clear();

			query = "select * from `creneau`";
			preparedStatement = connect.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Creneaulist.add(new Creneau(resultSet.getInt("idCreneau"), resultSet.getString("dateCreneau"),
						resultSet.getString("horaireDeb"), resultSet.getString("horaireFin")));
				table.setItems(Creneaulist);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Cette méthode permet de générer le calendrier de la page en utilisant des
	 * rectangle comme jours et affectant une indication verte quand le jour en
	 * question à au moins un créneau qui lui est affecté
	 */
	private void drawCalendar() {
		year.setText(String.valueOf(dateFocus.getYear()));
		month.setText(String.valueOf(dateFocus.getMonth()));

		double calendarWidth = calendar.getPrefWidth();
		double calendarHeight = calendar.getPrefHeight();
		double strokeWidth = 1;
		double spacingH = 10.0;
		double spacingV = calendar.getVgap();

		// création de la hbox ayant les noms des jours
		HBox dayNamesBox = createDayNamesBox(calendarWidth, spacingH);
		calendar.getChildren().add(dayNamesBox);

		// Calcule le nombre maximal de jours dans le mois
		int monthMaxDate = dateFocus.getMonth().maxLength();
		if (dateFocus.getYear() % 4 != 0 && monthMaxDate == 29) {
			monthMaxDate = 28;
		}
		int dateOffset = ZonedDateTime
				.of(dateFocus.getYear(), dateFocus.getMonthValue(), 1, 0, 0, 0, 0, dateFocus.getZone()).getDayOfWeek()
				.getValue();

		// Cette boucle génère les rectangle du mois
		for (int i = 0; i < monthMaxDate + dateOffset; i++) {
			final int currentDate = i - dateOffset + 1;
			final int var = i;

			// Création du StackPane de chaque jour
			StackPane stackPane = new StackPane();

			// Création du rectangle pour chaque jour du mois
			Rectangle rectangle = new Rectangle();
			rectangle.setFill(Color.rgb(128, 128, 128, 0.1));
			rectangle.setStroke(Color.TRANSPARENT);
			rectangle.setStrokeWidth(strokeWidth);
			rectangle.setArcWidth(10);
			rectangle.setArcHeight(10);

			// Detecte si un rectangle à été cliqué
			stackPane.setOnMouseClicked(event -> handleCalendarRectangleClick(event, var / 7, var % 7, currentDate));

			// Rajout d'un effet de survol
			rectangle.setOnMouseEntered(e -> rectangle.setFill(Color.rgb(128, 128, 128, 0.3))); // Couleur grise au
																								// survol
			rectangle.setOnMouseExited(e -> rectangle.setFill(Color.rgb(128, 128, 128, 0.1))); // Retour à la
																								// transparence

			double rectangleWidth = (calendarWidth / 8) - strokeWidth - spacingH;
			rectangle.setWidth(rectangleWidth);

			double rectangleHeight = (calendarHeight / 6.2) - strokeWidth - spacingV;
			rectangle.setHeight(rectangleHeight);

			// Ajout du numéto du jour
			if (currentDate > 0 && currentDate <= monthMaxDate) {
				// Partie pour le numéro du jour
				Text date = new Text(String.valueOf(currentDate));
				double textTranslationY = -(rectangleHeight / 2) * 0.75;
				date.setTranslateY(textTranslationY);
				stackPane.getChildren().add(date);

				// Ajoute l'indicateur si un créneau est présent
				if (hasCreneauForDay(currentDate)) {
					addCreneauIndicator(stackPane);
				}
			}
			// Ajout du rectangle au conteneur du jour et par la suite au calendrier
			stackPane.getChildren().add(rectangle);
			calendar.getChildren().add(stackPane);
		}
	}

	/**
	 * créer une indication couleur qui s'apparente à un cercle vert
	 *
	 * @param stackPane
	 */
	private void addCreneauIndicator(StackPane stackPane) {
		Circle indicator = new Circle();
		indicator.setFill(Color.GREEN);
		indicator.setStroke(Color.WHITE);
		indicator.setStrokeWidth(2);
		indicator.setRadius(10);

		stackPane.getChildren().add(indicator);
	}

	/**
	 * Permet de mettre à jour le tableau en fonction du jour (rectangle) cliqué
	 *
	 * @param event       MouseEvent qui indique le click d'un rectangle
	 * @param week        int le numéro de la semaine
	 * @param day         int le numéro du jour
	 * @param currentDate int la date actuel
	 */
	private void handleCalendarRectangleClick(MouseEvent event, int week, int day, int currentDate) {
		ZonedDateTime clickedDate = ZonedDateTime.of(dateFocus.getYear(), dateFocus.getMonthValue(), currentDate, 0, 0,
				0, 0, dateFocus.getZone());

		// Utilisation de creneauDAO pour obtenir les créneaux pour la date cliquée
		Timestamp sqlDate = Timestamp.valueOf(clickedDate.toLocalDateTime());
		List<Creneau> creneauxPourDate = creneauDAO.getAllCreneauDate(sqlDate);

		// Mise à jour du tableau avec les créneaux filtrés
		table.setItems(FXCollections.observableArrayList(creneauxPourDate));
	}

	/**
	 * Permet de vérifier si le jour séléctionné a au moins un créneau associé.
	 * Cette méthode utilise la date actuellement sélectionnée (dateFocus) et crée
	 * un objet ZonedDateTime pour représenter le jour donné puis la convertit en
	 * Timestamp pour la base de données. Elle interroge, ensuite, la base de
	 * données avec creneauDAO pour obtenir tous les créneaux associés à la date,
	 * retourne vrai si au moins un créneau est trouvé, autrement faux
	 *
	 * @param day Le jour du mois où il faut vérifier la présence de créneaux.
	 * @return true si au moins un créneau est associé au jour spécifié, sinon
	 *         false.
	 */
	private boolean hasCreneauForDay(int day) {
		ZonedDateTime clickedDate = ZonedDateTime.of(dateFocus.getYear(), dateFocus.getMonthValue(), day, 0, 0, 0, 0,
				dateFocus.getZone());
		Timestamp sqlDate = Timestamp.valueOf(clickedDate.toLocalDateTime());
		List<Creneau> creneauxPourDate = creneauDAO.getAllCreneauDate(sqlDate);
		return !creneauxPourDate.isEmpty();
	}

	/**
	 * Crée une HBox contenant les noms des jours de la semaine.
	 *
	 * @param calendarWidth La largeur totale du calendrier.
	 * @param spacingH L'espacement horizontal entre chaque nom de jour.
	 * @return Une boîte horizontale contenant les noms des jours de la semaine.
	 */
	private HBox createDayNamesBox(double calendarWidth, double spacingH) {
		//nom des jours de la semaine
		String[] dayNames = { "Lun.", "Mar.", "Mer.", "Jeu.", "Ven.", "Sam.", "Dim." };
		//Création d'une Hbox pour les contenirs
		HBox dayNamesBox = new HBox();

		//Boucle à travers des noms des jours (String[])
		for (String dayName : dayNames) {

			//Création d'un Text pour le nom du jour
			Text text = new Text(dayName);

			// Calcul de la largeur du texte en fonction de la largeur du calendrier et de l'espacement horizontal
			double textWidth = (calendarWidth / 8) - spacingH;

			// Définit la largeur du texte et la centre
			text.setWrappingWidth(textWidth);
			text.setTextAlignment(TextAlignment.CENTER);

			//ajout du texte au Hbox
			dayNamesBox.getChildren().add(text);
		}

		return dayNamesBox;
	}

}
