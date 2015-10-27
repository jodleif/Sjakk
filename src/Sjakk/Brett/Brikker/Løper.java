package Sjakk.Brett.Brikker;

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
	public Løper(Brett brett, int ruteid, Farge farge) throws IllegalArgumentException
	{
		super(brett, ruteid, farge);
		flytteRegel = new FlytteRegel(false,false,false,true);
		poeng = 5;
	}

	@Override
	public String brikkenavn()
	{
		return "L";
	}

	@Override
	public Brikke kopierBrikken()
	{
		Brikke b = new Løper(getBrett(), getRuteid(), getFarge());
		b.setAntTrekk(getAntallTrekk());
		return b;
	}
}
