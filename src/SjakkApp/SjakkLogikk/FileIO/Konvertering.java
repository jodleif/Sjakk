package SjakkApp.SjakkLogikk.FileIO;

/**
 * Created by Jo Øivind Gjernes on 21.10.2015.
 *
 * Konvertere fra "tekstformat" til "brikkeformat"
 */
public class Konvertering
{
	/*
	public static String brikkeTilTekst(Brikke br){
		String tekst=br.getRuteNavn();
		tekst+=br.brikkenavn();
		tekst+=fargeTilTekst(br.getFarge());
		return tekst;
	}

	public static Brikke tekstTilBrikke(String enBrikke, Brett spillBrett){
		if(enBrikke.length()!=4) throw new IllegalArgumentException("[tekstTilBrikke] Ugyldig input");
		String posisjon = enBrikke.substring(0,2);
		char brikketype = enBrikke.charAt(2);
		Farge farge = tekstTilFarge(enBrikke.charAt(3));

		Brikke tmp = BrikkeFabrikk.bygg(brikketype, farge, posisjon, spillBrett); // Generer brikke
		spillBrett.setBrikke(tmp.getRuteNavn(), tmp); // Fest brikken på brettet
		return tmp;
	}


	private static char fargeTilTekst(Farge farge){
		return (farge==Farge.HVIT) ? 'h' : 's';
	}

	private static Farge tekstTilFarge(char farge){
		return (farge=='h') ? Farge.HVIT : Farge.SVART;
	}

	private static class BrikkeFabrikk {
		public static Brikke bygg(char typeBrikke, Farge farge, String posisjon, Brett spillBrett){
			switch(typeBrikke){
				case 'B':
					return new Bonde(spillBrett,posisjon,farge);
				case 'K':
					return new Konge(spillBrett,posisjon,farge);
				case 'D':
					return new Dronning(spillBrett,posisjon,farge);
				case 'S':
					return new Springer(spillBrett,posisjon,farge);
				case 'L':
					return new Løper(spillBrett,posisjon,farge);
				case 'T':
					return new Tårn(spillBrett, posisjon,farge);
				default:
					return null;
			}
		}
	}
	*/

}
