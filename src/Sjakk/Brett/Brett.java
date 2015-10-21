package Sjakk.Brett;

/**
 * Created by Jo Øivind Gjernes on 20.10.2015.
 */

import Sjakk.Brikker.*;
import Sjakk.Regler.Farge;
import Sjakk.Regler.Koordinater;
import Sjakk.Regler.PosisjonRegler;

/***
 * Klasse for sjakkbrettet i spill-logikk.
 */
public class Brett
{


	public static final int BRETTSTØRRELSE = 8; // Vil ha tilgang til denne fra GUI
	private int spillNr;
	private Brikke[][] brikkene;

	/**
	 * Opprett et nytt spillbrett
	 * @param nyttSpillNr nummeret på spillet.
	 */
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

	/**
	 * regner fra sjakk koordinater til array koordinater
	 * @param rutenavn sjakk-koordinat
	 * @return array-koordinat
	 */
	private int[] tilKoordinater(String rutenavn)
	{
		return Koordinater.til_koordinater(rutenavn);
	}

	/**
	 * regner fra array-koordinater til sjakk-koordinater
	 * merk: kan fjernes- og erstattes med Koordinater.fraKoordinater
	 * @param koordinater
	 * @return
	 */
	private String fraKoordinater(int[] koordinater)
	{
		return Koordinater.fra_koordinater(koordinater);
	}

	/**
	 * Opprett spillbrikker- og plasser dem på sine startsposisjoner
	 * @throws IllegalArgumentException
	 */
	private void opprettSpillbrikker() throws IllegalArgumentException
	{

		for (int j = 1; j < BRETTSTØRRELSE; j += (BRETTSTØRRELSE - 3)) { // Andre rad og sjette rad.
			for (int i = 0; i < BRETTSTØRRELSE; ++i) {
				Farge farge = (j == 1) ? Farge.HVIT : Farge.SVART;
				String nyRuteNavn = fraKoordinater(new int[]{i, j});
				if (nyRuteNavn != null) {
					brikkene[i][j] = new Bonde(this, nyRuteNavn, farge);
				}
			}
		}
		for (String tårnPos : PosisjonRegler.TÅRNPOSISJONER) {
			int[] koord = tilKoordinater(tårnPos);
			Farge farge = (koord[1] <= 1) ? Farge.HVIT : Farge.SVART; // Hvis rad 1 eller 2 (koordinat 0, 1) Hvite brikker.
			brikkene[koord[0]][koord[1]] = new Tårn(this, tårnPos, farge);
		}
		for (String springerPos : PosisjonRegler.SPRINGERPOSISJONER) {
			int[] koord = tilKoordinater(springerPos);
			Farge farge = (koord[1] <= 1) ? Farge.HVIT : Farge.SVART;
			brikkene[koord[0]][koord[1]] = new Springer(this, springerPos, farge);
		}
		for (String løperPos : PosisjonRegler.LØPERPOSISJONER) {
			int[] koord = tilKoordinater(løperPos);
			Farge farge = (koord[1] <= 1) ? Farge.HVIT : Farge.SVART;
			brikkene[koord[0]][koord[1]] = new Løper(this, løperPos, farge);
		}
		for (String kongePos : PosisjonRegler.KONGEPOSISJONER) {
			int[] koord = tilKoordinater(kongePos);
			Farge farge = (koord[1] <= 1) ? Farge.HVIT : Farge.SVART;
			brikkene[koord[0]][koord[1]] = new Konge(this, kongePos, farge);
		}
		for (String dronningPos : PosisjonRegler.DRONNINGPOSISJONER) {
			int[] koord = tilKoordinater(dronningPos);
			Farge farge = (koord[1] <= 1) ? Farge.HVIT : Farge.SVART;
			brikkene[koord[0]][koord[1]] = new Dronning(this, dronningPos, farge);
		}

	}

	/**
	 * Er rutenavnet et gyldig rutenavn?
	 * merk: flyttet ut i en separat "hjelpeklasse" metoden kan fjernes, men dette krever noen endringer i koden i andre metoder
	 * @param rutenavn er dette rutenavnet gyldig?
	 * @return true hvis det er et gyldig rutenavn (a1-h8)
	 */
	public static boolean erLovligRutenavn(String rutenavn)
	{
		return Koordinater.erLovligRutenavn(rutenavn);
	}

	/**
	 * Hent spillbrikken i den spesifiserte ruten.
	 * @param rutenavn navnet på ruten
	 * @return brikken i rutenavn, null dersom ruten er tom.
	 */
	public Brikke getBrikke(String rutenavn)
	{
		int[] koordinater = tilKoordinater(rutenavn);
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
		int[] koordFra = tilKoordinater(fraRute);
		int[] koordTil = tilKoordinater(tilRute);
		brikkene[koordTil[0]][koordTil[1]] = brikkene[koordFra[0]][koordFra[1]];
		brikkene[koordFra[0]][koordFra[1]] = null; // Fjern brikken fra gamle posisjon.
		return true;
	}
}
