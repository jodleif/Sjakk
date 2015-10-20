package Sjakk;

/**
 * Created by Jo Øivind Gjernes on 20.10.2015.
 */

import Sjakk.Brikker.Brikke;

/***
 *
 */
public class Brett
{
	private static final int BRETTSTØRRELSE = 8;
	private int spillNr;
	private Brikke[][] brikkene;

	public Brett(int nyttSpillNr){

	}

	public static boolean erLovligRutenavn(String rutenavn){
		if(rutenavn.length()!=2)
			return false;
		char bokstavDel = rutenavn.charAt(0);
		char talldel = rutenavn.charAt(1);

		if(bokstavDel<'a'||bokstavDel>'h')
			return false;
		if(talldel<'1'||talldel>'8')
			return false;

		return true;
	}

	public Brikke getBrikke(String rutenavn){

	}

	public boolean flyttBrikke(String fraRute, String tilRute)
	{

	}
}
