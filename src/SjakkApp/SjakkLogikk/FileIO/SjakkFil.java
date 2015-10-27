package SjakkApp.SjakkLogikk.FileIO;

/**
 * Created by Jo Øivind Gjernes on 21.10.2015.
 *
 * Fil for å håndtere lagring og lasting av sjakkbrett
 */
public class SjakkFil
{/*
	private Brett brett;
	private String fil;
	private ArrayList<String> tekstBrikker;

	public SjakkFil(String filbane){
		fil = filbane;
	}

	public SjakkFil(Brett sjakkBrett){
		brett = sjakkBrett;
		konverterBrettTilTekst();
		printAlle();
	}

	private boolean konverterBrettTilTekst(){
		if(brett==null&&tekstBrikker!=null) // Sjekke at vi har et brett, samt at vi ikke overskriver annen informasjon!
			return false;
		tekstBrikker = new ArrayList<>();
		Brikke[] alle = brett.getAlleBrikker();
		for(Brikke b : alle){
			tekstBrikker.add(Konvertering.brikkeTilTekst(b));
		}
		return true;
	}

	private void printAlle(){
		tekstBrikker.forEach(System.out::println);
	}

	public Brett stringTilSjakkbrett(String fildata) {
		String[] brikker = fildata.split(";");
		Brett b = new Brett(0,true); // lag tomt brett
		for(String str : brikker){
			Konvertering.tekstTilBrikke(str, b);
		}
		return b;
	}

	public String getFilTekst(){
		String res = new String();
		if(tekstBrikker!=null){
			for(String str : tekstBrikker){
				res += str + ";";
			}
		}
		return res.substring(0,res.length()-1); // Fjern siste ;
	}
*/
	/**
	 * Hent sjakkbrett
	 * @return sjakkbrett
	 */
	/*
	public Brett getBrett(){
		return brett;
	}
	*/
}
