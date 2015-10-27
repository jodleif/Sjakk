package Sjakk.Regler;

import Sjakk.Brett.Brett;

/**
 * Created by Jo Øivind Gjernes on 20.10.2015.
 * <p>
 * Klasse for operasjoner mellom "sjakk koordinater" og "array koordinater"
 */
public class Koordinater
{


	public static boolean erLovligRutenavn(String rutenavn)
	{
		if (rutenavn.length() != 2)
			return false;
		char bokstavDel = rutenavn.charAt(0);
		char talldel = rutenavn.charAt(1);

		if (bokstavDel < 'a' || bokstavDel > 'h')
			return false;
		return !(talldel < '1' || talldel > '8');

	}

	public static int fraXY(int x, int y)
	{
		return (y * Brett.BRETTSTØRRELSE + x);
	}
	/**
	 * Gjør om et sjakk-koordinat til array koordinat
	 * @param rutenavn sjakk-koordinat
	 * @return array-koordinat
	 */
	public static int[] til_koordinater(String rutenavn)
	{
		if (erLovligRutenavn(rutenavn)) {
			int[] koordinater = new int[2];
			koordinater[0] = rutenavn.charAt(0) - 'a';
			koordinater[1] = rutenavn.charAt(1) - '1';
			return koordinater;
		}
		return null;
	}

	public static int tilRuteid(String rutenavn)
	{
		// x + y * Bredde = koord
		return (rutenavn.charAt(0) - 'a') + (rutenavn.charAt(1) - '1') * Brett.BRETTSTØRRELSE;
	}

	public static String fraRuteid(int ruteid)
	{
		String s = "" + (char) (ruteid % Brett.BRETTSTØRRELSE + 'a');
		s += (char) (ruteid / Brett.BRETTSTØRRELSE + '1');
		return s;
	}



	/**
	 * Regner ut et sjakk-koordinat fra et array-koordinat
	 * @param koordinater array-koordinat
	 * @return sjakk-koordinat
	 */
	public static String fra_koordinater(int[] koordinater)
	{
		if (koordinater.length != 2)
			return null; // Avbryt.

		String str = "" + (char) (koordinater[0] + 'a');
		str += (char) (koordinater[1] + '1');
		if (erLovligRutenavn(str)) { // Sjekk om lovlig rutenavn.
			return str;
		}

		return null;
	}

	/**
	 * Regner ut avstand mellom to ruter på sjakkbrettet.
	 * MERK: Diagonal avstand i sjakk blir bare antall ruter for det ene "benet"
	 * OBS! Fungerer ikke for "Spesielle bevegelser", i.e. for hesten
	 * @param diffx sjakk-koordinat fra
	 * @param diffy sjakk-koordinat til
	 * @return avstand (antall ruter)
	 */
	public static int avstand(int diffx, int diffy)
	{
		int antallRuter = Math.max(Math.abs(diffx), Math.abs(diffy));
		return antallRuter;
	}

	/**
	 * Sjekker hvilken retning en brikke beveger seg langs en rad
	 *
	 * @param diffy         endring i y koordinat
	 * @param brikkensFarge brikken som flyttes sin farge
	 * @return retningen brikken flyttes.
	 */
	public static Retning radRetning(int diffy, Farge brikkensFarge)
	{
		if (brikkensFarge == Farge.HVIT) {
			return (diffy > 0) ? Retning.FREM : Retning.BAKOVER;
		}
		return (diffy < 0) ? Retning.FREM : Retning.BAKOVER;
	}

	/**
	 * Gir en differanse mellom to array-koordinater
	 * @param fra array-koordinat fra rute
	 * @param til array-koordinat til rute
	 * @return gir differanse i "x" og "y" retning (relativ endring i posisjon)
	 */
	public static int[] differanse(int[] fra, int[] til)
	{
		if (fra.length != til.length)
			return null;
		return new int[]{til[0] - fra[0], til[1] - fra[1]};
	}

}
