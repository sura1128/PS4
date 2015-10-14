
// Copy paste this Java Template and save it as "OutForAWalk.java"
import java.util.*;
import java.io.*;

// write your matric number here: A0102800A
// write your name here: Suranjana Sengupta
// write list of collaborators here:
// year 2015 hash code: JESg5svjYpIsmHmIjabX (do NOT delete this line)

class OutForAWalk {
	private int V; // number of vertices in the graph (number of rooms in the
					// building)
	private Vector<Vector<IntegerPair>> AdjList; // the weighted graph (the
													// building), effort rating
													// of each corridor is
													// stored here too
	private List<Integer> weightList;

	public OutForAWalk() {
		weightList = new ArrayList<Integer>();
	}

	void PreProcess() {

	}

	int Query(int source, int destination) {
		int ans = 0;

		Prim(source, destination);

		Collections.sort(weightList);
		if (!weightList.isEmpty()) {
			ans = weightList.get(weightList.size() - 1);
		}

		weightList.clear();

		return ans;
	}

	void Prim(int source, int destination) {
		
		boolean T[] = new boolean[V];
		
		T[source] = true; // visited vector
		Vector<IntegerPair> temp = AdjList.get(source);

		PriorityQueue<IntegerPair> pq = new PriorityQueue<IntegerPair>();

		for (int i = 0; i < temp.size(); i++) { // populate PQ with edges of
			pq.add(temp.get(i));
		}

		while (!pq.isEmpty()) {
			IntegerPair u = pq.remove();
			if (T[u.first()] == false) {
				T[u.first()] = true;
				weightList.add(u.second());
				
				List<IntegerPair> neighbours = AdjList.get(u.first()); 
				for (int i = 0; i < AdjList.get(u.first()).size(); i++) {					
					if (T[neighbours.get(i).first()] == false) {
						pq.add(neighbours.get(i));
					}

				}
			}
			if (u.first() == destination)
				break;

		}

	}


	void run() throws Exception {
		// do not alter this method
		IntegerScanner sc = new IntegerScanner(System.in);
		PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		int TC = sc.nextInt(); // there will be several test cases
		while (TC-- > 0) {
			V = sc.nextInt();

			// clear the graph and read in a new graph as Adjacency List
			AdjList = new Vector<Vector<IntegerPair>>();
			for (int i = 0; i < V; i++) {
				AdjList.add(new Vector<IntegerPair>());

				int k = sc.nextInt();
				while (k-- > 0) {
					int j = sc.nextInt(), w = sc.nextInt();
					AdjList.get(i).add(new IntegerPair(j, w)); // edge
																// (corridor)
																// weight
																// (effort
																// rating) is
																// stored here
				}
			}

			PreProcess(); // you may want to use this function or leave it empty
							// if you do not need it

			int Q = sc.nextInt();
			while (Q-- > 0)
				pr.println(Query(sc.nextInt(), sc.nextInt()));
			pr.println(); // separate the answer between two different graphs
		}

		pr.close();
	}

	public static void main(String[] args) throws Exception {
		// do not alter this method
		OutForAWalk ps4 = new OutForAWalk();
		ps4.run();
	}
}

class IntegerScanner { // coded by Ian Leow, using any other I/O method is not
						// recommended
	BufferedInputStream bis;

	IntegerScanner(InputStream is) {
		bis = new BufferedInputStream(is, 1000000);
	}

	public int nextInt() {
		int result = 0;
		try {
			int cur = bis.read();
			if (cur == -1)
				return -1;

			while ((cur < 48 || cur > 57) && cur != 45) {
				cur = bis.read();
			}

			boolean negate = false;
			if (cur == 45) {
				negate = true;
				cur = bis.read();
			}

			while (cur >= 48 && cur <= 57) {
				result = result * 10 + (cur - 48);
				cur = bis.read();
			}

			if (negate) {
				return -result;
			}
			return result;
		} catch (IOException ioe) {
			return -1;
		}
	}
}

class IntegerPair implements Comparable<IntegerPair> {
	Integer _first, _second;

	public IntegerPair(Integer f, Integer s) {
		_first = f;
		_second = s;
	}

	public int compareTo(IntegerPair o) {
		if (!this.second().equals(o.second()))
			return this.second() - o.second();
		else
			return this.first() - o.first();
	}

	Integer first() {
		return _first;
	}

	Integer second() {
		return _second;
	}

}