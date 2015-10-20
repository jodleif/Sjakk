package GUI;
import GUI.Sjakk.LogikkKobling;
import GUI.Sjakk.Rute;
import GUI.Sjakk.SpilleBrett;
import Sjakk.Brett.Brett;
import Sjakk.Brikker.Brikke;
import Sjakk.Regler.Koordinater;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Created by Jo Øivind Gjernes on 20.10.2015.
 */
public class SjakkApp extends Application
{
	private Group rot;
	private Scene scene;
	private BorderPane borderPane;
	private SpilleBrett spilleBrett;
	public static final double HEIGHT = 740;
	public static final double WIDTH = 740;
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		primaryStage.setTitle("Sjakk - Jo Øivind Gjernes");

		// Init
		borderPane = new BorderPane();
		rot = new Group();
		scene = new Scene(rot,WIDTH,HEIGHT);
		spilleBrett = new SpilleBrett(0);
		tegnBakgrunn();
		byggSpillBrett();
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args)
	{
		launch(args);
		System.exit(0);
	}

	private void tegnBakgrunn()
	{
		ImageView iv = HjelpeFunksjoner.lastImageViewFraFil("img/sjakkbrett.png");
		if(iv!=null){
			rot.getChildren().add(iv);
		}
	}


	private void byggSpillBrett(){
		borderPane.setPrefSize(740,740);
		leggTilPadding();
		borderPane.setCenter(spilleBrett.getGridPane());
		rot.getChildren().add(borderPane);
	}
	private void leggTilPadding(){
		borderPane.setTop(new Padding(WIDTH,60));
		borderPane.setBottom(new Padding(WIDTH,60));
		borderPane.setLeft(new Padding(60,HEIGHT));
		borderPane.setRight(new Padding(60,HEIGHT));
	}
}
