package Sjakk.Brikker;

import Sjakk.Brett.Brett;

/**
 * Created by Jo Øivind Gjernes on 20.10.2015.
 */
public class Tårn extends Offiser
{
	private static final String BRIKKENAVN = "T";

	public Tårn(Brett brett, String rutenavn, Farge farge) throws IllegalArgumentException
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
		return BRIKKENAVN;
	}
}
