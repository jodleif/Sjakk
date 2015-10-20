package Sjakk.Regler;

import Sjakk.Brett.Brett;
import Sjakk.Brikker.Brikke;
import Sjakk.Brikker.Farge;

/**
 * Created by Jo Øivind Gjernes on 20.10.2015.
 *
 * Skal inneholde et rammeverk for flytteregel
 */
public class FlytteRegel
{
	private boolean hoppOverAndre = false; // Standard.
	private boolean flytteSidelengs;
	private boolean flytteFremover;
	private boolean flytteBakover; // Bønder kan ikke dette.
	private boolean flytteDiagonalt;
//	private boolean flytteSpesielt; FOR SPRINGEREN

	public FlytteRegel(boolean flytteSidelengs, boolean flytteFremover, boolean flytteBakover, boolean flytteDiagonalt)
	{
		this.flytteSidelengs = flytteSidelengs;
		this.flytteFremover = flytteFremover;
		this.flytteBakover = flytteBakover;
		this.flytteDiagonalt = flytteDiagonalt;
	}

	public FlytteRegel(boolean flytteSidelengs, boolean flytteFremover, boolean flytteBakover, boolean flytteDiagonalt, boolean hoppOverAndre)
	{
		this.hoppOverAndre = hoppOverAndre;
		this.flytteSidelengs = flytteSidelengs;
		this.flytteFremover = flytteFremover;
		this.flytteBakover = flytteBakover;
		this.flytteDiagonalt = flytteDiagonalt;
	}

	public boolean gyldigTrekk(String fraPos, String tilPos, Brikke br, Brett brett)
	{
		Retning retning = finnRetning(fraPos, tilPos, br.getFarge());
		if(retning==null) return false;
		switch(retning){
			case FREM:
				return flytteFremover;
			case BAKOVER:
				return flytteBakover;
			case DIAGONALT:
				return flytteDiagonalt;
			case SIDELENGS:
				return flytteSidelengs;
			default:
				return false;
		}
	}

	/**
	 * Metode som sjekker rutene mellom fra og til posisjon for å finne ut om det er noen brikker "på veien"
	 * @param fraPos brikkens fra-posisjon
	 * @param tilPos brikkens til-posisjon
	 * @param brett spillbrettet
	 * @return true hvis det oppstår kollisjoner.
	 */
	private static boolean kollisjonsSjekk(String fraPos, String tilPos, Brett brett)
	{
		int[] fraKoord = Koordinater.til_koordinater(fraPos);
		int[] tilKoord = Koordinater.til_koordinater(tilPos);
		int[] differanse = differanse(fraKoord, tilKoord);

	}

	/**
	 * Bestemmer retningen flyttingen går i
	 * @param fraPos brikkens fra-posisjon
	 * @param tilPos brikkens til-posisjon
	 * @param farge brikkens farge (trengs for å finne ut om man beveger seg fremover eller bakover)
	 * @return Returnerer hvilken retning bevegelsen vil være - (SE enumen Retning)
	 */
	private static Retning finnRetning(String fraPos, String tilPos, Farge farge)
	{
		int[] koordFra = Koordinater.til_koordinater(fraPos);
		int[] koordTil = Koordinater.til_koordinater(tilPos);
		int[] diff = differanse(koordFra, koordTil);
		// Frem og tilbake
		if(diff[0]==0&&diff[1]!=0){
			if(diff[1]>0){
				return (farge==Farge.HVIT) ? Retning.FREM : Retning.BAKOVER;
			}
			if(diff[1]<0){
				return (farge==Farge.HVIT) ? Retning.BAKOVER : Retning.FREM;
			}
		}
		// Sidelengs
		if(diff[0]!=0&&diff[1]==0){
			return Retning.SIDELENGS;
		}
		// Diagonalt - TODO: fikse- ikke helt korrekt.
		if(diff[1]==diff[0]){
			return Retning.DIAGONALT;
		}
		return null;
	}

	private static int[] differanse(int[] fra, int[] til)
	{
		if(fra.length!=til.length)
			return null;
		return new int[] {til[0]-fra[0],til[1]-fra[1]};
	}

}
