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
	private Brett sjakkBrett; // Sjakkbrett - spill logikk
	private GridPane gridPane; // GridPane - til å knytte opp mot hovedvinduet
	private Rute sistMerket; // Her lagres den siste merkede ruten - for interaksjon med musepeker!
	private Rute[][] ruter; // Her lagres GUI representasjonen av spillruter.
	private String[] gyldigePos; // Lagres for å kunne fjerne avmerkinger. Kunne eventuelt søkt gjennom rutene for å fjerne.

	/**
	 * Oppretter et nytt GUI-brett
	 * @param spillnr spillnr for sjakkbrettet
	 */
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

	/**
	 * Opprett ruter i brettet
	 */
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

	/**
	 * Returner GridPane for spillbrettet
	 * @return gridpane
	 */
	public GridPane getGridPane()
	{
		return gridPane;
	}

	/**
	 * Eventhandler for museklikk på spillbrettet! Gjelder kun i GridPane sitt område. Brukes for flytting av brikker
	 * @param event sendes fra MouseEvent
	 */
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


				oppdaterBrett(); // Brikke flyttet, oppdater brett!!
			} else {
				System.out.println("Ugyldig trekk!");
			}

		}

	}

	/**
	 * Merk alle mulige gyldige trekk.
	 */
	private void merkGyldige()
	{
		if(gyldigePos!=null){
			for(String sjakkPos : gyldigePos){
				getRute(sjakkPos).merkGrønn();
			}
		}
	}

	/**
	 * Oppdater brett etter endringer i spill-logikk (Klasse Brett)
	 * Må kjøres etter at brikker har blitt flyttet!
	 */
	private void oppdaterBrett()
	{
		for (int i = 0; i < Brett.BRETTSTØRRELSE; ++i) {
			for (int j = 0; j < Brett.BRETTSTØRRELSE; ++j) {
				ruter[i][j].oppdater(sjakkBrett.getBrikke(Koordinater.fra_koordinater(new int[]{i, j})));
			}
		}
	}

	/**
	 * Hent ut rute (GUI element) fra ruter tabellen
	 * @param sjakkPos sjakkPosisjon som ruten representerer
	 * @return ruten
	 */
	private Rute getRute(String sjakkPos)
	{
		int[] koordinater = Koordinater.til_koordinater(sjakkPos);
		return ruter[koordinater[0]][koordinater[1]];
	}
}
