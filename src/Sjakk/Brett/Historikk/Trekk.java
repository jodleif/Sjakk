package Sjakk.Brett.Historikk;

import Sjakk.Brett.Brikker.Brikke;
import Sjakk.Regler.Farge;

/**
 * Created by Jo Øivind Gjernes on 21.10.2015.
 * <p>
 * Klasse som beskriver et trekk i spillet
 * <p>
 * Litt uggen implementasjon... Burde muligens håndtert angringen fra brettet, og behandlet dette kun som en kommandoliste.
 */
public class Trekk
{

	private Brikke flyttetBrikke;
	private int flyttTil;
	private Brikke fjernetBrikke;
	private Farge spillerSittTrekk;
	private int poeng;

	/**
	 * Opprett trekk
	 *
	 * @param flyttetBrikke    brikken som blir flyttet
	 * @param flyttTil         dit brikken blir flyttet
	 * @param fjernetBrikke    brikken som evt. blir fjernet
	 * @param spillerSittTrekk hvilken spiller sin tur det er
	 * @param poeng            antall poeng som ligger på brettet.
	 */
	public Trekk(Brikke flyttetBrikke, int flyttTil, Brikke fjernetBrikke, Farge spillerSittTrekk, int poeng)
	{
		if (flyttetBrikke != null)
			this.flyttetBrikke = flyttetBrikke.kopierBrikken();
		this.flyttTil = flyttTil;
		if (fjernetBrikke != null)
			this.fjernetBrikke = fjernetBrikke.kopierBrikken();
		this.spillerSittTrekk = spillerSittTrekk;
		this.poeng = poeng;

	}

	/**
	 *
	 * @return flyttet brikke
	 */
	public Brikke getFlyttetBrikke()
	{
		return flyttetBrikke;
	}

	/**
	 * @return evt fjernet brikke
	 */
	public Brikke getFjernetBrikke()
	{
		return fjernetBrikke;
	}

	/**
	 * @return hvor brikken ble flyttet til
	 */
	public int getFlyttTil()
	{
		return flyttTil;
	}

	public int getPoeng()
	{
		return poeng;
	}

	public Farge getSpillerSittTrekk()
	{
		return spillerSittTrekk;
	}
}
