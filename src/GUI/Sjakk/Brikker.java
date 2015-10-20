package GUI.Sjakk;

import GUI.HjelpeFunksjoner;
import Sjakk.Brikker.Farge;

/**
 * Created by Jo Øivind Gjernes on 20.10.2015.
 */
public class Brikker
{
	private static final String[] BØNDER = {"img/bondehvit.png","img/bondesvart.png"};
	private static final String[] SPRINGERE = {"img/springerhvit.png","img/springersvart.png"};
	private static final String[] KONGER = {"img/kongehvit.png", "img/kongesvart.png"};
	private static final String[] DRONNINGER = {"img/dronninghvit.png", "img/dronningsvart.png"};
	private static final String[] TÅRN = {"img/tårnhvit.png","img/tårnsvart.png"};
	private static final String[] LØPERE = {"img/løperhvit.png","img/løpersvart.png"};

	public static String bildestiForBrikke(String brikketype, Farge farge) throws IllegalArgumentException
	{
		if(brikketype.length()!=1) return null;
		switch(brikketype.charAt(0)){
			case 'B':
				return BØNDER[HjelpeFunksjoner.fargeTilTall(farge)];
			case 'S':
				return SPRINGERE[HjelpeFunksjoner.fargeTilTall(farge)];
			case 'K':
				return KONGER[HjelpeFunksjoner.fargeTilTall(farge)];
			case 'D':
				return DRONNINGER[HjelpeFunksjoner.fargeTilTall(farge)];
			case 'T':
				return TÅRN[HjelpeFunksjoner.fargeTilTall(farge)];
			case 'L':
				return LØPERE[HjelpeFunksjoner.fargeTilTall(farge)];
			default:
				throw new IllegalArgumentException("[bildestiForBrikke] Ugyldig brikke!");
		}
	}
}
