package SjakkApp.SjakkLogikk.Brett.Brikker;

import SjakkApp.SjakkLogikk.Brett.Brett;
import SjakkApp.SjakkLogikk.Regler.Farge;

/**
 * Created by Jo Øivind Gjernes on 20.10.2015.
 *
 * En brikke som ikke er bonde.
 */
public abstract class Offiser extends Brikke
{
	public Offiser(Brett brett, int ruteid, Farge farge) throws IllegalArgumentException
	{
		super(brett, ruteid, farge);
	}

}
