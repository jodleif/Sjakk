package GUI.Sjakk;

import Sjakk.Brett.Brett;
import Sjakk.Brikker.Brikke;
import Sjakk.Regler.Koordinater;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import sun.rmi.runtime.Log;

import java.util.ArrayList;

/**
 * Created by Jo Øivind Gjernes on 20.10.2015.
 */
public class SpilleBrett
{
	private Brett sjakkBrett;
	private GridPane gridPane;
	private Rute sistMerket;
	private Rute[][] ruter;
	public SpilleBrett(int spillnr)
	{
		sjakkBrett = new Brett(spillnr);
		gridPane = new GridPane();
		gridPane.setPrefSize(620,620);
		gridPane.setOnMouseClicked(e->mouseClickHandler(e));
		ruter = new Rute[8][8];
		initBrett();
		oppdaterBrett();
	}

	public void initBrett()
	{
		for (int i = 0; i < Brett.BRETTSTØRRELSE; ++i) {
			for (int j = 0; j < Brett.BRETTSTØRRELSE; ++j) {
				Rute tempRute = new Rute(Koordinater.fra_koordinater(new int[] {i,7-j}));
				ruter[i][7-j]=(tempRute); // Lagre en referanse til ruten.
				gridPane.add(tempRute, i, 7-j); // OBS: Må tegne "opp ned" siden gridpane starter med (0,0) i øvre venstre hjørne, ikke nedre som sjakk
			}
		}

	}

	public GridPane getGridPane() {return gridPane;}

	private void mouseClickHandler(MouseEvent event)
	{
		int[] arrPos = LogikkKobling.pixelTilArrPos(event.getX(),event.getY());

		Rute r = ruter[arrPos[0]][arrPos[1]];

		if(sistMerket==null){
			if(r.erSjakkbrikke()) {
				r.merk();
				sistMerket = r;
			}
		} else if (sistMerket.equals(r)) {
			r.merk();
			sistMerket = null;
		} else {
			String pos = Koordinater.fra_koordinater(arrPos);
			if(sjakkBrett.getBrikke(sistMerket.getPos()).flyttTil(pos)) {
				sistMerket.merk();
				sistMerket=null;
			} else {
				System.out.println("Ugyldig trekk!");
			}

		}

		oppdaterBrett();
	}

	private void oppdaterBrett()
	{
		for(int i=0;i<Brett.BRETTSTØRRELSE;++i){
			for(int j=0;j<Brett.BRETTSTØRRELSE;++j){
				ruter[i][j].oppdater(sjakkBrett.getBrikke(Koordinater.fra_koordinater(new int[] {i,j})));
			}
		}
	}
}
