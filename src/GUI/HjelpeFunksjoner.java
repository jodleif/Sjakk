package GUI;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by Jo Øivind Gjernes on 20.10.2015.
 * <p>
 * Hjelpefunksjoner til GUI.
 */
public class HjelpeFunksjoner
{
	/**
	 * For å lage en imageview fra en bildefil på harddisken!
	 * @param filbane relativ bane til bildefil
	 * @return ImageView av bildefilen hvis den finnes, ellers null
	 */
	public static ImageView lastImageViewFraFil(String filbane)
	{

		ImageView bildenode = new ImageView();
		Image bilde = lastImageFraFil(filbane);
		if (bilde != null) {
			bildenode.setImage(bilde);
			return bildenode;
		}
		System.err.println("ERROR: Kunne ikke laste bildet.");
		return null;
	}

	public static Image lastImageFraFil(String filbane)
	{
		Image bilde;
		try {
			bilde = new Image(filbane);
		} catch (Exception e) {
			System.out.println("[lastImageViewFraFil] ERROR: " + e.getMessage() + "\nFIL: " + filbane);
			return null;
		}
		return bilde;
	}


}
