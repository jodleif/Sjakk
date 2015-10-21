package GUI.Sjakk;

import Sjakk.Brett.Brett;
import Sjakk.Regler.Koordinater;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

/**
 * Created by Jo Øivind Gjernes on 20.10.2015.
 *
 * Spillebrettets representasjon i gui
 */
public class SpilleBrett
{
	private Brett sjakkBrett;
	private GridPane gridPane;
	private Rute sistMerket;
	private Rute[][] ruter;
	private String[] gyldigePos; // Lagres for å kunne fjerne avmerkinger. Kunne eventuelt søkt gjennom rutene for å fjerne.

	public SpilleBrett(int spillnr)
	{
		sjakkBrett = new Brett(spillnr);
		gridPane = new GridPane();
		gridPane.setPrefSize(620, 620);
		gridPane.setOnMouseClicked(e -> mouseClickHandler(e));
		ruter = new Rute[Brett.BRETTSTØRRELSE][Brett.BRETTSTØRRELSE];
		initBrett();
		oppdaterBrett();
	}

	public void initBrett()
	{
		for (int i = 0; i < Brett.BRETTSTØRRELSE; ++i) {
			for (int j = 0; j < Brett.BRETTSTØRRELSE; ++j) {
				Rute tempRute = new Rute(Koordinater.fra_koordinater(new int[]{i, j}));
				ruter[i][j] = (tempRute); // Lagre en referanse til ruten.
				gridPane.add(tempRute, i, 7-j); // OBS: Må tegne "opp ned" siden gridpane starter med (0,0) i øvre venstre hjørne, ikke nedre som sjakk
			}
		}

	}

	public GridPane getGridPane()
	{
		return gridPane;
	}

	private void mouseClickHandler(MouseEvent event)
	{
		String sjakkPos = LogikkKobling.pixelTilSjakkPos(event.getX(),event.getY());
		Rute r = getRute(sjakkPos);

		if (sistMerket == null) {
			if (r.erSjakkbrikke()) {
				r.merk();
				sistMerket = r;
				gyldigePos = sjakkBrett.getBrikke(r.getPos()).gyldigeTrekk();
				merkGyldige();
			}
		} else if (sistMerket.equals(r)) {
			r.merk();
			merkGyldige();
			gyldigePos = null;
			sistMerket = null;
		} else {
			System.out.println(sjakkPos);
			if (sjakkBrett.getBrikke(sistMerket.getPos()).flyttTil(sjakkPos)) {
				sistMerket.merk();
				merkGyldige();
				gyldigePos = null;
				sistMerket = null;
			} else {
				System.out.println("Ugyldig trekk!");
			}

		}

		oppdaterBrett();
	}
	private void merkGyldige()
	{
		if(gyldigePos!=null){
			for(String sjakkPos : gyldigePos){
				getRute(sjakkPos).merkGrønn();
			}
		}
	}
	private void oppdaterBrett()
	{
		for (int i = 0; i < Brett.BRETTSTØRRELSE; ++i) {
			for (int j = 0; j < Brett.BRETTSTØRRELSE; ++j) {
				ruter[i][j].oppdater(sjakkBrett.getBrikke(Koordinater.fra_koordinater(new int[]{i, j})));
			}
		}
	}

	private Rute getRute(String sjakkPos)
	{
		int[] koordinater = Koordinater.til_koordinater(sjakkPos);
		return ruter[koordinater[0]][koordinater[1]];
	}
}
