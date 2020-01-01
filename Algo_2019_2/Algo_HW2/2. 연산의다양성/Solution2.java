import java.util.StringTokenizer;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.*;
/**
	name : Jung, Eunjoo
	student_ID : 2014-19498
	department : German Education
**/
/*
   1. 아래와 같은 명령어를 입력하면 컴파일이 이루어져야 하며, Solution4 라는 이름의 클래스가 생성되어야 채점이 이루어집니다.
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

	/*
		** 주의사항
		정답의 숫자가 매우 크기 때문에 답안은 반드시 int 대신 long 타입을 사용합니다.
		그렇지 않으면 overflow가 발생해서 0점 처리가 됩니다.
		Answer[0]을 a의 개수, Answer[1]을 b의 개수, Answer[2]를 c의 개수라고 가정했습니다.
	*/
    static int n;                           // 문자열 길이
    static String s; 						// 문자열
	static long a_count, b_count, c_count;
	static int x, y;
	static Node pi, pj;
	static long a_ans, b_ans, c_ans;
	static long[] Answer = new long[3];     // 정답
	static LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();

	public static void main(String[] args) throws Exception {
		/*
		   동일 폴더 내의 input2.txt 로부터 데이터를 읽어옵니다.
		   또한 동일 폴더 내의 output2.txt 로 정답을 출력합니다.
		 */
		BufferedReader br = new BufferedReader(new FileReader("input2.txt"));
		StringTokenizer stk;
		PrintWriter pw = new PrintWriter("output2.txt");
		map.put("aa","b");
		map.put("ba","c");
		map.put("ca","a");
		map.put("ab","b");
		map.put("bb","b");
		map.put("cb","c");
		map.put("ac","a");
		map.put("bc","a");
		map.put("cc","c");

		/*
		   10개의 테스트 케이스가 주어지므로, 각각을 처리합니다.
		 */
		for (int test_case = 1; test_case <= 10; test_case++) {
			/*
			   각 테스트 케이스를 파일에서 읽어옵니다.
               첫 번째 행에 쓰여진 문자열의 길이를 n에 읽어들입니다.
               그 다음 행에 쓰여진 문자열을 s에 한번에 읽어들입니다.
			 */
			stk = new StringTokenizer(br.readLine());
			n = Integer.parseInt(stk.nextToken());
			s = br.readLine();

			counting(); /** COUNTING **/
			/////////////////////////////////////////////////////////////////////////////////////////////
			/*
			   이 부분에서 여러분의 알고리즘이 수행됩니다.
			   정답을 구하기 위해 주어진 문자열 s를 여러분이 원하시는 대로 가공하셔도 좋습니다.
			   문제의 답을 계산하여 그 값을 Answer(long 타입!!)에 저장하는 것을 가정하였습니다.
			 */
			/////////////////////////////////////////////////////////////////////////////////////////////
			Answer[0] = a_ans;  // a 의 갯수
			Answer[1] = b_ans;  // b 의 갯수
			Answer[2] = c_ans;  // c 의 갯수

			// output2.txt로 답안을 출력합니다.
			pw.println("#" + test_case + " " + Answer[0] + " " + Answer[1] + " " + Answer[2]);
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

	public static void counting() {
		/**
		    total Theta(n^3) time complexity.
		 	1) outer loop: n-1 time
		 	2) first inner loop depends on the first outer loop: n-1 -> n-2 -> n-3 -> ... 1
		 	3) second inner loop iterates same as outer loop ( y-x time == i): n-1
		 **/
		Node[][] m = new Node[n+1][n+1];
		for(int i = 1; i<=n; i++) { /** Theta n (linear time) **/
			m[i][i] = new Node();
			m[i][i].counter(1, s.substring(i-1, i));
		}
		for(int i = 1; i<=n-1; i++) { /** 1) (n-1)time, => theta n **/
			for(x = 1; x<=n-i; x++) { /** 2) theta n **/
				y = i+x; /** y-x == i it means second inner loop depends on first loop **/
				m[x][y] = new Node();
				for(int k = x; k < y; k++) { /** 3) theta n **/
					pi = m[x][k];
					pj = m[k+1][y];

					if (pi.a_count != 0) {
						if (pj.a_count != 0)
							m[x][y].counter(pi.a_count*pj.a_count, map.get("aa"));
						if (pj.b_count != 0)
							m[x][y].counter(pi.a_count*pj.b_count, map.get("ab"));
						if (pj.c_count != 0)
							m[x][y].counter(pi.a_count*pj.c_count, map.get("ac"));
					}
					if (pi.b_count != 0) {
						if (pj.a_count != 0)
							m[x][y].counter(pi.b_count*pj.a_count, map.get("ba"));
						if (pj.b_count != 0)
							m[x][y].counter(pi.b_count*pj.b_count, map.get("bb"));
						if (pj.c_count != 0)
							m[x][y].counter(pi.b_count*pj.c_count, map.get("ca"));
					}
					if (pi.c_count != 0) {
						if (pj.a_count != 0)
							m[x][y].counter(pi.c_count*pj.a_count, map.get("ca"));
						if (pj.b_count != 0)
							m[x][y].counter(pi.c_count*pj.b_count, map.get("cb"));
						if (pj.c_count != 0)
							m[x][y].counter(pi.c_count*pj.c_count, map.get("cc"));
					}
				}
			}
		}
		a_ans = m[1][n].a_count;
		b_ans = m[1][n].b_count;
		c_ans = m[1][n].c_count;
	}

	public static class Node {
		long a_count;
		long b_count;
		long c_count;

		public Node() {
			this.a_count = 0;
			this.b_count = 0;
			this.c_count = 0;
		}

		public Node(long a_count, long b_count, long c_count) {
			this.a_count = a_count;
			this.b_count = b_count;
			this.c_count = c_count;
		}

		public void counter(long preNum, String target) {
			if(target.equals("a")) a_count += preNum;
			else if(target.equals("b")) b_count += preNum;
			else c_count += preNum;
		}
	}
}