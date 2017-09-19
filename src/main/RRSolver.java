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
		ArrayList<String> counter = new ArrayList();
		RRState robin = new RRState(n);
		Stack stapel = new Stack();
		stapel.push(robin);
		do {
			if (stapel.isEmpty() == true) {
//				return "Keine Lösung";
				for(Iterator i = counter.iterator(); i.hasNext();){
					System.out.println(i.next().toString());
				}
				return counter.size() + " Lösungen";
			} else {
				RRState state = (RRState)stapel.pop();
				if (state.isSolution() == true) {
//					return state.toString();
					boolean flag = false;
					for(Iterator i = counter.iterator(); i.hasNext();){
						if(i.next().toString().equals(state.toString())){
							flag = true;
						}
					}
					if(flag == false){
						counter.add(state.toString());
					}
				} else {
					for (Iterator i = state.expand().iterator(); i.hasNext(); ) {
						stapel.push((RRState)i.next());
					}
				}
			}
		} while (true);
	}
}
