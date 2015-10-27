package Sjakk.Brett.Brikker;

import Sjakk.Brett.Brett;
import Sjakk.Regler.Farge;
import Sjakk.Regler.FlytteRegel;

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

	/**
	 * Konstruktør for klassen Brikke
	 *
	 * @param brett  Brettet brikken plasseres på
	 * @param ruteid ruten brikken står i
	 * @param farge  brikkens farge.
	 * @throws IllegalArgumentException
	 */
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


	/**
	 * Metode for å kopiere brikken. Implementeres i hver klasse som arver av denne
	 * @return
	 */
	public abstract Brikke kopierBrikken();
	/***
	 * Implementeres i underklasser- unike regler for hver "brikketype"
	 * @param tilRuteid navnet på ruten brikken skal flyttes til. på sjakkformat i.e. a1
	 * @return returnerer true hvis trekket er gyldig.
	 */
	public boolean erLovligTrekk(int tilRuteid)
	{
		if (tilRuteid == getRuteid()) return false;
		return flytteRegel.gyldigTrekk(this.ruteid, tilRuteid, this, brett);
	}

	/**
	 * Funksjon for å se om et trekk er et gyldig angrep. For de fleste brikker er lovlige trekk = lovlig angrep. Untatt for bonden.
	 * @param tilRute ruteid man sjekker om er et gyldig angrep
	 * @return true hvis man kan angripe ruten
	 */
	public boolean erLovligAngrep(int tilRute)
	{
		return erLovligTrekk(tilRute);
	}

	/***
	 * Implementeres i underklasse.
	 * @return returnerer navnet på brikken. (i.e. T for tårn)
	 */
	public abstract String brikkenavn();

	/**
	 *
	 * @return
	 */
	public int getRuteid()
	{
		return ruteid;
	}

	/**
	 * Setter brikkens ruteid
	 * @param ruteid id til ruten der brikken står.
	 */
	public void setRuteid(int ruteid)
	{
		this.ruteid = ruteid;
	}

	/**
	 *
	 * @return Brikkens farge
	 */
	public Farge getFarge()
	{
		return farge;
	}

	/**
	 * @return Brettet brikken tilhører.
	 */
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
	 * @param xMin minimum x-koordinat å sjekke
	 * @param xMax maximum x-koordinat å sjekke
	 * @param yMin minimuam y-koordinat å sjekke
	 * @param yMax maksimum y-koordinat å sjekke
	 * @return Liste over alle gyldige trekk for brikken
	 */
	protected ArrayList<Integer> gyldigeTrekk(int xMin, int xMax, int yMin, int yMax)
	{
		ArrayList<Integer> tmp = new ArrayList<>();
		for (int y = yMin; y <= yMax; ++y) {
			for (int x = xMin; x <= xMax; ++x) {
				int localRuteid = (y * 8) + x;
				if (erLovligTrekk(localRuteid) && (!sjekkForKollisjoner(localRuteid))) {
					tmp.add(localRuteid);
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

	/**
	 * @return antall trekk brikken er flyttet
	 */
	public int getAntallTrekk(){
		return antTrekk;
	}

	public void reduserAntallTrekk()
	{
		--antTrekk;
		//assert(antTrekk>=0);
	}

	/**
	 * Øker antall trekk brikken er flyttet med en.
	 */
	public void økAntallTrekk()
	{
		++antTrekk;
		//assert(antTrekk>=0);
	}

	/**
	 * @return Antall poeng brikken er verdt (til AI)
	 */
	public int getPoeng()
	{
		return poeng;
	}

	/**
	 * Sett hvor mange trekk brikken er flyttet.
	 * @param antallTrekk antall trekk brikken har flyttet
	 */
	protected void setAntTrekk(int antallTrekk){
		this.antTrekk = antallTrekk;
	}
}
