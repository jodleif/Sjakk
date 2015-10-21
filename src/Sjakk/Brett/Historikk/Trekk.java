package Sjakk.Brett.Historikk;

import Sjakk.Brett.Brett;
import Sjakk.Brikker.Brikke;
import Sjakk.Regler.Farge;

/**
 * Created by Jo Øivind Gjernes on 21.10.2015.
 *
 * Klasse som beskriver et trekk i spillet
 *
 * Litt uggen implementasjon... Burde muligens håndtert angringen fra brettet, og behandlet dette kun som en kommandoliste.
 */
public class Trekk
{
	private String fraPos;
	private String tilPos;
	private String brikkeType;
	private Farge farge;
	private Brett brett;
	private Brikke fjernetBrikke;

	public Trekk(String fraPos, String tilPos, String brikkeType, Farge farge, Brett brett)
	{
		this.fraPos = fraPos;
		this.tilPos = tilPos;
		this.brikkeType = brikkeType;
		this.farge = farge;
		this.brett = brett;
	}

	public Trekk(String fraPos, String tilPos, String brikkeType, Farge farge, Brett brett, Brikke fjernetBrikke)
	{
		this.fraPos = fraPos;
		this.tilPos = tilPos;
		this.brikkeType = brikkeType;
		this.farge = farge;
		this.brett = brett;
		this.fjernetBrikke = fjernetBrikke;
	}

	public void angre() {
		Brikke tmp = brett.getBrikke(tilPos);
		if(tmp!=null&&tmp.getFarge()==farge&&tmp.brikkenavn().equals(brikkeType)) {
			brett.setBrikke(fraPos, tmp);
			tmp.reduserAntallTrekk();
			if (fjernetBrikke != null) {
				brett.setBrikke(fjernetBrikke.getRuteNavn(), fjernetBrikke);
			} else {
				brett.fjernBrikke(tilPos);
			}
		} else {
			System.err.println("[Trekk.angre] Feilet!");
		}
	}

	public void utfør() {
		Brikke tmp = brett.getBrikke(fraPos);
		if(tmp!=null&&tmp.getFarge()==farge&&tmp.brikkenavn().equals(brikkeType)){ // Sjekker for å være sikker på at historikken stemmer overens med spillbrettet
			brett.setBrikke(tilPos,tmp);
			brett.fjernBrikke(fraPos);
		} else {
			System.err.println("[Trekk.utfør] Feilet!");
		}
	}
	public void setBrett(Brett brett)
	{
		this.brett = brett;
		if(fjernetBrikke!=null){
			fjernetBrikke.setBrett(brett);
		}
	}
}
