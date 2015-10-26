package GUI.Sjakk;

import Sjakk.AI.MiniMax;
import Sjakk.Brett.Brett;
import Sjakk.Brikker.Brikke;
import Sjakk.Regler.Farge;
import Sjakk.Regler.Koordinater;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by Jo Øivind Gjernes on 20.10.2015.
 * <p>
 * Spillebrettets representasjon i GUI
 */
public class SpilleBrett
{
	private Brett sjakkBrett; // Sjakkbrett - spill logikk
	private GridPane gridPane; // GridPane - til å knytte opp mot hovedvinduet
	private Rute sistMerket; // Her lagres den siste merkede ruten - for interaksjon med musepeker!
	private Rute[][] ruter; // Her lagres GUI representasjonen av spillruter.
	private LinkedHashMap<Integer, ArrayList<Integer>> gyldigePos; // Lagres for å kunne fjerne avmerkinger. Kunne eventuelt søkt gjennom rutene for å fjerne.
	private BildeListe bildeCache; // Lagrer imageviews for alle brikker.
	private MiniMax aiSpiller;
	private Farge spillerFarge;
	private Timeline aiSpillerGjørTrekk;
	private Label statusFelt;
	private boolean lock = false;

	/**
	 * Oppretter et nytt GUI-brett
	 *
	 * @param spillnr spillnr for sjakkbrettet
	 */
	public SpilleBrett(int spillnr, Label statusFelt)
	{
		sjakkBrett = new Brett(spillnr);
		bildeCache = new BildeListe();
		gridPane = new GridPane();
		this.statusFelt = statusFelt;
		gridPane.setPrefSize(620, 620);
		gridPane.setOnMouseClicked(e -> mouseClickHandler(e));
		ruter = new Rute[Brett.BRETTSTØRRELSE][Brett.BRETTSTØRRELSE];
		spillerFarge = Farge.HVIT;
		aiSpiller = new MiniMax(5, spillerFarge.motsatt());
		initBrett();
		oppdaterBrett();
		startAiSpiller();
		oppdaterStatusfelt();
	}

	private void startAiSpiller()
	{
		aiSpillerGjørTrekk = new Timeline(
			new KeyFrame(Duration.millis(250), new EventHandler<ActionEvent>()
			{
				@Override
				public void handle(ActionEvent event)
				{
					aiSpillerSinTur();
				}

			})
		);
		aiSpillerGjørTrekk.setCycleCount(Timeline.INDEFINITE);
		aiSpillerGjørTrekk.play();
	}

	/**
	 * Opprett ruter i brettet. Kjøres når spillbrettet opprettes (og skal kun kjøres da)
	 */
	public void initBrett()
	{
		for (int i = 0; i < Brett.BRETTSTØRRELSE; ++i) {
			for (int j = 0; j < Brett.BRETTSTØRRELSE; ++j) {
				Rute tempRute = new Rute(Koordinater.fra_koordinater(new int[]{i, j}), bildeCache);
				ruter[i][j] = (tempRute); // Lagre en referanse til ruten.
				gridPane.add(tempRute, i, 7 - j); // OBS: Må tegne "opp ned" siden gridpane starter med (0,0) i øvre venstre hjørne, ikke nedre som sjakk
			}
		}

	}

	public void aiSpillerSinTur()
	{
		if (sjakkBrett.getSpillerSinTur() == spillerFarge.motsatt() && !lock) {
			lock = true;
			aiSpiller.nesteAiTrekk(sjakkBrett);
			oppdaterBrett();
			oppdaterStatusfelt();
			lock = false;
		}
	}

	/**
	 * Returner GridPane for spillbrettet
	 *
	 * @return gridpane
	 */
	public GridPane getGridPane()
	{
		return gridPane;
	}

