package GUI;


import Sjakk.Brikker.Farge;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by Jo Øivind Gjernes on 20.10.2015.
 */
public class HjelpeFunksjoner
{
	public static ImageView lastImageViewFraFil(String filbane)
	{
		Image bilde;
		try {
			bilde = new Image(filbane);
		} catch (Exception e){
			System.out.println("[lastImageViewFraFil] ERROR: "  + e.getMessage() + "\nFIL: " + filbane);
			return null;
		}
		ImageView bildenode = new ImageView();
		bildenode.setImage(bilde);
		return bildenode;
	}

	/**
	 * Returnerer et tall fra farge
	 * @param farge farge
	 * @return 0 for hvit, 1 for svart
	 */
	public static int fargeTilTall(Farge farge)
	{
		return (farge==Farge.HVIT) ? 0 : 1;
	}
}
