package Sjakk.Brett.Historikk;

import Sjakk.Brett.Brett;
import Sjakk.Brikker.Brikke;

/**
 * Created by Jo Øivind Gjernes on 21.10.2015.
 * <p>
 * Klasse som beskriver et trekk i spillet
 * <p>
 * Litt uggen implementasjon... Burde muligens håndtert angringen fra brettet, og behandlet dette kun som en kommandoliste.
 */
public class Trekk
{

	private Brikke[][] snapshot;

	public Trekk(Brikke[][] brett)
	{
		snapshot = new Brikke[Brett.BRETTSTØRRELSE][Brett.BRETTSTØRRELSE];
		kopierBrett(brett);
	}

	private void kopierBrett(Brikke[][] brett)
	{
		for (int i = 0; i < Brett.BRETTSTØRRELSE; ++i) {
			for (int j = 0; j < Brett.BRETTSTØRRELSE; j++) {
				Brikke tmp = brett[i][j];
				if (tmp != null) {
					snapshot[i][j] = tmp.kopierBrikken();
				}
			}
		}
	}

	public Brikke[][] getSnapshot(){return snapshot;}
}
