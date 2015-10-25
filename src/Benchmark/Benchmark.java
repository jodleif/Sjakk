package Benchmark;

import Sjakk.AI.MiniMax;
import Sjakk.Brett.Brett;
import Sjakk.Regler.Farge;
import Sjakk.Regler.Koordinater;
import javafx.util.Pair;

import java.util.ArrayList;

/**
 * Created by Jo Øivind Gjernes on 25.10.2015.
 */
public class Benchmark
{
	public static void main(String[] args)
	{
		ArrayList<Pair<Long, Integer>> liste = new ArrayList<>();
		for (int i = 0; i < 10; ++i) {
			Brett b = new Brett(0);
			MiniMax aiTest = new MiniMax(3, Farge.HVIT);
			long startTime = System.currentTimeMillis();
			aiTest.nesteAiTrekk(b);
			int trekk = aiTest.getAntallTrekk();
			long time = System.currentTimeMillis() - startTime;
			liste.add(new Pair<Long, Integer>(time, trekk));
			Koordinater.resetTables();
		}
		displayBenchmarkStats(liste);
	}

	public static void displayBenchmarkStats(ArrayList<Pair<Long, Integer>> stats)
	{
		int avgTrekk = 0;
		long avgTime = 0;
		for (Pair<Long, Integer> par : stats) {
			avgTime += par.getKey();
			avgTrekk += par.getValue();
		}
		long avgTimePerTrekk = avgTrekk * 1000 / (avgTime);
		avgTime = avgTime / (stats.size());
		avgTrekk = avgTrekk / (stats.size());
		System.out.println("Utførelsen tok i snitt: " + avgTime + " for " + avgTrekk + " og gjorde " + avgTimePerTrekk + " per sek");
	}
}
