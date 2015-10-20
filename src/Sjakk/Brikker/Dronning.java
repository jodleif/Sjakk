package Sjakk.Brikker;

import Sjakk.Brett;

/**
 * Created by Jo Øivind Gjernes on 20.10.2015.
 */
public class Dronning extends Offiser
{
	public Dronning(Brett brett, String rutenavn, Farge farge) throws IllegalArgumentException
	{
		super(brett, rutenavn, farge);
	}

	@Override
	public boolean erLovligTrekk(String rutenavn)
	{
		return false;
	}

	@Override
	public String brikkenavn()
	{
		return "D";
	}
}
