package Sjakk;

/**
 * Created by Jo Øivind Gjernes on 20.10.2015.
 */

import Sjakk.Brikker.*;

/***
 *
 */
public class Brett
{

	/***
	 * Startsposisjoner for tårn.
	 */
	private static final String[] TÅRNPOSISJONER = {new String("a1"), new String("a8"), new String("h1"), new String("h8")};
	private static final String[] LØPERPOSISJONER = {new String("c1"), new String("f1"), new String("c8"), new String("f8")};
	private static final String[] SPRINGERPOSISJONER = {new String("b1"), new String("g1"), new String("b8"), new String("g8")};
	private static final String[] DRONNINGPOSISJONER = {new String("e1"), new String("d8")};
	private static final String[] KONGEPOSISJONER = {new String("d1"), new String("e8")};
	private static final int BRETTSTØRRELSE = 8;
	private int spillNr;
	private Brikke[][] brikkene;

	public Brett(int nyttSpillNr)
	{
		this.spillNr = nyttSpillNr;
		brikkene = new Brikke[BRETTSTØRRELSE][BRETTSTØRRELSE];
		try {
			opprettSpillbrikker();
		} catch (IllegalArgumentException e) {
			System.out.println("[BRETT] Feil under konstruksjon!\n" + e.getMessage());
		}
	}

	private int[] til_koordinater(String rutenavn)
	{
		if (erLovligRutenavn(rutenavn)) {
			int[] koordinater = new int[2];
			koordinater[0] = rutenavn.charAt(0) - 'a';
			koordinater[1] = rutenavn.charAt(1) - '1';
			return koordinater;
		}
		return null;
	}

	private String fra_koordinater(int[] koordinater)
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

	private void opprettSpillbrikker() throws IllegalArgumentException
	{

		for (int j = 1; j < BRETTSTØRRELSE; j += (BRETTSTØRRELSE - 3)) { // Andre rad og sjette rad.
			for (int i = 0; i < BRETTSTØRRELSE; ++i) {
				Farge farge = (j == 1) ? Farge.HVIT : Farge.SVART;
				String nyRuteNavn = fra_koordinater(new int[]{i, j});
				if (nyRuteNavn != null) {
					brikkene[i][j] = new Bonde(this, nyRuteNavn, farge);
				}
			}
		}
		for (String tårnPos : TÅRNPOSISJONER) {
			int[] koord = til_koordinater(tårnPos);
			Farge farge = (koord[1] <= 1) ? Farge.HVIT : Farge.SVART; // Hvis rad 1 eller 2 (koordinat 0, 1) Hvite brikker.
			brikkene[koord[0]][koord[1]] = new Tårn(this, tårnPos, farge);
		}
		for (String springerPos : SPRINGERPOSISJONER) {
			int[] koord = til_koordinater(springerPos);
			Farge farge = (koord[1] <= 1) ? Farge.HVIT : Farge.SVART;
			brikkene[koord[0]][koord[1]] = new Springer(this, springerPos, farge);
		}
		for (String løperPos : LØPERPOSISJONER) {
			int[] koord = til_koordinater(løperPos);
			Farge farge = (koord[1] <= 1) ? Farge.HVIT : Farge.SVART;
			brikkene[koord[0]][koord[1]] = new Løper(this, løperPos, farge);
		}
		for (String kongePos : KONGEPOSISJONER) {
			int[] koord = til_koordinater(kongePos);
			Farge farge = (koord[1] <= 1) ? Farge.HVIT : Farge.SVART;
			brikkene[koord[0]][koord[1]] = new Konge(this, kongePos, farge);
		}
		for (String dronningPos : DRONNINGPOSISJONER) {
			int[] koord = til_koordinater(dronningPos);
			Farge farge = (koord[1] <= 1) ? Farge.HVIT : Farge.SVART;
			brikkene[koord[0]][koord[1]] = new Dronning(this, dronningPos, farge);
		}

	}


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

	public Brikke getBrikke(String rutenavn)
	{
		int[] koordinater = til_koordinater(rutenavn);
		if (koordinater == null) {
			return null;
		} else {
			return brikkene[koordinater[0]][koordinater[1]];
		}

	}

	public boolean flyttBrikke(String fraRute, String tilRute)
	{
		if (!(erLovligRutenavn(fraRute) && erLovligRutenavn(tilRute))) {
			return false;
		}
		// Sjekk at det står en brikke der vi flytter fra
		if (getBrikke(fraRute) == null)
			return false;
		// TODO: Eventuell spillogikk som sjekker dit den flytter - Må være motsatt farge
		int[] koordFra = til_koordinater(fraRute);
		int[] koordTil = til_koordinater(tilRute);
		brikkene[koordTil[0]][koordTil[1]] = brikkene[koordFra[0]][koordFra[1]];
		brikkene[koordFra[0]][koordFra[1]] = null; // Fjern brikken fra gamle posisjon.
		return true;
	}
}
