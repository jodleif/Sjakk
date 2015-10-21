package Sjakk.Brikker;

import Sjakk.Brett.Brett;
import Sjakk.Regler.Farge;

/**
 * Created by Jo Øivind Gjernes on 20.10.2015.
 *
 * En brikke som ikke er bonde.
 */
public abstract class Offiser extends Brikke
{
	public Offiser(Brett brett, String rutenavn, Farge farge) throws IllegalArgumentException
	{
		super(brett, rutenavn, farge);
	}
}
