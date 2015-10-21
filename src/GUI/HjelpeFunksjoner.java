package GUI;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by Jo Øivind Gjernes on 20.10.2015.
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


}
