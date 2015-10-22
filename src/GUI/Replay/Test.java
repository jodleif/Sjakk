package GUI.Replay;

import Sjakk.Brett.Brett;
import Sjakk.Brett.Historikk.Trekk;
import Sjakk.FileIO.PGN;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Jo Øivind Gjernes on 22.10.2015.
 * <p>
 * Jepp.
 */
public class Test
{
	/*
	1. e4 e5 2. Nf3 Nc6 3. Bb5 a6 {This opening is called the Ruy Lopez.}
	4. Ba4 Nf6 5. O-O Be7 6. Re1 b5 7. Bb3 d6 8. c3 O-O 9. h3 Nb8  10. d4 Nbd7
	11. c4 c6 12. cxb5 axb5 13. Nc3 Bb7 14. Bg5 b4 15. Nb1 h6 16. Bh4 c5 17. dxe5
	Nxe4 18. Bxe7 Qxe7 19. exd6 Qf6 20. Nbd2 Nxd6 21. Nc4 Nxc4 22. Bxc4 Nb6
	23. Ne5 Rae8 24. Bxf7+ Rxf7 25. Nxf7 Rxe1+ 26. Qxe1 Kxf7 27. Qe3 Qg5 28. Qxg5
	hxg5 29. b3 Ke6 30. a3 Kd6 31. axb4 cxb4 32. Ra5 Nd5 33. f3 Bc8 34. Kf2 Bf5
	35. Ra7 g6 36. Ra6+ Kc5 37. Ke1 Nf4 38. g3 Nxh3 39. Kd2 Kb5 40. Rd6 Kc5 41. Ra6
	Nf2 42. g4 Bd3 43. Re6 1/2-1/2
	 */
	public static Brett getSjakkBrettMedHistorikk()
	{
		String[] test = new String[]{
			"e4", "e5", "Nf3", "Nc6", "Bb5", "a6",
			"Ba4", "Nf6", "O-O", "Be7", "Re1", "b5", "Bb3", "d6", "c3", "O-O", "h3", "Nb8", "d4", "Nbd7"
			, "c4", "c6", "cxb5", "axb5", "Nc3", "Bb7", "Bg5", "b4", "Nb1", "h6", "Bh4", "c5", "dxe5"
			, "Nxe4", "Bxe7", "Qxe7", "exd6", "Qf6", "Nbd2", "Nxd6", "Nc4", "Nxc4", "Bxc4", "Nb6"
			, "Ne5", "Rae8", "Bxf7+", "Rxf7", "Nxf7", "Rxe1+", "Qxe1", "Kxf7", "Qe3", "Qg5", "Qxg5",
			"hxg5", "b3", "Ke6", "a3", "Kd6", "axb4", "cxb4", "Ra5", "Nd5", "f3", "Bc8", "Kf2", "Bf5",
			"Ra7", "g6", "Ra6+", "Kc5", "Ke1", "Nf4", "g3", "Nxh3", "Kd2", "Kb5", "Rd6", "Kc5", "Ra6",
			"Nf2", "g4", "Bd3",  "Re6" /* 1/2-1/2*/
		};
		ArrayList<String> trekkListe = new ArrayList<String>();
		Collections.addAll(trekkListe, test);
		/*trekkListe.add("e4");
		trekkListe.add("e5");
		trekkListe.add("Nf3");
		trekkListe.add("Nc6");
		trekkListe.add("Bb5");
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
		trekkListe.add("exd6");
		trekkListe.add("Qf6");
		trekkListe.add("Nbd2");
		trekkListe.add("Nxd6");
		trekkListe.add("Nc4");
		trekkListe.add("Nxc4");
		trekkListe.add("Bxc4");
		trekkListe.add("Nb6");
		trekkListe.add("Ne5");
		trekkListe.add("Rae8");
		trekkListe.add("Bxf7+");
		trekkListe.add("Rxf7");
		trekkListe.add("Nxf7");*/
		return PGN.pgnListeTilBrett(trekkListe);
	}
}
