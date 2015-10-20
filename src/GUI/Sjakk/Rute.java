package GUI.Sjakk;

import GUI.HjelpeFunksjoner;
import Sjakk.Brikker.Brikke;
import Sjakk.Brikker.Farge;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


/**
 * Created by Jo Øivind Gjernes on 20.10.2015.
 */
public class Rute extends Pane
{
	private Rectangle bakgrunn;
	private ImageView brikke;
	public Rute() {
		setPrefSize(80,80);
		initBakgrunn();
	}

	public Rute(String brikketype, Farge farge)
	{
		this();
		setBrikke(brikketype, farge);
	}
	private void setBrikke(String brikketype, Farge farge) throws IllegalArgumentException
	{
		String bildeSti = Brikker.bildestiForBrikke(brikketype, farge);
		if(bildeSti!=null) {
			brikke = HjelpeFunksjoner.lastImageViewFraFil(bildeSti);
			getChildren().add(brikke);
		}
	}
	private void initBakgrunn()
	{
		bakgrunn = new Rectangle(80,80);
		bakgrunn.setFill(Color.RED);
		bakgrunn.setOpacity(0.0d);
		getChildren().add(bakgrunn);
	}
	private void handleMouseEntered(MouseEvent e)
	{
		if(e.isSecondaryButtonDown())
			bakgrunn.setOpacity(0.5d);
	}
	private void handleMouseOut()
	{
		bakgrunn.setOpacity(0.0d);
	}

}
