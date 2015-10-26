package Sjakk.Brett.Historikk;

import Sjakk.Brikker.Brikke;
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

	public Brikke getFlyttetBrikke()
	{
		return flyttetBrikke;
	}

	public Brikke getFjernetBrikke()
	{
		return fjernetBrikke;
	}

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
