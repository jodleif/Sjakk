package Sjakk.Brikker;

import Sjakk.Brett.Brett;
import Sjakk.Regler.BondeFlytteRegel;
import Sjakk.Regler.FlytteRegel;

/**
 * Created by Jo Øivind Gjernes on 20.10.2015.
 */
public class Bonde extends Brikke
{
	private FlytteRegel flytteRegel;
	private boolean førsteTrekk = true;

	public Bonde(Brett brett, String rutenavn, Farge farge) throws IllegalArgumentException
	{
		super(brett, rutenavn, farge);
		flytteRegel = new BondeFlytteRegel(2); // Kan kun flytte fremover. 2 i første trekk
	}

	@Override
	public boolean erLovligTrekk(String rutenavn)
	{
		return flytteRegel.gyldigTrekk(getRuteNavn(), rutenavn, this, getBrett());
	}

	/**
	 * Sørger for at den flytter seg kun 2 på første trekk.
	 * @param ruteNavn ruten brikken skal flyttes til.
	 * @return
	 */
	@Override
	public boolean flyttTil(String ruteNavn)
	{
		boolean status = super.flyttTil(ruteNavn);
		if(status && førsteTrekk){
			førsteTrekk = false;
			flytteRegel.setMaxAvstand(1);
		}
		return status;
	}

	@Override
	public String brikkenavn()
	{
		return "B"; // Bonde
	}
}
