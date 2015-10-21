package Sjakk.Brikker;

import Sjakk.Brett.Brett;
import Sjakk.Regler.FlytteRegel;

/**
 * Created by Jo Øivind Gjernes on 20.10.2015.
 */
public class Konge extends Offiser
{
	private static FlytteRegel flytteRegel;
	public Konge(Brett brett, String rutenavn, Farge farge) throws IllegalArgumentException
	{
		super(brett, rutenavn, farge);
		flytteRegel = new FlytteRegel(true, true, true, true, 1);
	}

	@Override
	public boolean erLovligTrekk(String rutenavn)
	{
		return flytteRegel.gyldigTrekk(getRuteNavn(), rutenavn, this, getBrett());
	}

	@Override
	public String brikkenavn()
	{
		return "K";
	}
}
