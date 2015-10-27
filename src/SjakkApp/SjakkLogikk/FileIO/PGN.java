package SjakkApp.SjakkLogikk.FileIO;

/**
 * Created by Jo Øivind Gjernes on 21.10.2015.
 * <p>
 * Forsøk på å lese inn PGN filer for å spille av!
 *
 * Tankegang:
 * 1.Leser inn fil og utfører alle kommandoer på et spesifikt brett
 * 2.Lagre historikken for brettet
 * 3.Sette historikken tilbake til utgangspunktet
 * 4.Så kan man spille av historikken trekk for trekk
 *
 */
public class PGN
{
/*
	private static String fjernEkstraInformasjon(String pgnTekst){
		int start = pgnTekst.indexOf("1. ");
		return pgnTekst.substring(start);
	}

	public static ArrayList<Trekk> pgnListeTilTrekk(ArrayList<String> pgnTrekk){
		boolean hvit = true;
		ArrayList<Trekk> trekkListe = new ArrayList<>();
		Brett brett = new Brett(0);

		for(String trekk : pgnTrekk){
			pgnOrdTilTrekk(trekk, brett, (hvit)?Farge.HVIT:Farge.SVART);
			hvit = !hvit;
			trekkListe.add(brett.seSisteTrekk());
		}
		brett.lagreTrekk();
		trekkListe.add(brett.seSisteTrekk());
		return trekkListe;
	}


	public static Brett pgnListeTilBrett(ArrayList<String> pgnTrekk){
		boolean hvit = true;
		Brett brett = new Brett(0);

		for(String trekk : pgnTrekk){
			pgnOrdTilTrekk(trekk, brett, (hvit)?Farge.HVIT:Farge.SVART);
			hvit = !hvit;
		}
		brett.lagreTrekk();
		brett.spillAvNesteTrekk();
		return brett;
	}


	public static void pgnOrdTilTrekk(String pgnTrekk, Brett brett, Farge farge) {
		try {
			String[] brikkeInfo = hentInfoFraPGNtrekk(pgnTrekk); // [0] brikketype, [1] sluttPos
			if(brikkeInfo.length == 2) {
				String startPos = finnBrikkeStartPos(brikkeInfo[0], brikkeInfo[1], brett, farge);
				brett.flyttBrikke(startPos, brikkeInfo[1]);
			} else if (brikkeInfo.length == 3) {
				String startPos = finnBrikkeStartPos(brikkeInfo[0], brikkeInfo[1], brikkeInfo[2], brett, farge);
				brett.flyttBrikke(startPos, brikkeInfo[2]);
			} else {
				if(brikkeInfo[0].equals("KINGSIDE")){
					brett.Rokker(true,farge);
				} else {
					brett.Rokker(false, farge);
				}
			}
		} catch (Exception e) {
			System.err.println("Tolking feilet!: " + e.getMessage());
		}
	}

	private static Brikke finnFjernetBrikke(String pos, Brett brett){
		return brett.getBrikke(pos);
	}

	private static String[] hentInfoFraPGNtrekk(String pgnTrekk){
		String brikkeType;
		String sluttPos;
		//Spesielt tilfelle
		if(pgnTrekk.equals("O-O")){
			return new String[]{"KINGSIDE"};
		} else if (pgnTrekk.equals("O-O-O")) {
			return new String[]{"QUEENSIDE"};
		}
		if(pgnTrekk.charAt(pgnTrekk.length()-1)=='+'){
			pgnTrekk = pgnTrekk.substring(0,pgnTrekk.length()-1);
		}
		//SjakkAngrep BRIKKETYPE x rute
		if(pgnTrekk.charAt(1)=='x') {
			if(Character.isUpperCase(pgnTrekk.charAt(0))) {
				brikkeType = String.valueOf(pgnTrekk.charAt(0));
				pgnTrekk = brikkeType + pgnTrekk.substring(2,4);
			} else {
				brikkeType = "B";
				String brikkeKol = String.valueOf(pgnTrekk.charAt(0));
				sluttPos=pgnTrekk.substring(2,4);
				return new String[]{brikkeType,brikkeKol,sluttPos};
			}
		}
		//Vanlige trekk format: BRIKKETYPE rute f.eks Ne5, eler kun rute = bonde til rute ex: e5
		if (pgnTrekk.length()==2){ // Ingen info om brikketype
			brikkeType = "B"; // Bonde!
			sluttPos = pgnTrekk;
		} else if (pgnTrekk.length()==3) {
			brikkeType = String.valueOf(SpråkKonvertering.KonverterFraPGN(pgnTrekk.charAt(0)));
			sluttPos = pgnTrekk.substring(1, 3);
		} else if (pgnTrekk.length()==4) {
			brikkeType = String.valueOf(SpråkKonvertering.KonverterFraPGN(pgnTrekk.charAt(0)));
			String brikkeKol = String.valueOf(pgnTrekk.charAt(1));
			sluttPos = pgnTrekk.substring(2,4);
			return new String[] {brikkeType, brikkeKol, sluttPos};
		} else {
			throw new IllegalArgumentException("ugyldig pgn trekk!");
		}
		return new String[] {brikkeType, sluttPos};
	}

	private static String finnBrikkeStartPos(String brikkeType, String sluttPos, Brett brett, Farge farge) throws IllegalArgumentException {
		return finnBrikkeStartPos(brikkeType, null, sluttPos, brett, farge);
	}

	private static String finnBrikkeStartPos(String brikkeType, String startKol, String sluttPos, Brett brett, Farge farge) throws IllegalArgumentException {
		String startPos = new String();

		Brikke[] brikker = brett.getAlleBrikker();
		for(Brikke b : brikker){
			if(startKol==null || b.getRuteNavn().charAt(0)==startKol.charAt(0)) {
				if (b.brikkenavn().equals(brikkeType) && b.getFarge() == farge) {
					if (b.erLovligTrekk(sluttPos)) {
						startPos = b.getRuteNavn();
					}
				}
			}
		}
		if ((startPos.length() == 0)) throw new IllegalArgumentException("[finnBrikkeStartPos] fant ikke startposisjon for brikke: " + brikkeType + " til " + sluttPos);
		return startPos;
	}

	private static class SpråkKonvertering
	{
		public static char KonverterBrikkeNavn(char tilPGN)
		{
			switch (tilPGN) {
				case 'B':
					return 'P'; // Peasant
				case 'D':
					return 'Q'; // Queen
				case 'K':
					return tilPGN; // King
				case 'S':
					return 'N'; // kNight
				case 'L':
					return 'B'; // Bishop
				case 'T':
					return 'R'; // Rook
				default:
					return ' '; // INVALID
			}
		}

		public static char KonverterFraPGN(char fraPGN)
		{
			switch (fraPGN) {
				case 'P':
					return 'B'; // Bonde
				case 'Q':
					return 'D'; // Dronning
				case 'K':
					return fraPGN; // Konge
				case 'N':
					return 'S'; // Springer / hest
				case 'R':
					return 'T'; // Tårn
				case 'B':
					return 'L'; // Løper
				default:
					return ' ';
			}
		}
	}*/
}
