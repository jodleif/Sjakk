package Sjakk.Brikker;

import Sjakk.Brett.Brett;
import Sjakk.Regler.Farge;
import Sjakk.Regler.FlytteRegel;

/**
 * Created by Jo Øivind Gjernes on 20.10.2015.
 *
 * Sjakkbrikken løper
 */
public class Løper extends Offiser
{
	private static FlytteRegel flytteRegel;

	public Løper(Brett brett, String rutenavn, Farge farge) throws IllegalArgumentException
	{
		super(brett, rutenavn, farge);
		flytteRegel = new FlytteRegel(false,false,false,true);
	}

	@Override
	public boolean erLovligTrekk(String rutenavn)
	{
		return flytteRegel.gyldigTrekk(getRuteNavn(), rutenavn, this, getBrett());
	}

	@Override
	public String brikkenavn()
	{
		return "L";
	}

	@Override
	public Brikke kopierBrikken()
	{
		Brikke b = new Løper(getBrett(), getRuteNavn(), getFarge());
		b.setAntTrekk(getAntallTrekk());
		return b;
	}
}
