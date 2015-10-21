package Sjakk.Brett;

/**
 * Created by Jo Øivind Gjernes on 20.10.2015.
 */

import Sjakk.Brikker.*;
import Sjakk.Regler.Koordinater;
import Sjakk.Regler.PosisjonRegler;

/***
 *
 */
public class Brett
{

	/***
	 * Startsposisjoner for brikker..
	 */
	public static final int BRETTSTØRRELSE = 8; // Vil ha tilgang til denne fra GUI
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
		return Koordinater.til_koordinater(rutenavn);
	}

	private String fra_koordinater(int[] koordinater)
	{
		return Koordinater.fra_koordinater(koordinater);
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
		for (String tårnPos : PosisjonRegler.TÅRNPOSISJONER) {
			int[] koord = til_koordinater(tårnPos);
			Farge farge = (koord[1] <= 1) ? Farge.HVIT : Farge.SVART; // Hvis rad 1 eller 2 (koordinat 0, 1) Hvite brikker.
			brikkene[koord[0]][koord[1]] = new Tårn(this, tårnPos, farge);
		}
		for (String springerPos : PosisjonRegler.SPRINGERPOSISJONER) {
			int[] koord = til_koordinater(springerPos);
			Farge farge = (koord[1] <= 1) ? Farge.HVIT : Farge.SVART;
			brikkene[koord[0]][koord[1]] = new Springer(this, springerPos, farge);
		}
		for (String løperPos : PosisjonRegler.LØPERPOSISJONER) {
			int[] koord = til_koordinater(løperPos);
			Farge farge = (koord[1] <= 1) ? Farge.HVIT : Farge.SVART;
			brikkene[koord[0]][koord[1]] = new Løper(this, løperPos, farge);
		}
		for (String kongePos : PosisjonRegler.KONGEPOSISJONER) {
			int[] koord = til_koordinater(kongePos);
			Farge farge = (koord[1] <= 1) ? Farge.HVIT : Farge.SVART;
			brikkene[koord[0]][koord[1]] = new Konge(this, kongePos, farge);
		}
		for (String dronningPos : PosisjonRegler.DRONNINGPOSISJONER) {
			int[] koord = til_koordinater(dronningPos);
			Farge farge = (koord[1] <= 1) ? Farge.HVIT : Farge.SVART;
			brikkene[koord[0]][koord[1]] = new Dronning(this, dronningPos, farge);
		}

	}


	public static boolean erLovligRutenavn(String rutenavn)
	{
		return Koordinater.erLovligRutenavn(rutenavn);
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

	/**
	 * Metoden flytter en brikke. Sjekker om felter er gyldige, men ikke om et eventuelt sjakktrekk er gyldig
	 * @param fraRute Ruten man flytter fra
	 * @param tilRute Ruten man flytter til
	 * @return Sann hvis vi flyttet brikken
	 */
        public boolean flyttBrikke(String fraRute, String tilRute)
	{
		if (!(erLovligRutenavn(fraRute) && erLovligRutenavn(tilRute))) {
			return false;
		}
		// Sjekk at det står en brikke der vi flytter fra
		Brikke br = getBrikke(fraRute);
		if (br == null)
			return false;
		if(!br.erLovligTrekk(tilRute))
			return false;
		int[] koordFra = til_koordinater(fraRute);
		int[] koordTil = til_koordinater(tilRute);
		brikkene[koordTil[0]][koordTil[1]] = brikkene[koordFra[0]][koordFra[1]];
		brikkene[koordFra[0]][koordFra[1]] = null; // Fjern brikken fra gamle posisjon.
		return true;
	}
}
