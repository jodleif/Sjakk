package Sjakk.Brikker;

import Sjakk.Brett.Brett;
import Sjakk.Regler.FlytteRegel;

/**
 * Created by Jo Øivind Gjernes on 20.10.2015.
 */
public class Dronning extends Offiser
{
	private static final FlytteRegel flytteRegel = new FlytteRegel(true, true, true, true);

	public Dronning(Brett brett, String rutenavn, Farge farge) throws IllegalArgumentException
	{
		super(brett, rutenavn, farge);
	}

	@Override
	public boolean erLovligTrekk(String rutenavn)
	{
		return flytteRegel.gyldigTrekk(getRuteNavn(), rutenavn, this, getBrett());
	}

	@Override
	public String brikkenavn()
	{
		return "D";
	}
}
