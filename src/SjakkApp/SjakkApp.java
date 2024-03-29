package SjakkApp;

import SjakkApp.SjakkGUI.SpilleBrett;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by Jo Øivind Gjernes on 20.10.2015.
 *
 * Kommentarer til øvingen:
 *
 *
 * Hovedklassen til SjakkApp-applikasjonen.
 *
 * Her opprettes hovedvinduet.
 *
 */
public class SjakkApp extends Application
{
	public static final double HEIGHT = 770;
	public static final double WIDTH = 740;
	private Group rot;
	//private Scene scene;
	private BorderPane borderPane;
	private SpilleBrett spilleBrett;
	private VBox bunnPanel;
	private Label statusFelt;

	public static void main(String[] args)
	{
		launch(args);
		System.exit(0);
	}

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		primaryStage.setTitle("SjakkLogikk - Jo Øivind Gjernes");

		// Init
		borderPane = new BorderPane();
		rot = new Group();
		Scene scene = new Scene(rot,WIDTH,HEIGHT);
		statusFelt = new Label();
		spilleBrett = new SpilleBrett(0, statusFelt);
		tegnBakgrunn();
		byggSpillBrett();
		leggTilTestKnapp();
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void tegnBakgrunn()
	{
		ImageView iv = HjelpeFunksjoner.lastImageViewFraFil("img/sjakkbrett.png");
		if(iv!=null){
			rot.getChildren().add(iv);
		}
	}
	private void leggTilTestKnapp(){
		HBox hbox = new HBox();
		hbox.setMaxHeight(30);
		Button b = new Button("Angre");
		Button b2 = new Button("Nytt spill");
		b.setPrefHeight(30);
		b2.setPrefHeight(30);
		b.setOnAction(e -> this.spilleBrett.angre());
		b2.setOnAction(e -> {
			borderPane.getChildren().remove(spilleBrett.getGridPane());
			spilleBrett = new SpilleBrett(0, statusFelt);
			borderPane.setCenter(spilleBrett.getGridPane());

		});
		bunnPanel.getChildren().add(hbox);
		hbox.getChildren().add(b);
		hbox.getChildren().add(b2);
		hbox.getChildren().add(statusFelt);
	}

	private void byggSpillBrett(){
		borderPane.setPrefSize(WIDTH, HEIGHT);
		leggTilPadding();
		borderPane.setCenter(spilleBrett.getGridPane());
		rot.getChildren().add(borderPane);
	}
	private void leggTilPadding(){
		borderPane.setTop(new Padding(WIDTH,60));
		bunnPanel = new VBox();
		borderPane.setBottom(bunnPanel);
		borderPane.setLeft(new Padding(60,HEIGHT));
		borderPane.setRight(new Padding(60,HEIGHT));
		bunnPanel.getChildren().add(new Padding(WIDTH,60));
	}
}
