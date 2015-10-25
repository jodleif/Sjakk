package Sjakk.Regler;

import java.util.Arrays;
import java.util.HashMap;

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
	public static HashMap<Integer, int[]> stringTilKoord = new HashMap<>();
	public static HashMap<Integer, String> koordTilString = new HashMap<>();

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
		int[] tmp = stringTilKoord.get(rutenavn.hashCode());
		if (tmp != null) return tmp;
		if (erLovligRutenavn(rutenavn)) {
			int[] koordinater = new int[2];
			koordinater[0] = rutenavn.charAt(0) - 'a';
			koordinater[1] = rutenavn.charAt(1) - '1';
			//stringTilKoord.put(rutenavn.hashCode(), Arrays.copyOf(koordinater, 2));
			return koordinater;
		}
		return null;
	}

	public static void preBuild()
	{
		for (int i = 0; i < 8; ++i) {
			for (int j = 0; j < 8; j++) {
				fraKoordinater(new int[]{i, j});
				tilKoordinater(String.valueOf((char) (i + 'a')) + String.valueOf((char) (j + '1')));
			}
		}
	}

	private static void tilKoordinater(String rutenavn)
	{
		if (erLovligRutenavn(rutenavn)) {
			int[] koordinater = new int[2];
			koordinater[0] = rutenavn.charAt(0) - 'a';
			koordinater[1] = rutenavn.charAt(1) - '1';
			stringTilKoord.put(rutenavn.hashCode(), koordinater);
		}
	}

	private static void fraKoordinater(int[] koordinater)
	{
		String str = new String();
		str += (char) (koordinater[0] + 'a');
		str += (char) (koordinater[1] + '1');
		if (erLovligRutenavn(str)) { // Sjekk om lovlig rutenavn.
			koordTilString.put(Arrays.hashCode(koordinater), str);
		}
	}

	/**
	 * Regner ut et sjakk-koordinat fra et array-koordinat
	 * @param koordinater array-koordinat
	 * @return sjakk-koordinat
	 */
	public static String fra_koordinater(int[] koordinater)
	{
		String tmp = koordTilString.get(Arrays.hashCode(koordinater));
		if (tmp != null) return tmp;
		if (koordinater.length != 2)
			return null; // Avbryt.

		String str = new String();
		str += (char) (koordinater[0] + 'a');
		str += (char) (koordinater[1] + '1');
		if (erLovligRutenavn(str)) { // Sjekk om lovlig rutenavn.
			//koordTilString.put(Arrays.hashCode(koordinater), str);
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

	public static Retning radRetning(String fraPos, String tilPos, Farge f)
	{
		int[] koordFra = til_koordinater(fraPos);
		int[] koordTil = til_koordinater(tilPos);
		int[] diff = differanse(koordFra, koordTil);
		if (f == Farge.HVIT) {
			return (diff[1] > 0) ? Retning.FREM : Retning.BAKOVER;
		}
		return (diff[1] < 0) ? Retning.FREM : Retning.BAKOVER;
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

	public static void resetTables()
	{
		stringTilKoord = new HashMap<>();
		koordTilString = new HashMap<>();
	}
}
