package Sjakk.Brett.Brikker;

import Sjakk.Brett.Brett;
import Sjakk.Regler.Farge;
import Sjakk.Regler.FlytteRegel;

/**
 * Created by Jo Øivind Gjernes on 20.10.2015.
 *
 * Sjakkbrikken springer.
 */
public class Springer extends Offiser
{

	public Springer(Brett brett, int ruteid, Farge farge) throws IllegalArgumentException
	{
		super(brett, ruteid, farge);
		flytteRegel = new FlytteRegel(true, true);
		poeng = 5;
	}


	@Override
	public String brikkenavn()
	{
		return "S";
	}

	@Override
	public Brikke kopierBrikken()
	{
		Brikke b = new Springer(getBrett(), getRuteid(), getFarge());
		b.setAntTrekk(getAntallTrekk());
		return b;
	}
}
