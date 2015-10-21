package Sjakk.Regler;

/**
 * Created by Jo Øivind Gjernes on 20.10.2015.
 * <p>
 * Klasse for operasjoner mellom "sjakk koordinater" og "array koordinater"
 */
public class Koordinater
{
	/**
	 * Sjekker om rutenavnet er lovlig i sjakk
	 * @param rutenavn et rutenavn. Format: bokstav tall eks: d1
	 * @return True hvis gyldig
	 */
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

	/**
	 * Regner ut et sjakk-koordinat fra et array-koordinat
	 * @param koordinater array-koordinat
	 * @return sjakk-koordinat
	 */
	public static String fra_koordinater(int[] koordinater)
	{
		if (koordinater.length != 2)
			return null; // Avbryt.

		String str = new String();
		str += (char) (koordinater[0] + 'a');
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
	 * @param fraPos sjakk-koordinat fra
	 * @param tilPos sjakk-koordinat til
	 * @return avstand (antall ruter)
	 */
	public static int avstand(String fraPos, String tilPos)
	{
		int[] koordFra = Koordinater.til_koordinater(fraPos);
		int[] koordTil = Koordinater.til_koordinater(tilPos);
		int[] diff = differanse(koordFra,koordTil);
		int antallRuter = Math.max(Math.abs(diff[0]),Math.abs(diff[1]));
		return antallRuter;
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
