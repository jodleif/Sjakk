package Sjakk.Brikker;

import Sjakk.Brett.Brett;
import Sjakk.Regler.Farge;
import Sjakk.Regler.FlytteRegel;

/**
 * Created by Jo Øivind Gjernes on 20.10.2015.
 *
 * Sjakkbrikken tårn.
 */
public class Tårn extends Offiser
{
	private static final String BRIKKENAVN = "T";

	public Tårn(Brett brett, String rutenavn, Farge farge) throws IllegalArgumentException
	{
		super(brett, rutenavn, farge);
		flytteRegel = new FlytteRegel(true, true, true, false);
	}

	@Override
	public String brikkenavn()
	{
		return BRIKKENAVN;
	}

	@Override
	public Brikke kopierBrikken()
	{
		Brikke b = new Tårn(getBrett(), getRuteNavn(), getFarge());
		b.setAntTrekk(getAntallTrekk());
		return b;
	}
}
