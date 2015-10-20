package GUI.Sjakk;

import Sjakk.Brett.Brett;
import Sjakk.Brikker.Brikke;
import Sjakk.Regler.Koordinater;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import sun.rmi.runtime.Log;

/**
 * Created by Jo Øivind Gjernes on 20.10.2015.
 */
public class SpilleBrett
{
	private Brett sjakkBrett;
	private GridPane gridPane;
	private String sistMerket;
	public SpilleBrett(int spillnr)
	{
		sjakkBrett = new Brett(spillnr);
		gridPane = new GridPane();
		gridPane.setPrefSize(620,620);
		gridPane.setOnMouseClicked(e->mouseClickHandler(e));
		initBrett();
	}

	public void initBrett()
	{
		for (int i = 0; i < Brett.BRETTSTØRRELSE; ++i) {
			for (int j = 0; j < Brett.BRETTSTØRRELSE; ++j) {
				Brikke tempBrikke = sjakkBrett.getBrikke(Koordinater.fra_koordinater(new int[]{i, j}));
				Rute tempRute;
				if (tempBrikke != null) {
					tempRute = new Rute(tempBrikke.brikkenavn(), tempBrikke.getFarge());
				} else {
					tempRute = new Rute();
				}
				gridPane.add(tempRute,i, 7 - j); // OBS: Må tegne "opp ned" siden gridpane starter med (0,0) i øvre venstre hjørne, ikke nedre som sjakk
			}
		}

	}

	public GridPane getGridPane() {return gridPane;}

	private void mouseClickHandler(MouseEvent event)
	{
		String pos = LogikkKobling.pixelTilSjakkPos(event.getX(),event.getY());

	}

	private void tegnPåNytt()
	{
		gridPane.getChildren().removeAll();
		for(int i=0;i<Brett.BRETTSTØRRELSE;++i){
			for(int j=0;j<Brett.BRETTSTØRRELSE;++j){
				Brikke tempBrikke = sjakkBrett.getBrikke(Koordinater.fra_koordinater(new int[]{i, j}));
				Rute tempRute;
				if(tempBrikke!=null){
					tempRute = new Rute(tempBrikke.brikkenavn(), tempBrikke.getFarge());
				} else {
					tempRute = new Rute();
				}
				gridPane.add(tempRute, i, 7-j);
			}
		}
	}

}
