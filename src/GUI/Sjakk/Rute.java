package GUI.Sjakk;

import Sjakk.Regler.Farge;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


/**
 * Created by Jo Øivind Gjernes on 20.10.2015.
 *
 * Grafisk representasjon av en "logisk" rute på sjakkbrettet
 */
public class Rute extends Pane
{
	private Rectangle bakgrunn;
	private ImageView brikkeBilde;
	private BildeListe bildeCache; // Referanse til bildeCache.
	private String rutePos;
	private boolean merket = false;
	private boolean sjakkBrikke;

	public Rute(String pos, BildeListe cache)
	{
		setPrefSize(80, 80);
		initBakgrunn();
		bildeCache = cache;
		rutePos = pos;
	}

	private void setBilde(String brikketype, Farge farge) throws IllegalArgumentException
	{
		Image temp = bildeCache.getBilde(brikketype, farge); // Slår opp bilde.
		brikkeBilde = new ImageView(temp);
		if (brikkeBilde == null)
			throw new IllegalArgumentException("[setBilde] Fant ikke ImageView");
		else
			getChildren().add(brikkeBilde);
	}

	public void fjernBilde()
	{
		getChildren().remove(brikkeBilde);
		brikkeBilde = null;
		sjakkBrikke = false;
	}

	private void initBakgrunn()
	{
		bakgrunn = new Rectangle(80, 80);
		bakgrunn.setFill(Color.RED);
		bakgrunn.setOpacity(0.0d);
		getChildren().add(bakgrunn);
	}

	public void oppdater(String brikkeNavn, Farge farge)
	{
		if (brikkeBilde != null) {
			fjernBilde();
		}

		if (brikkeNavn != null) {
			sjakkBrikke = true;
			setBilde(brikkeNavn, farge);
		} else {
			sjakkBrikke = false;
		}
	}

	/**
	 * Merk av rute (spillervalgt)
	 */
	public void merk()
	{
		if(sjakkBrikke) {
			if (merket) {
				bakgrunn.setOpacity(0.0d);
				merket = false;
			} else {
				merket = true;
				bakgrunn.setOpacity(0.5d);
			}
		}
	}

	/**
	 * Merk av rute (grønn farge - gyldige trekk)
	 */
	public void merkGrønn()
	{
		if(merket){
			bakgrunn.setOpacity(0.0d);
			bakgrunn.setFill(Color.RED);
			merket = false;
		} else {
			bakgrunn.setFill(Color.GREEN);
			bakgrunn.setOpacity(0.5d);
			merket = true;
		}
	}

	/**
	 * Rutens posisjon på sjakkbrettet
	 * @return sjakk-koordinat for ruten.
	 */
	public String getPos() {return rutePos;
	}

	/**
	 * @return True hvis det er en sjakkbrikke i ruten.
	 */
	public boolean erSjakkbrikke() {return sjakkBrikke;}


}
