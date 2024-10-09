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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import metier.Creneau;
import metier.Personnel;

public class ControleurPersonnelEDT implements Initializable {

	@FXML
	private Text nomText;

	@FXML
	private Text prenomText;

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

	@FXML
	private Text year;

	@FXML
	private Text month;

	@FXML
	private FlowPane calendar;

	ZonedDateTime dateFocus;
	ZonedDateTime today;

	Personnel personnel;

	CreneauDAO creneauDAO;

	private Stage stage;
	private Scene scene;
	private Parent root;

	String query = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	Connection connect = null;

	Creneau creneau = null;

	ObservableList<Creneau> Creneaulist = FXCollections.observableArrayList();

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
	    creneauDAO = new CreneauDAO();

	    // Ne chargez pas les dates ici, car la propriété personnel n'est pas encore initialisée
	    // loadDate();

	    // Ajouter un écouteur d'événements pour le clic sur une ligne
	    table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
	        if (newSelection != null) {
	            // Appel de la méthode pour gérer le clic sur la ligne
	            handleRowClick(newSelection);
	        }
	    });
	}

	private void handleRowClick(Creneau selectedCreneau) {
		// Vous pouvez faire ce que vous voulez avec l'objet Creneau sélectionné
		// Par exemple, ouvrir une nouvelle fenêtre avec les détails du créneau
		openDetailsWindow(selectedCreneau);
	}

	private void openDetailsWindow(Creneau creneau) {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/vues/CreneauDetail.fxml"));
			Parent parent = loader.load();

			// Obtenez le contrôleur de la nouvelle fenêtre
			ControleurDetailsCreneau detailsController = loader.getController();

			// Passez les détails du créneau au contrôleur de la nouvelle fenêtre
			detailsController.setCreneauDetails(creneau);

			// Créez une nouvelle scène et affichez la fenêtre
			Scene scene = new Scene(parent);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.initStyle(StageStyle.TRANSPARENT);
			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
			// Gérez l'exception d'I/O
		}
	}

	@FXML
	void backOneMonth(ActionEvent event) {
		dateFocus = dateFocus.minusMonths(1);
		calendar.getChildren().clear();
		drawCalendar();
	}

	@FXML
	void forwardOneMonth(ActionEvent event) {
		dateFocus = dateFocus.plusMonths(1);
		calendar.getChildren().clear();
		drawCalendar();
	}

	@FXML
	private void close(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
	}

	@FXML
	private void min(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setIconified(true);
	}

	@FXML
	private void max(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setFullScreen(true);
	}

	public void setPersonnel(Personnel personnel) {
	    this.personnel = personnel;
	    nomText.setText(personnel.getNomPersonnel());
	    prenomText.setText(personnel.getPrenomPersonnel());

	    // Maintenant que la propriété personnel est initialisée, chargez les dates
	    loadDate();

	    dateFocus = ZonedDateTime.now();
	    today = ZonedDateTime.now();
	    drawCalendar();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	private void refreshTable() {

		try {
			Creneaulist.clear();

			query = "SELECT c.* "
					+ "FROM creneau c "
					+ "JOIN estaffecte e ON c.idCreneau = e.idBesoin "
					+ "WHERE e.idPersonnel = " + personnel.getIdPersonnel();
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

	private void drawCalendar() {
		year.setText(String.valueOf(dateFocus.getYear()));
		month.setText(String.valueOf(dateFocus.getMonth()));

		double calendarWidth = calendar.getPrefWidth();
		double calendarHeight = calendar.getPrefHeight();
		double strokeWidth = 1;
		double spacingH = 10.0;
		double spacingV = calendar.getVgap();

		HBox dayNamesBox = createDayNamesBox(calendarWidth, spacingH);
		calendar.getChildren().add(dayNamesBox);

		int monthMaxDate = dateFocus.getMonth().maxLength();
		if (dateFocus.getYear() % 4 != 0 && monthMaxDate == 29) {
			monthMaxDate = 28;
		}
		int dateOffset = ZonedDateTime
				.of(dateFocus.getYear(), dateFocus.getMonthValue(), 1, 0, 0, 0, 0, dateFocus.getZone()).getDayOfWeek()
				.getValue();

		for (int i = 0; i < monthMaxDate + dateOffset; i++) {
			final int currentDate = i - dateOffset + 1;
			final int var = i;

			StackPane stackPane = new StackPane();

			Rectangle rectangle = new Rectangle();
			rectangle.setFill(Color.rgb(128, 128, 128, 0.1));
			rectangle.setStroke(Color.TRANSPARENT);
			rectangle.setStrokeWidth(strokeWidth);
			rectangle.setArcWidth(10);
			rectangle.setArcHeight(10);

			stackPane.setOnMouseClicked(event -> handleCalendarRectangleClick(event, var / 7, var % 7, currentDate));

			rectangle.setOnMouseEntered(e -> rectangle.setFill(Color.rgb(128, 128, 128, 0.3))); // Couleur grise au
																								// survol
			// Ajoutez le gestionnaire d'événements pour le départ du survol de la souris
			rectangle.setOnMouseExited(e -> rectangle.setFill(Color.rgb(128, 128, 128, 0.1))); // Retour à la
																								// transparence

			double rectangleWidth = (calendarWidth / 8) - strokeWidth - spacingH;
			rectangle.setWidth(rectangleWidth);

			double rectangleHeight = (calendarHeight / 6.2) - strokeWidth - spacingV;
			rectangle.setHeight(rectangleHeight);

			if (currentDate > 0 && currentDate <= monthMaxDate) {
				// Partie pour le numéro du jour
				Text date = new Text(String.valueOf(currentDate));
				double textTranslationY = -(rectangleHeight / 2) * 0.75;
				date.setTranslateY(textTranslationY);
				stackPane.getChildren().add(date);

				// Partie pour l'indicateur de créneau
				if (hasCreneauForDay(currentDate)) {
					addCreneauIndicator(stackPane);
				}
			}

			stackPane.getChildren().add(rectangle);
			calendar.getChildren().add(stackPane);
		}
	}

	private void addCreneauIndicator(StackPane stackPane) {
		Circle indicator = new Circle();
		indicator.setFill(Color.GREEN); // Fill color of the indicator
		indicator.setStroke(Color.WHITE); // Border color of the indicator
		indicator.setStrokeWidth(2); // Border width of the indicator
		indicator.setRadius(10); // Radius of the indicator

		stackPane.getChildren().add(indicator);
	}

	private void handleCalendarRectangleClick(MouseEvent event, int week, int day, int currentDate) {
		ZonedDateTime clickedDate = ZonedDateTime.of(dateFocus.getYear(), dateFocus.getMonthValue(), currentDate, 0, 0,
				0, 0, dateFocus.getZone());

		// Utilisez creneauDAO pour obtenir les créneaux pour la date cliquée
		Timestamp sqlDate = Timestamp.valueOf(clickedDate.toLocalDateTime());
		List<Creneau> creneauxPourDate = creneauDAO.getAllCreneauDateID(sqlDate, personnel.getIdPersonnel());

		// Mettez à jour le tableau avec les créneaux filtrés
		table.setItems(FXCollections.observableArrayList(creneauxPourDate));
	}

	private boolean hasCreneauForDay(int day) {
		ZonedDateTime clickedDate = ZonedDateTime.of(dateFocus.getYear(), dateFocus.getMonthValue(), day, 0, 0, 0, 0,
				dateFocus.getZone());
		Timestamp sqlDate = Timestamp.valueOf(clickedDate.toLocalDateTime());
		List<Creneau> creneauxPourDate = creneauDAO.getAllCreneauDateID(sqlDate, personnel.getIdPersonnel());
		return !creneauxPourDate.isEmpty();
	}

	private List<CalendarActivity> getCalendarActivitiesForDay(int day) {
		Map<Integer, List<CalendarActivity>> activitiesByDay = getCalendarActivitiesMonth(dateFocus);

		// Replace this with your logic to fetch activities for the given day
		int dayOfMonth = dateFocus.withDayOfMonth(day).getDayOfMonth();
		List<CalendarActivity> activitiesForDay = activitiesByDay.getOrDefault(dayOfMonth, new ArrayList<>());

		return activitiesForDay;
	}

	private HBox createDayNamesBox(double calendarWidth, double spacingH) {
		String[] dayNames = { "Lun.", "Mar.", "Mer.", "Jeu.", "Ven.", "Sam.", "Dim." };
		HBox dayNamesBox = new HBox();

		for (String dayName : dayNames) {
			Text text = new Text(dayName);
			double textWidth = (calendarWidth / 8) - spacingH;
			text.setWrappingWidth(textWidth);
			text.setTextAlignment(TextAlignment.CENTER);
			dayNamesBox.getChildren().add(text);
		}

		return dayNamesBox;
	}

	private void createCalendarActivity(List<CalendarActivity> calendarActivities, double rectangleHeight,
			double rectangleWidth, StackPane stackPane) {
		VBox calendarActivityBox = new VBox();
		for (int k = 0; k < calendarActivities.size(); k++) {
			if (k >= 2) {
				Text moreActivities = new Text("...");
				calendarActivityBox.getChildren().add(moreActivities);
				moreActivities.setOnMouseClicked(mouseEvent -> {
					// On ... click print all activities for given date
					System.out.println(calendarActivities);
				});
				break;
			}
			Text text = new Text(calendarActivities.get(k).getClientName() + ", "
					+ calendarActivities.get(k).getDate().toLocalTime());
			calendarActivityBox.getChildren().add(text);
			text.setOnMouseClicked(mouseEvent -> {
				// On Text clicked
				System.out.println(text.getText());
			});
		}
		calendarActivityBox.setTranslateY((rectangleHeight / 2) * 0.20);
		calendarActivityBox.setMaxWidth(rectangleWidth * 0.8);
		calendarActivityBox.setMaxHeight(rectangleHeight * 0.65);
		calendarActivityBox.setStyle("-fx-background-color:GRAY");
		stackPane.getChildren().add(calendarActivityBox);
	}

	private Map<Integer, List<CalendarActivity>> createCalendarMap(List<CalendarActivity> calendarActivities) {
		Map<Integer, List<CalendarActivity>> calendarActivityMap = new HashMap<>();

		for (CalendarActivity activity : calendarActivities) {
			int activityDate = activity.getDate().getDayOfMonth();
			if (!calendarActivityMap.containsKey(activityDate)) {
				calendarActivityMap.put(activityDate, List.of(activity));
			} else {
				List<CalendarActivity> OldListByDate = calendarActivityMap.get(activityDate);

				List<CalendarActivity> newList = new ArrayList<>(OldListByDate);
				newList.add(activity);
				calendarActivityMap.put(activityDate, newList);
			}
		}
		return calendarActivityMap;
	}

	private Map<Integer, List<CalendarActivity>> getCalendarActivitiesMonth(ZonedDateTime dateFocus) {
		List<CalendarActivity> calendarActivities = new ArrayList<>();
		int year = dateFocus.getYear();
		int month = dateFocus.getMonth().getValue();

		Random random = new Random();
		for (int i = 0; i < 50; i++) {
			ZonedDateTime time = ZonedDateTime.of(year, month, random.nextInt(27) + 1, 16, 0, 0, 0,
					dateFocus.getZone());
			calendarActivities.add(new CalendarActivity(time, "test ", 111111));
		}

		return createCalendarMap(calendarActivities);
	}

}
