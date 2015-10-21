package GUI.Sjakk;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Jo Øivind Gjernes on 20.10.2015.
 *
 * Inneholder en statisk liste over alle bilder
 */
public class BildeListe
{
	private static HashMap<String,Image> sjakkBrikkeBilder;

	public BildeListe(){

	}

	private void byggBildeListe() {
		sjakkBrikkeBilder = new HashMap<>();
	}

	public void add(String navn, Image bilde)
	{

	}
}
