package Sjakk.Brikker;

import Sjakk.Brett.Brett;
import Sjakk.Regler.FlytteRegel;

/**
 * Created by Jo Øivind Gjernes on 20.10.2015.
 */
public class Bonde extends Brikke
{
	private static FlytteRegel flytteRegel;

	public Bonde(Brett brett, String rutenavn, Farge farge) throws IllegalArgumentException
	{
		super(brett, rutenavn, farge);
		flytteRegel = new FlytteRegel(false, true, false, false); // Kan kun flytte fremover.
	}

	@Override
	public boolean erLovligTrekk(String rutenavn)
	{
		return flytteRegel.gyldigTrekk(getRuteNavn(), rutenavn, this, getBrett());
	}

	@Override
	public String brikkenavn()
	{
		return "B"; // Bonde
	}
}
