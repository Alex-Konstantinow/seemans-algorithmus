package main;

import java.util.*;

/**
 * Diese Klasse sucht nach Loesungen des n-RR Spiels
 * @author Lixiang
 */
public class RRSolver {

	public static void main(String[] args) {

		/** Gerade Zahl groesser 1. */
		int size = 12;

		try {
			System.out.print("Solution: ");
			System.out.println(solve(size));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sucht nach einer Loesung fuer n-Robinson-Roulette mit Hilfe einer
	 * Tiefensuche und gibt diese als String zurueck
	 */
	private static String solve(int n) throws Exception {
		/*
		 * Hier soll eure Loesung implementiert werden. 
		 * Wichtige Methoden der Klasse RRState: 
		 * 		RRState(size) - Konstruktor fuer den Startzustand.
		 * 		isSolution()  - Gibt true zurueck, falls das Spiel geloest wurde.
		 * 		toString()    - Gibt bisherige Zuege als String zurueck.
		 * 		expand()      - Gibt alle moeglichen Folgezustaende als Liste zurueck.
		 * 
		 * Tipp: Haltet euch eng an den in der Vorlesung vorgestellten Algorithmus
		 * und waehlt eine geeignete Datenstruktur fuer die ToDo-Liste.
		 */
		RRState robin = new RRState(n);

		//	Stack stapel;
//		stapel = new Stack();
		ArrayList<RRState> queue = new ArrayList();
		do {
			if (queue.isEmpty() == true) {
				return "Keine Lösung";
			} else {
				RRState state = queue.get(queue.size());
				queue.remove(queue.size());
				if (state.isSolution() == true) {
					return state.toString();
				} else {
					for (Iterator i = state.expand().iterator(); i.hasNext(); ) {
						queue.add((RRState) i.next());
					}
				}
			}
		} while (true);
	}
}
