package Sjakk.Regler;

import Sjakk.Brett.Brett;
import Sjakk.Brikker.Brikke;
import Sjakk.Brikker.Farge;

/**
 * Created by Jo �ivind Gjernes on 20.10.2015.
 * <p>
 * Skal inneholde et rammeverk for flytteregel
 */
public class FlytteRegel
{
	private boolean hoppOverAndre = false; // Standard.
	private boolean flytteSidelengs;
	private boolean flytteFremover;
	private boolean flytteBakover; // Bønder kan ikke dette.
	private boolean flytteDiagonalt;
	private boolean flytteSpringer = false;
	// private int maxAvstand; TODO: implementere

	/**
	 * Flytteregel for en brikke
	 * @param flytteSidelengs Har lov til å flytte sidelengs (a-h)
	 * @param flytteFremover Har lov til å flytte fremover (avhengig av farge)
	 * @param flytteBakover Har lov til å flytte bakover
	 * @param flytteDiagonalt Har lov til å flytte diagonalt
	 */
	public FlytteRegel(boolean flytteSidelengs, boolean flytteFremover, boolean flytteBakover, boolean flytteDiagonalt)
	{
		this.flytteSidelengs = flytteSidelengs;
		this.flytteFremover = flytteFremover;
		this.flytteBakover = flytteBakover;
		this.flytteDiagonalt = flytteDiagonalt;
	}
	/**
	 * Flytteregel for en brikke
	 * @param flytteSpringer Har lov til å flytte seg etter springer regler
	 * @param hoppOverAndre Har lov til å hoppe over andre brikker
	 */
	public FlytteRegel(boolean hoppOverAndre, boolean flytteSpringer)
	{
		this.hoppOverAndre = hoppOverAndre;
		this.flytteSidelengs = false;
		this.flytteFremover = false;
		this.flytteBakover = false;
		this.flytteDiagonalt = false;
		this.flytteSpringer = true;
	}

	public boolean gyldigTrekk(String fraPos, String tilPos, Brikke br, Brett brett)
	{
		Retning retning = finnRetning(fraPos, tilPos, br.getFarge());
		if (retning == null) return false;
		if(!hoppOverAndre){ // Hvis den ikke kan hoppe over andre brikker!
			if(kollisjonsSjekk(fraPos,tilPos,brett)){
				System.out.println("DEBUG: KOLLISJON!");
				return false; // Hvis kollisjon - ugyldig trekk!
			}
		}
		switch (retning) {
			case FREM:
				System.out.println("DEBUG: Flytter fremover!");
				return flytteFremover;
			case BAKOVER:
				System.out.println("DEBUG: Flytter bakover!");
				return flytteBakover;
			case DIAGONALT:
				System.out.println("DEBUG: Flytter diagonalt");
				return flytteDiagonalt;
			case SIDELENGS:
				System.out.println("DEBUG: Flytter sidelengs!");
				return flytteSidelengs;
			case SPRINGER:
				System.out.println("DEBUG: Flytter SPRINGER");
				return flytteSpringer;
			default:
				return false;
		}
	}

	/**
	 * Metode som sjekker rutene mellom fra og til posisjon for � finne ut om det er noen brikker "p� veien"
	 *
	 * @param fraPos brikkens fra-posisjon
	 * @param tilPos brikkens til-posisjon
	 * @param brett  spillbrettet
	 * @return true hvis det oppst�r kollisjoner.
	 */
	private static boolean kollisjonsSjekk(String fraPos, String tilPos, Brett brett)
	{
		String[] diff = ruterMellom(fraPos, tilPos);
		if(diff==null)
			return false;
		for(String mellomPos : diff){
			if(brett.getBrikke(mellomPos)!=null) // Hvis feltet ikke er null inneholder det en brikke.
				return true;
		}
		return false;
	}

	/**
	 * Bestemmer retningen flyttingen g�r i
	 *
	 * @param fraPos brikkens fra-posisjon
	 * @param tilPos brikkens til-posisjon
	 * @param farge  brikkens farge (trengs for � finne ut om man beveger seg fremover eller bakover)
	 * @return Returnerer hvilken retning bevegelsen vil v�re - (SE enumen Retning)
	 */
	private static Retning finnRetning(String fraPos, String tilPos, Farge farge)
	{
		int[] koordFra = Koordinater.til_koordinater(fraPos);
		int[] koordTil = Koordinater.til_koordinater(tilPos);
		int[] diff = differanse(koordFra, koordTil);
		// Frem og tilbake
		if (diff[0] == 0 && diff[1] != 0) {
			if (diff[1] > 0) {
				return (farge == Farge.HVIT) ? Retning.FREM : Retning.BAKOVER;
			}
			if (diff[1] < 0) {
				return (farge == Farge.HVIT) ? Retning.BAKOVER : Retning.FREM;
			}
		}
		// Sidelengs
		if (diff[0] != 0 && diff[1] == 0) {
			return Retning.SIDELENGS;
		}
		// Diagonalt - TODO: fikse- ikke helt korrekt.
		if (Math.abs(diff[0])==Math.abs(diff[1])) {
			return Retning.DIAGONALT;
		}
		if(Math.abs(diff[0])==2&&Math.abs(diff[1])==1){
			return Retning.SPRINGER;
		}
		if(Math.abs(diff[0])==1&&Math.abs(diff[1])==2){
			return Retning.SPRINGER;
		}
		return null;
	}

	private static int[] differanse(int[] fra, int[] til)
	{
		if (fra.length != til.length)
			return null;
		return new int[]{til[0] - fra[0], til[1] - fra[1]};
	}

	public static String[] ruterMellom(String fra, String til)
	{
		int[] fraKoord = Koordinater.til_koordinater(fra);
		int[] tilKoord = Koordinater.til_koordinater(til);
		int[] diff = differanse(fraKoord, tilKoord);
		int[] tPos = new int[2];

		tPos[0] = fraKoord[0]; tPos[1] = fraKoord[1];
		int avstand = Math.max(Math.abs(diff[0]),Math.abs(diff[1]));
		if(avstand<=1) return null;

		String[] ruterMellom = new String[avstand-1];
		int stegKol = diff[0]/(avstand);
		int stegRad = diff[1]/(avstand);

		for(int i=0;i<avstand-1;++i){
			tPos[0]+=stegKol;tPos[1]+=stegRad; // Flytt til neste posisjon
			ruterMellom[i]=Koordinater.fra_koordinater(tPos);
		}
		return ruterMellom;
	}

}
