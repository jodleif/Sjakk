package GUI.Sjakk;

import GUI.HjelpeFunksjoner;
import Sjakk.Regler.Farge;
import javafx.scene.image.Image;

import java.util.HashMap;

/**
 * Created by Jo Øivind Gjernes on 20.10.2015.
 *
 * Inneholder en liste over alle ImageViews (bilder av alle spillbrikker)
 */
public class BildeListe
{
	private HashMap<String, Image> sjakkBrikkeBilder;

	public BildeListe(){
		sjakkBrikkeBilder = new HashMap<>();
	}

	public void leggTil(String brikkeType, Farge farge, String unikId)
	{
		Image element = HjelpeFunksjoner.lastBildeFraFil(BrikkeBilder.bildestiForBrikke(brikkeType, farge));
		sjakkBrikkeBilder.put(unikId, element);
	}

	public Image getBilde(String brikkeType, Farge farge)
	{
		String unikId = unikBildeIdentifikasjon(brikkeType, farge);
		Image bilde = sjakkBrikkeBilder.get(unikId);
		if (bilde == null) {
			leggTil(brikkeType, farge, unikId);
			return getBilde(brikkeType, farge);
		}
		return bilde;
	}

	private String unikBildeIdentifikasjon(String brikkeType, Farge farge)
	{
		String unikId = brikkeType + String.valueOf(BrikkeBilder.fargeTilTall(farge));
		return unikId;
	}
}
