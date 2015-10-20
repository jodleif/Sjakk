package GUI;
import GUI.Sjakk.Rute;
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
	private GridPane gridPane;
	private BorderPane borderPane;
	private Rute[][] ruter;
	private Brett sjakkBrett;
	public static final double HEIGHT = 740;
	public static final double WIDTH = 740;
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		primaryStage.setTitle("Sjakk - Jo Øivind Gjernes");

		// Init

		ruter = new Rute[Brett.BRETTSTØRRELSE][Brett.BRETTSTØRRELSE];
		rot = new Group();
		scene = new Scene(rot,WIDTH,HEIGHT);
		gridPane = new GridPane();
		borderPane = new BorderPane();
		initSjakk(); // Start sjakkbrett.
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

	private void initRuter(){
		for(int i=0;i<Brett.BRETTSTØRRELSE;++i){
			for(int j=0;j<Brett.BRETTSTØRRELSE;++j){
				Brikke tempBrikke = sjakkBrett.getBrikke(Koordinater.fra_koordinater(new int[] {i,j}));
				if(tempBrikke!=null){
					ruter[i][j] = new Rute(tempBrikke.brikkenavn(), tempBrikke.getFarge());
				} else {
					ruter[i][j] = new Rute();
				}
				gridPane.add(ruter[i][j],i,7-j); // OBS: Må tegne "opp ned" siden gridpane starter med (0,0) i øvre venstre hjørne, ikke nedre som sjakk
			}
		}
	}

	private void initSjakk(){
		sjakkBrett = new Brett(0);
	}
	private void byggSpillBrett(){
		borderPane.setPrefSize(740,740);
		gridPane.setPrefSize(620,620);
		leggTilPadding();
		borderPane.setCenter(gridPane);
		initRuter();
		rot.getChildren().add(borderPane);
	}
	private void leggTilPadding(){
		borderPane.setTop(new Padding(WIDTH,60));
		borderPane.setBottom(new Padding(WIDTH,60));
		borderPane.setLeft(new Padding(60,HEIGHT));
		borderPane.setRight(new Padding(60,HEIGHT));
	}
}
