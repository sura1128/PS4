
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

	private boolean visited[];
	private Vector<Vector<IntegerPair>> DFSAdjList;
	private int[][] mat;

	private int[][] maxWeights;

	public OutForAWalk() {
		DFSAdjList = new Vector<Vector<IntegerPair>>();

	}

	void PreProcess() {

		if (V < 10) {
			for (int i = 0; i < 10; i++) {
				DFSAdjList.add(i, new Vector<IntegerPair>());
			} 
		} else {
			for (int i = 0; i < V; i++) {
				DFSAdjList.add(i, new Vector<IntegerPair>());
			} 
		}

		mat = new int[V][V];

		Prim(0);

		maxWeights = new int[10][V];

		for (int i = 0; i < 10; i++) {
			if (V > 10) {
				visited = new boolean[V];
			} else {
				visited = new boolean[10];
			}
			DFS(i, i, 0);

		}

	}

	int Query(int source, int destination) {
		int ans = 0;

		ans = maxWeights[source][destination];

		return ans;
	}

	void Prim(int source) {

		boolean T[] = new boolean[V]; // visited vector
		// T[source] = true;

		Vector<IntegerPair> neighbours = AdjList.get(source);
		PriorityQueue<IntegerTriple> pq = new PriorityQueue<IntegerTriple>();

		for (int i = 0; i < neighbours.size(); i++) {
			pq.add(new IntegerTriple(neighbours.get(i).second(), source, neighbours.get(i).first()));
		}

		while (!pq.isEmpty()) {

			IntegerTriple u = pq.remove();

			if (T[u.third()] == false) {
				neighbours = AdjList.get(u.third());

				for (int i = 0; i < neighbours.size(); i++) {
					if (T[neighbours.get(i).first()] == false) {
						pq.add(new IntegerTriple(neighbours.get(i).second(), u.third(), neighbours.get(i).first()));
					}
				}

				T[u.third()] = true;

				if (mat[u.third()][u.second()] == 0) {
					DFSAdjList.get(u.third()).add(new IntegerPair(u.second(), u.first()));
					DFSAdjList.get(u.second()).add(new IntegerPair(u.third(), u.first()));
					mat[u.third()][u.second()] = u.first();
					mat[u.second()][u.third()] = u.first();

				}

			}

		}

	}


	void DFS(int source, int u, int max) {

		visited[u] = true;

		Vector<IntegerPair> neighbours = DFSAdjList.get(u);

		for (int i = 0; i < neighbours.size(); i++) {
			int x = neighbours.get(i).first();
			int w = neighbours.get(i).second();
			int tempMax = max;

			if (visited[x] == false) {
				if (w > tempMax) {
					tempMax = w;
				}
				maxWeights[source][x] = tempMax;
				DFS(source, x, tempMax);
			}

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

class IntegerTriple implements Comparable<IntegerTriple> {
	Integer _first, _second, _third;

	public IntegerTriple(Integer f, Integer s, Integer t) {
		_first = f;
		_second = s;
		_third = t;
	}

	public int compareTo(IntegerTriple o) {
		if (!this.first().equals(o.first()))
			return this.first() - o.first();
		else if (!this.second().equals(o.second()))
			return this.second() - o.second();
		else
			return this.third() - o.third();
	}

	Integer first() {
		return _first;
	}

	Integer second() {
		return _second;
	}

	Integer third() {
		return _third;
	}
}
