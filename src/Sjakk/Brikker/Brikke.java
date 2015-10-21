package Sjakk.Brikker;

import Sjakk.Brett.Brett;
import Sjakk.Regler.Koordinater;

import java.util.ArrayList;

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
		if(Brett.erLovligRutenavn(rutenavn)){
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
	 * Flytt brikke til rutenavn. Mesteparten av sjekkingen skjer i "brett"
	 * @param ruteNavn ruten brikken skal flyttes til.
	 * @return true hvis den ble flyttet, false ellers.
	 *
	 */
	public boolean flyttTil(String ruteNavn){
		if(brett.getBrikke(ruteNavn) == null || (brett.getBrikke(ruteNavn).getFarge()!=this.getFarge())) {
			if (brett.flyttBrikke(this.ruteNavn, ruteNavn)) { // Flytt på brettet, hvis false var det ulovlig
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

	public String[] gyldigeTrekk()
	{
		ArrayList<String> tmp = new ArrayList<>();
		String trekk;
		for(int i=0;i<Brett.BRETTSTØRRELSE;++i){
			for(int j=0;j<Brett.BRETTSTØRRELSE;++j){
				trekk = Koordinater.fra_koordinater(new int[]{i,j});
				if(erLovligTrekk(trekk)&&(!sjekkForKollisjoner(trekk))){
					tmp.add(trekk);
				}
			}
		}
		String[] str = new String[tmp.size()];
		tmp.toArray(str);
		return str;
	}

	private boolean sjekkForKollisjoner(String sjakkPos)
	{
		Brikke tmp = brett.getBrikke(sjakkPos);
		return tmp != null && (tmp.getFarge() == this.getFarge());
	}
}
