import java.util.StringTokenizer;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
// 2014-19498 정은주 독어교육과 

/*
   1. 아래와 같은 명령어를 입력하면 컴파일이 이루어져야 하며, Solution2 라는 이름의 클래스가 생성되어야 채점이 이루어집니다.
       javac Solution2.java -encoding UTF8

   2. 컴파일 후 아래와 같은 명령어를 입력했을 때 여러분의 프로그램이 정상적으로 출력파일 output2.txt 를 생성시켜야 채점이 이루어집니다.
       java Solution2

   - 제출하시는 소스코드의 인코딩이 UTF8 이어야 함에 유의 바랍니다.
   - 수행시간 측정을 위해 다음과 같이 time 명령어를 사용할 수 있습니다.
       time java Solution2
   - 일정 시간 초과시 프로그램을 강제 종료 시키기 위해 다음과 같이 timeout 명령어를 사용할 수 있습니다.
       timeout 0.5 java Solution2   // 0.5초 수행
       timeout 1 java Solution2     // 1초 수행
 */

class Solution2 {
	static final int MAX_N = 20000;
	static final int MAX_E = 80000;

	static int N, E, vertex = 1, x, y, total_weight;
	static Edge edge, rv_Edge, removed;
	static Edge[] key = new Edge[MAX_E];
	static int[] U = new int[MAX_E], V = new int[MAX_E], W = new int[MAX_E];
	static int Answer;
	static int[] parent;
	static HeapTree maxHeap = new HeapTree();

	public static void main(String[] args) throws Exception {
		/*
		   동일 폴더 내의 input2.txt 로부터 데이터를 읽어옵니다.
		   또한 동일 폴더 내의 output2.txt 로 정답을 출력합니다.
		 */
		BufferedReader br = new BufferedReader(new FileReader("input2.txt"));
		StringTokenizer stk;
		PrintWriter pw = new PrintWriter("output2.txt");

		/*
		   10개의 테스트 케이스가 주어지므로, 각각을 처리합니다.
		 */
		for (int test_case = 1; test_case <= 10; test_case++) {
			/*
			   각 테스트 케이스를 표준 입력에서 읽어옵니다.
			   먼저 정점의 개수와 간선의 개수를 각각 N, E에 읽어들입니다.
			   그리고 각 i번째 간선의 양 끝점의 번호를 U[i], V[i]에 읽어들이고, i번째 간선의 가중치를 W[i]에 읽어들입니다. (0 ≤ i ≤ E-1, 1 ≤ U[i] ≤ N, 1 ≤ V[i] ≤ N)
			 */
			stk = new StringTokenizer(br.readLine());
			N = Integer.parseInt(stk.nextToken()); 
			E = Integer.parseInt(stk.nextToken());
			stk = new StringTokenizer(br.readLine());
			maxHeap = new HeapTree();
			total_weight = 0;
			vertex = 0;
			for (int i = 0; i < E; i++) {
				U[i] = Integer.parseInt(stk.nextToken());
				V[i] = Integer.parseInt(stk.nextToken());
				W[i] = Integer.parseInt(stk.nextToken());
				edge = new Edge(U[i], V[i], W[i]); 
				maxHeap.insert(edge); // O(E) -> without initialization (just insert)
			}
			maxHeap.heapSort(E); // O(ElogE) = O(ElogV) heapsort
			parent = new int[N+1];
			
			// total Time Complexity : O(ElogV) 
			for(int i = 1; i<N+1; i++) {
				parent[i] = i; // theta(V)
			}
			while(vertex < N-1) { // O(Elog*V) with path compression find , union
				rv_Edge = maxHeap.remove(); //constant time
				if(find(parent, rv_Edge.src) != find(parent, rv_Edge.des)) {
					//** Union **/
					x = find(parent, rv_Edge.src);
					y = find(parent, rv_Edge.des);
					parent[x] = y;
					total_weight += rv_Edge.weight;
					vertex++;
				}
			}

			/////////////////////////////////////////////////////////////////////////////////////////////
			/*
			   이 부분에서 여러분의 알고리즘이 수행됩니다.
			   문제의 답을 계산하여 그 값을 Answer에 저장하는 것을 가정하였습니다.
			 */
			/////////////////////////////////////////////////////////////////////////////////////////////
			Answer = total_weight;
			// output2.txt로 답안을 출력합니다.
			pw.println("#" + test_case + " " + Answer);
			/*
			   아래 코드를 수행하지 않으면 여러분의 프로그램이 제한 시간 초과로 강제 종료 되었을 때,
			   출력한 내용이 실제로 파일에 기록되지 않을 수 있습니다.
			   따라서 안전을 위해 반드시 flush() 를 수행하시기 바랍니다.
			 */
			pw.flush();
		}
		br.close();
		pw.close();
	}

	public static int find(int parent[], int i) {
         if(parent[i] != i) parent[i] = find(parent, parent[i]);
         return parent[i]; //** path compression **//
	}

	public static class Edge implements Comparable<Edge> {
		int src;
		int des;
		int weight;

		public Edge(int src, int des, int weight) {
			this.src = src;
			this.des = des;
			this.weight = weight;
		}

		public Edge() {
			this.src = 0;
			this.des = 0;
			this.weight = 0;
		}

		@Override
		public int compareTo(Edge other) {
			return other.weight - weight;
		}
	}

	public static class HeapTree {
		Edge[] key;
		int index = 1;
		int outIdx = 1;
		int left, right, bigger;

		public HeapTree() {
			key = new Edge[MAX_E+1];
		}

		public void insert(Edge ret) {
			key[index++] = ret;
		}

		public void heapSort(int n) {
			//** O(nlogn) **//
			for(int i = n/2; i>=1; i--) 
				heapify(i, n);

			for(int i = n; i>=2; i--) { 
				Edge tmp = key[i];
				key[i] = key[1];
				key[1] = tmp;
				heapify(1, i-1);
			 } // n -1 percolating down in total 
			   // => O(nlogn) heapsort

		}
		public void heapify(int i , int n) {
			left = 2*i;
			right = 2*i+1;
			bigger = i;

			if(right <=n) { //constant
				if(key[left].compareTo(key[right]) >= 0)
					bigger = left;
				else bigger = right;
			}
			else if(left <=n) { //constant
				bigger = left;
			}
			else return;
			if(key[bigger].compareTo(key[i])>0) {
				Edge tmp = key[bigger];
				key[bigger] = key[i];
				key[i] = tmp;
				heapify(bigger, n);
			}
		}

		public Edge remove() {
			removed = key[outIdx++];
			return removed;
		}

	}
}

