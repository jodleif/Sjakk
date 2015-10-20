package Sjakk.Brikker;

import Sjakk.Brett;

/**
 * Created by Jo Øivind Gjernes on 20.10.2015.
 */
public abstract class Brikke
{
	private String ruteNavn;
	private Farge farge;
	private Brett brett;

	public Brikke(Brett brett, String rutenavn, Farge farge) throws IllegalArgumentException {
		this.farge = farge;
		this.brett = brett;
		if(brett.erLovligRutenavn(rutenavn)){
			this.ruteNavn = rutenavn;
		} else {
			throw new IllegalArgumentException("Ugyldig rutenavn");
		}
	}

	/***
	 * Implementeres i underklasser- unike regler for hver "brikketype"
	 * @param rutenavn navnet på ruten brikken skal flyttes til. på sjakkformat i.e. a1
	 * @return returnerer true hvis trekket er gyldig.
	 */
	public abstract boolean erLovligTrekk(String rutenavn);

	/***
	 * Implementeres i underklasse.
	 * @return returnerer navnet på brikken. (i.e. T for tårn)
	 */
	public abstract String brikkenavn();

	/***
	 * Flytt brikke til rutenavn.
	 * @param ruteNavn ruten brikken skal flyttes til.
	 * @return true hvis den ble flyttet, false ellers.
	 * TODO: Krasjdeteksjon (med andre brikker)
	 */
	public boolean flyttTil(String ruteNavn){
		// Første sjekk! Er det et gyldig rutenavn?
		if(!brett.erLovligRutenavn(ruteNavn))
			return false;
		// Hvis trekket er lovlig- utfør!
		if(erLovligTrekk(ruteNavn)){
			if(brett.flyttBrikke(this.ruteNavn, ruteNavn)){ // Flytt på brettet
				this.ruteNavn = ruteNavn; // Oppdater posisjon hvis flyttingen ble utført
				return true; // Ferdig flyttet!
			}
		}
		return false; // "noe" gikk galt, men har ikke endret noe!
	}
	public String getRuteNavn()
	{
		return ruteNavn;
	}

	public Farge getFarge()
	{
		return farge;
	}

	public Brett getBrett()
	{
		return brett;
	}
}
