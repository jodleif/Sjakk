package SjakkApp.SjakkGUI;

import SjakkApp.HjelpeFunksjoner;
import SjakkApp.SjakkLogikk.Regler.Farge;
import javafx.scene.image.Image;

import java.util.HashMap;

/**
 * Created by Jo Øivind Gjernes on 20.10.2015.
 *
 * Inneholder en liste over alle ImageViews (bilder av alle spillbrikker)
 */
public class BildeListe
{
	/**
	 * Variabel som lagrer en liste over "Images" av sjakkbrikkene
	 */
	private HashMap<String, Image> sjakkBrikkeBilder;

	public BildeListe(){
		sjakkBrikkeBilder = new HashMap<>();
	}

	/**
	 * Legg til en brikke med bilde i listen
	 * @param brikkeType type brikke f.eks B (for bonde)
	 * @param farge farge på spillbrikken
	 * @param unikId en unikId
	 */
	public void leggTil(String brikkeType, Farge farge, String unikId)
	{
		Image element = HjelpeFunksjoner.lastBildeFraFil(BrikkeBilder.bildestiForBrikke(brikkeType, farge));
		sjakkBrikkeBilder.put(unikId, element);
	}

	/**
	 * Henter en Image for en brikke fra sjakkBrikkeBilder
	 * @param brikkeType type brikke
	 * @param farge farge på brikken
	 * @return Image for javafx av brikken
	 */
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

	/**
	 * Kombiner brikkenavn og farge for å lage en "unik" verdi for brikkenTypen (f.eks hvit bonde)
	 * @param brikkeType type brikke
	 * @param farge farge på brikken
	 * @return id for brikken
	 */
	private String unikBildeIdentifikasjon(String brikkeType, Farge farge)
	{
		return brikkeType + String.valueOf(BrikkeBilder.fargeTilTall(farge));
	}
}
