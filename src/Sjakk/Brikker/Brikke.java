package Sjakk.Brikker;

import Sjakk.Brett.Brett;
import Sjakk.Regler.Farge;
import Sjakk.Regler.FlytteRegel;
import Sjakk.Regler.Koordinater;

import java.util.ArrayList;

/**
 * Created by Jo Øivind Gjernes on 20.10.2015.
 *
 * "Prototype" for en spillbrikke
 */
public abstract class Brikke
{
	protected FlytteRegel flytteRegel;
	protected int poeng = 1;
	private int ruteid;
	private Farge farge;
	private Brett brett;
	private int antTrekk = 0;


	public Brikke(Brett brett, int ruteid, Farge farge) throws IllegalArgumentException
	{
		this.farge = farge;
		this.brett = brett;
		if (Brett.erLovligRutenavn(ruteid)) {
			this.ruteid = ruteid;
		} else {
			throw new IllegalArgumentException("Ugyldig rutenavn");
		}
	}


	public abstract Brikke kopierBrikken();
	/***
	 * Implementeres i underklasser- unike regler for hver "brikketype"
	 * @param tilRuteid navnet på ruten brikken skal flyttes til. på sjakkformat i.e. a1
	 * @return returnerer true hvis trekket er gyldig.
	 */
	public boolean erLovligTrekk(int tilRuteid)
	{
		return flytteRegel.gyldigTrekk(this.ruteid, tilRuteid, this, brett);
	}

	public boolean erLovligAngrep(int ruteid)
	{
		return erLovligTrekk(ruteid);
	}

	/***
	 * Implementeres i underklasse.
	 * @return returnerer navnet på brikken. (i.e. T for tårn)
	 */
	public abstract String brikkenavn();

	/***
	 * Flytt brikke til rutenavn. Mesteparten av sjekkingen skjer i "brett"
	 * @param tilRuteid ruten brikken skal flyttes til.
	 * @return true hvis den ble flyttet, false ellers.
	 *
	 */
	public boolean flyttTil(int tilRuteid)
	{
		Brikke tmp = brett.getBrikke(tilRuteid);
		if (tmp == null || (tmp.getFarge() != this.getFarge())) {
			if (brett.flyttBrikke(this.ruteid, tilRuteid)) { // Flytt på brettet, hvis false var det ulovlig
				this.ruteid = tilRuteid; // Oppdater posisjon hvis flyttingen ble utført
				antTrekk++;
				return true; // Ferdig flyttet!
			}
		}
		return false; // "noe" gikk galt, men har ikke endret noe!
	}

	public boolean flyttTil(String rutenavn)
	{
		if (rutenavn != null) {
			int ruteid = Koordinater.tilRuteid(rutenavn);
			return flyttTil(ruteid);
		}
		return false;
	}


	public int getRuteid()
	{
		return ruteid;
	}

	public void setRuteid(int ruteid)
	{
		this.ruteid = ruteid;
	}

	public Farge getFarge()
	{
		return farge;
	}

	public Brett getBrett()
	{
		return brett;
	}

	/**
	 * Brukes for å endre hvilket brett brikken tilhører
	 * @param brett nytt brett
	 */
	public void setBrett(Brett brett)
	{
		this.brett = brett;
	}

	/**
	 * Returnerer en array med alle gyldige trekk for brikken.
	 * @return array med gyldige trekk for brikken.
	 */
	public ArrayList<Integer> gyldigeTrekk()
	{
		return gyldigeTrekk(0, Brett.BRETTSTØRRELSE - 1, 0, Brett.BRETTSTØRRELSE - 1);
	}

	/**
	 * Gir gyldige trekk fra en range [min,max] (inklusiv siste)
	 *
	 * @param xMin
	 * @param xMax
	 * @param yMin
	 * @param yMax
	 * @return
	 */
	protected ArrayList<Integer> gyldigeTrekk(int xMin, int xMax, int yMin, int yMax)
	{
		ArrayList<Integer> tmp = new ArrayList<>();
		for (int y = yMin; y <= yMax; ++y) {
			for (int x = xMin; x <= xMax; ++x) {
				int ruteid = (y * 8) + x;
				if (erLovligTrekk(ruteid)&&(!sjekkForKollisjoner(ruteid))){
					tmp.add(ruteid);
				}
			}
		}
		return tmp;
	}

	public boolean sjekkSjakk()
	{
		return false;
	}

	/**
	 * Sjekk om en posisjon er "opptatt" av en brikke av samme farge.
	 * @param sjakkPos posisjon man sjekker
	 * @return true hvis det er en kollisjon (feltet er ikke tomt og inneholder en brikke av samme farge.
	 */
	private boolean sjekkForKollisjoner(int sjakkPos)
	{
		Brikke tmp = brett.getBrikke(sjakkPos);
		return tmp != null && (tmp.getFarge() == this.getFarge());
	}

	@Override
	public boolean equals(Object obj)
	{
		if(obj==null)
			return false;
		Brikke b = (Brikke)obj;
		return b.getFarge() == getFarge() && b.getRuteid() == getRuteid() && b.brikkenavn().equals(brikkenavn());
	}

	public int getAntallTrekk(){
		return antTrekk;
	}

	public void reduserAntallTrekk(){
		--antTrekk;
		//assert(antTrekk>=0);
	}

	public void økAntallTrekk()
	{
		++antTrekk;
		//assert(antTrekk>=0);
	}

	public int getPoeng()
	{
		return poeng;
	}

	protected void setAntTrekk(int antallTrekk){
		this.antTrekk = antallTrekk;
	}
}
