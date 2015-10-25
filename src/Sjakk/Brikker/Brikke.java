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
	private String ruteNavn;
	private Farge farge;
	private Brett brett;
	private int antTrekk = 0;


	public Brikke(Brett brett, String rutenavn, Farge farge) throws IllegalArgumentException {
		this.farge = farge;
		this.brett = brett;
		if(Brett.erLovligRutenavn(rutenavn)){
			this.ruteNavn = rutenavn;
		} else {
			throw new IllegalArgumentException("Ugyldig rutenavn");
		}
	}


	public abstract Brikke kopierBrikken();
	/***
	 * Implementeres i underklasser- unike regler for hver "brikketype"
	 * @param rutenavn navnet på ruten brikken skal flyttes til. på sjakkformat i.e. a1
	 * @return returnerer true hvis trekket er gyldig.
	 */
	public boolean erLovligTrekk(String rutenavn)
	{
		return flytteRegel.gyldigTrekk(getRuteNavn(), rutenavn, this, brett);
	}

	public boolean erLovligAngrep(String rutenavn)
	{
		return erLovligTrekk(rutenavn);
	}

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
				antTrekk++;
				System.out.println("FLYTTER" + this.brikkenavn());
				return true; // Ferdig flyttet!
			}
		}
		return false; // "noe" gikk galt, men har ikke endret noe!
	}
	public String getRuteNavn()
	{
		return ruteNavn;
	}

	public void setRuteNavn(String ruteNavn)
	{
		this.ruteNavn = ruteNavn;
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

	public boolean sjekkSjakk()
	{
		return false;
	}

	/**
	 * Sjekk om en posisjon er "opptatt" av en brikke av samme farge.
	 * @param sjakkPos posisjon man sjekker
	 * @return true hvis det er en kollisjon (feltet er ikke tomt og inneholder en brikke av samme farge.
	 */
	private boolean sjekkForKollisjoner(String sjakkPos)
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
		return b.getFarge() == getFarge() && b.getRuteNavn().equals(getRuteNavn()) && b.brikkenavn().equals(brikkenavn());
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
