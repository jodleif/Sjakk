package GUI.Sjakk;

import Sjakk.Regler.Koordinater;

/**
 * Created by Jo Øivind Gjernes on 20.10.2015.
 *
 * Klasse som håndterer koblingen mellom spillogikk og GUI
 *
 * I praksis utfører denne klassen "omregninger" mellom koordinatsystemene til GridPane og Sjakkbrettet.
 */
public class LogikkKobling
{

	private static final double celleStørrelse = 80; // Spillets cellestørrelse - 80 piksler

	/**
	 * Regner fra gridpane koordinater til sjakk-koordinater
	 * @param x x-pos i gridpane
	 * @param y y-pos i gridpane
	 * @return sjakk-koordinat
	 */
	public static String fraGridPane(int x, int y)
	{
		String str = new String();
		str += (char)(x+'a');
		str += (char)((7-y)+'1');
		return str;
	}

	/**
	 * Fra sjakkposisjon til gridpane-posisjon
	 * @param sjakkPos sjakk-koordinat
	 * @return gridpane-koordinat {x, y}
	 */
	public static int[] tilGridPane(String sjakkPos)
	{
		int[] koord = Koordinater.til_koordinater(sjakkPos);
		int[] copyCoord = new int[]{koord[0], 7 - koord[1]};
		return copyCoord;
	}

	/**
	 * Omregner pixel-posisjon (fra mousevent på gridpanen)
	 * @param x musens x-koordinat (0,0 i øvre venstre hjørne på spillbrettet)
	 * @param y musens y-koordinat
	 * @return sjakk-koordinat
	 */
	public static String pixelTilSjakkPos(double x, double y)
	{
		int posX = (int)(x/celleStørrelse);
		int posY = (int)(y/celleStørrelse);
		return fraGridPane(posX,posY);
	}

}
