package Sjakk.Brikker;

import Sjakk.Brett.Brett;
import Sjakk.Regler.Farge;
import Sjakk.Regler.FlytteRegel;

import java.util.ArrayList;

/**
 * Created by Jo Øivind Gjernes on 20.10.2015.
 *
 * Sjakkbrikken "konge"
 */
public class Konge extends Offiser
{
	public Konge(Brett brett, String rutenavn, Farge farge) throws IllegalArgumentException
	{
		super(brett, rutenavn, farge);
		flytteRegel = new FlytteRegel(true, true, true, true, 1);
	}

	@Override
	public boolean erLovligTrekk(String rutenavn)
	{
		Boolean gyldig = flytteRegel.gyldigTrekk(getRuteNavn(), rutenavn, this, getBrett());
		if (!gyldig) return false;
		return !sjekkSjakk(rutenavn);
	}

	/**
	 * @param rutenavn
	 * @return
	 */
	private boolean sjekkSjakk(String rutenavn)
	{

		/**
		 * Tungvindt metode- brikken "flyttes" på brettet hvis feltet er åpent, så sjekkes det om den (kongen) fortsatt står i sjakk.
		 */
		if (getBrett().getBrikke(rutenavn) == null) {
			String backup = getRuteNavn();
			getBrett().setBrikke(rutenavn, this);
			getBrett().fjernBrikke(backup);
			boolean sjakk = sjekkSjakk();
			getBrett().setBrikke(backup, this);
			getBrett().fjernBrikke(rutenavn);
			return sjakk;
		} else {

			ArrayList<Brikke> brikker = getBrett().getAlleBrikker(getFarge().motsatt());
			ArrayList<Brikke> setterISjakk = new ArrayList<>();
			brikker.forEach((brikke) -> {
				if (brikke.erLovligAngrep(rutenavn)) {
					setterISjakk.add(brikke);
				}
			});
			if (setterISjakk.size() > 1) {
				return true;
			} else if (setterISjakk.size() == 1) {
				return !setterISjakk.get(0).getRuteNavn().equals(rutenavn);
			}
			return false;
		}
	}

	@Override
	public boolean sjekkSjakk()
	{
		//return sjekkSjakk(this.getRuteNavn());
		ArrayList<Brikke> brikker = getBrett().getAlleBrikker(getFarge().motsatt());
		ArrayList<Brikke> setterISjakk = new ArrayList<>();
		brikker.forEach((brikke) -> {
			if (brikke.erLovligAngrep(getRuteNavn())) {
				setterISjakk.add(brikke);
			}
		});
		return setterISjakk.size() != 0;
	}

	@Override
	public String brikkenavn()
	{
		return "K";
	}

	@Override
	public Brikke kopierBrikken()
	{
		Brikke b = new Konge(getBrett(), getRuteNavn(), getFarge());
		b.setAntTrekk(getAntallTrekk());
		return b;
	}

	public boolean rokker(boolean kongeside)
	{
		getBrett().Rokker(kongeside, getFarge());
		return true; // TODO: implementer i spillet, legge til som gyldig trekk.
	}
}
