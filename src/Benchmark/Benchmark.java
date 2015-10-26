package Benchmark;

import Sjakk.AI.MiniMax;
import Sjakk.Brett.Brett;
import Sjakk.Regler.Farge;
import javafx.util.Pair;

import java.util.ArrayList;

/**
 * Created by Jo Øivind Gjernes on 25.10.2015.
 *
 * Teste hvor mange trekk AIen kan gjøre per sekund.
 */
public class Benchmark
{
	public static void main(String[] args)
	{
		ArrayList<Pair<Long, Integer>> liste = new ArrayList<>();

		Brett b = new Brett(0);
		MiniMax aiTest = new MiniMax(4, Farge.HVIT);
		MiniMax aiTest2 = new MiniMax(4, Farge.SVART);

		for (int i = 0; i < 20; ++i) {

			long startTime = System.currentTimeMillis();
			aiTest.nesteAiTrekk(b);
			long time = System.currentTimeMillis() - startTime;
			int trekk = aiTest.getAntallTrekk();
			liste.add(new Pair<Long, Integer>(time, trekk));
			startTime = System.currentTimeMillis();
			aiTest2.nesteAiTrekk(b);
			time = System.currentTimeMillis() - startTime;
			trekk = aiTest2.getAntallTrekk();
			liste.add(new Pair<Long, Integer>(time, trekk));
		}
		visBenchmarkStatistikk(liste);
	}

	public static void visBenchmarkStatistikk(ArrayList<Pair<Long, Integer>> stats)
	{
		int avgTrekk = 0;
		long avgTime = 0;
		for (Pair<Long, Integer> par : stats) {
			avgTime += par.getKey();
			avgTrekk += par.getValue();
		}
		long avgTimePerTrekk = avgTrekk / (avgTime);
		avgTimePerTrekk *= 1000;
		avgTime = avgTime / (stats.size());
		avgTrekk = avgTrekk / (stats.size());
		System.out.println("Utførelsen tok i snitt: " + avgTime + " for " + avgTrekk + " og gjorde " + avgTimePerTrekk + " per sek");
	}
}
