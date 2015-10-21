package GUI.Sjakk;

import GUI.HjelpeFunksjoner;
import Sjakk.Brikker.Brikke;
import Sjakk.Regler.Farge;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


/**
 * Created by Jo �ivind Gjernes on 20.10.2015.
 */
public class Rute extends Pane
{
	private Rectangle bakgrunn;
	private ImageView brikkeBilde;
	private String rutePos;
	private boolean merket = false;
	private boolean sjakkBrikke;

	public Rute(String pos)
	{
		setPrefSize(80, 80);
		initBakgrunn();
		rutePos = pos;
	}

	private void setBrikke(String brikketype, Farge farge) throws IllegalArgumentException
	{
		String bildeSti = Brikker.bildestiForBrikke(brikketype, farge);
		if (bildeSti != null) {
			brikkeBilde = HjelpeFunksjoner.lastImageViewFraFil(bildeSti);
			getChildren().add(brikkeBilde);
		}
	}

	public void fjernBilde()
	{
		getChildren().remove(brikkeBilde);
		brikkeBilde = null;
	}

	private void initBakgrunn()
	{
		bakgrunn = new Rectangle(80, 80);
		bakgrunn.setFill(Color.RED);
		bakgrunn.setOpacity(0.0d);
		getChildren().add(bakgrunn);
	}

	public void oppdater(Brikke brikke)
	{
		if (brikkeBilde != null) {
			fjernBilde();
		}

		if (brikke != null) {
			sjakkBrikke = true;
			setBrikke(brikke.brikkenavn(), brikke.getFarge());
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
	public String getPos() {return rutePos;}
	public boolean erSjakkbrikke() {return sjakkBrikke;}


}
