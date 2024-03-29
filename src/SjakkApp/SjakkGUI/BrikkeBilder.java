package SjakkApp.SjakkGUI;

import SjakkApp.SjakkLogikk.Regler.Farge;

/**
 * Created by Jo Øivind Gjernes on 20.10.2015.
 *
 * Klasse som inneholder informasjon om bildefiler til sjakkbrikker.
 */
public class BrikkeBilder
{
	private static final String[] BØNDER = {"img/bondehvit.png","img/bondesvart.png"};
	private static final String[] SPRINGERE = {"img/springerhvit.png","img/springersvart.png"};
	private static final String[] KONGER = {"img/kongehvit.png", "img/kongesvart.png"};
	private static final String[] DRONNINGER = {"img/dronninghvit.png", "img/dronningsvart.png"};
	private static final String[] TÅRN = {"img/tårnhvit.png","img/tårnsvart.png"};
	private static final String[] LØPERE = {"img/løperhvit.png","img/løpersvart.png"};

	/**
	 * Henter bildestien til bilde for alle typer brikker.
	 *
	 * @param brikketype type brikke f.eks K for konge
	 * @param farge      farge på brikken f.eks (Farge.HVIT)
	 * @return fil-sti til bildefilen
	 * @throws IllegalArgumentException
	 */
	public static String bildestiForBrikke(String brikketype, Farge farge) throws IllegalArgumentException
	{
		if(brikketype.length()!=1) return null;
		switch(brikketype.charAt(0)){
			case 'B':
				return BØNDER[fargeTilTall(farge)];
			case 'S':
				return SPRINGERE[fargeTilTall(farge)];
			case 'K':
				return KONGER[fargeTilTall(farge)];
			case 'D':
				return DRONNINGER[fargeTilTall(farge)];
			case 'T':
				return TÅRN[fargeTilTall(farge)];
			case 'L':
				return LØPERE[fargeTilTall(farge)];
			default:
				throw new IllegalArgumentException("[bildestiForBrikke] Ugyldig brikke!");
		}
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
