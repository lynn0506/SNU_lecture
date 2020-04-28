public class Matrix {
		private int rowNum;
		private int colNum;
		double elem[][];
		
		public Matrix(int rowNum, int colNum) {
			this.rowNum = rowNum;
			this.colNum = colNum;
			elem = new double[rowNum][colNum];
		}
		
		public void add(int rowN, int colN, double input) {
			elem[rowN][colN] = input;
		}
		
		public int getRowNum() {
			return rowNum;
		}
		
		public int getColNum() {
			return colNum;
		}
		
		public void multiply(Matrix elem2) {
			int resultRow = rowNum;
			int resultCol = elem2.getColNum();
			double[][] result = new double[resultRow][resultCol];
			double Product = 0;
			
			for(int i = 0; i<resultRow; i++) {
				for(int j = 0; j<colNum; j++) {
					for(int k = 0; k<resultCol; k++) {
						result[i][k] += (double)(elem[i][j]*elem2.elem[j][k]);
					}
				}
			}
			for(int i = 0; i<resultRow; i++) {
				for(int j = 0; j<resultCol; j++) {
					System.out.print(result[i][j] + " ");
				}
				System.out.println();
			}
		}
	}