	/**
	 * Eventhandler for museklikk på spillbrettet! Gjelder kun i GridPane sitt område. Brukes for flytting av brikker
	 *
	 * @param event sendes fra MouseEvent
	 */
	private void mouseClickHandler(MouseEvent event)
	{
		String sjakkPos = LogikkKobling.pixelTilSjakkPos(event.getX(), event.getY());
		Rute r = getRute(sjakkPos);

		if (sistMerket == null) {
			if (r.erSjakkbrikke()) {
				Brikke b = sjakkBrett.getBrikke(sjakkPos);
				if (b != null && b.getFarge() == sjakkBrett.getSpillerSinTur()) {
					r.merk();
					sistMerket = r;
					merkGyldige();
				} else if (b == null) {
					System.err.println("Ikke gyldig brikke");
				} else {
					System.err.println("Andre spiller sin tur!");
				}
			}
		} else if (sistMerket.equals(r)) {
			r.merk();
			merkGyldige();
			sistMerket = null;
		} else {
			System.out.println(sjakkPos);
			if (sjakkBrett.flyttBrikke(sistMerket.getPos(), sjakkPos)) {
				sistMerket.merk();
				merkGyldige();
				sistMerket = null;
				oppdaterBrett(); // Brikke flyttet, oppdater brett!!
			} else {
				System.out.println("Ugyldig trekk!");
			}

		}

		oppdaterStatusfelt();
	}

	/**
	 * Merk alle mulige gyldige trekk.
	 */
	private void merkGyldige()
	{
		if (gyldigePos != null && gyldigePos.get(Koordinater.tilRuteid(sistMerket.getPos())) != null) {
			for (Integer sjakkPos : gyldigePos.get(Koordinater.tilRuteid(sistMerket.getPos()))) {
				getRute(Koordinater.fraRuteid(sjakkPos)).merkGrønn();
			}
		}

	}

	/**
	 * Oppdater brett etter endringer i spill-logikk (Klasse Brett)
	 * Må kjøres etter at brikker har blitt flyttet!
	 */
	private void oppdaterBrett()
	{
		gyldigePos = sjakkBrett.getAlleGyldigeTrekk(sjakkBrett.getSpillerSinTur());
		if (gyldigePos == null || gyldigePos.size() == 0)
			System.err.println("Sjakk matt! Vinner: " + sjakkBrett.getSpillerSinTur().motsatt());
		for (int i = 0; i < Brett.BRETTSTØRRELSE; ++i) {
			for (int j = 0; j < Brett.BRETTSTØRRELSE; ++j) {
				Brikke tmp = sjakkBrett.getBrikke(String.valueOf((char) (i + 'a')) + String.valueOf((char) (j + '1')));
				if (tmp != null)
					ruter[i][j].oppdater(tmp.brikkenavn(), tmp.getFarge());
				else {
					ruter[i][j].fjernBilde();
				}
			}
		}
	}

	private void oppdaterStatusfelt()
	{
		if (sjakkBrett.getSpillerSinTur() == spillerFarge) {
			statusFelt.setText("Din tur, poeng:" + sjakkBrett.getPoeng(spillerFarge));
		} else {
			statusFelt.setText("AI tenker..." + sjakkBrett.getPoeng(spillerFarge.motsatt()));
		}
	}

	/**
	 * Hent ut rute (GUI element) fra ruter tabellen
	 *
	 * @param sjakkPos sjakkPosisjon som ruten representerer
	 * @return ruten
	 */
	private Rute getRute(String sjakkPos)
	{
		int[] koordinater = Koordinater.til_koordinater(sjakkPos);
		return ruter[koordinater[0]][koordinater[1]];
	}

	/**
	 * Angre funksjon.
	 * merk: Sørger for å fjerne eventuelle merkede felter for å unngå bugs.
	 *
	 * @return true hvis angringen gikk bra.
	 */
	public boolean angre()
	{
		lock = true;
		if (sjakkBrett.angre()) {
			if (sjakkBrett.angre()) {
				if (sistMerket != null) {
					merkGyldige();
					sistMerket.merk();
					sistMerket = null;
				}
				oppdaterBrett();
				lock = false;
				return true;
			}
		}
		lock = false;
		return false;
	}

}
