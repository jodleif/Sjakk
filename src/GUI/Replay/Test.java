package GUI.Replay;

import Sjakk.Brett.Brett;
import Sjakk.Brett.Historikk.Trekk;
import Sjakk.FileIO.PGN;

import java.util.ArrayList;

/**
 * Created by Jo Øivind Gjernes on 22.10.2015.
 *
 * Jepp.
 */
public class Test
{
	public static Brett getSjakkBrettMedHistorikk()
	{
		ArrayList<String> trekkListe = new ArrayList<String>();
		trekkListe.add("e4");
		trekkListe.add("e5");
		trekkListe.add("Nf3");
		trekkListe.add("Nc6");
		trekkListe.add("Bb3");
		trekkListe.add("a6");
		trekkListe.add("Ba4");
		trekkListe.add("Nf6");
		trekkListe.add("O-O");
		trekkListe.add("Be7");
		trekkListe.add("Re1");
		trekkListe.add("b5");
		trekkListe.add("Bb3");
		trekkListe.add("d6");
		trekkListe.add("c3");
		trekkListe.add("O-O");
		trekkListe.add("h3");
		trekkListe.add("Nb8");
		trekkListe.add("d4");
		trekkListe.add("Nbd7");
		trekkListe.add("c4");
		trekkListe.add("c6");
		trekkListe.add("cxb5");
		trekkListe.add("axb5");
		trekkListe.add("Nc3");
		trekkListe.add("Bb7");
		trekkListe.add("Bg5");
		trekkListe.add("b4");
		trekkListe.add("Nb1");
		trekkListe.add("h6");
		trekkListe.add("Bh4");
		trekkListe.add("c5");
		trekkListe.add("dxe5");
		trekkListe.add("Nxe4");
		trekkListe.add("Bxe7");
		trekkListe.add("Qxe7");
		return PGN.pgnListeTilBrett(trekkListe);
	}
}
