package GUI.Sjakk;

import Sjakk.Brett.Brett;
import Sjakk.Brikker.Brikke;
import Sjakk.Regler.Koordinater;
import javafx.scene.layout.GridPane;

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

	public static String fraGridPane(int x, int y)
	{
		String str = new String();
		str += (char)(x+'a');
		str += (char)((7-y)+'1');
		return str;
	}

	public static int[] tilGridPane(String sjakkPos)
	{
		int[] koord = Koordinater.til_koordinater(sjakkPos);
		koord[1] = 7-koord[1];
		return koord;
	}

	public static String pixelTilSjakkPos(double x, double y)
	{
		int posX = (int)(x/celleStørrelse);
		int posY = (int)(y/celleStørrelse);
		return fraGridPane(posX,posY);
	}

	public static int[] pixelTilArrPos(double x, double y)
	{
		int posX = (int)(x/celleStørrelse);
		int posY = (int)(y/celleStørrelse);
		return new int[] {posX,posY};
	}

}
