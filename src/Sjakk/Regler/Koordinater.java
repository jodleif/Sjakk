package Sjakk.Regler;

/**
 * Created by Jo Øivind Gjernes on 20.10.2015.
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
		if (talldel < '1' || talldel > '8')
			return false;

		return true;
	}
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
}
