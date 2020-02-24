package hashsort;
/**
 * Recursive hash sorting algorithm. 
 * Current implementation only sort matrices if all values are filled within a range. 
 * 		EX. test uses a matrices containing all values 1-16
 * 
 * @author Michael Link
 * @version 1.0.1
 */
public class RecHashSort {

	public static void main(String[] args) {
		//load matrices
		Integer[][] matrix = new Integer[4][4];
		matrix[0][0] = 4;
		matrix[0][1] = 1;
		matrix[0][2] = 3;
		matrix[0][3] = 10;
		matrix[1][0] = 9;
		matrix[1][1] = 5;
		matrix[1][2] = 8;
		matrix[1][3] = 11;
		matrix[2][0] = 14;
		matrix[2][1] = 12;
		matrix[2][2] = 6;
		matrix[2][3] = 7;
		matrix[3][0] = 2;
		matrix[3][1] = 13;
		matrix[3][2] = 16;
		matrix[3][3] = 15;
		
		System.out.println("Pre-sorted matrices: \n");
		//print
		printMatrix(matrix);
		
		System.out.println("Pre-sorted matrices: \n");
		//print
		printMatrix(recHashSort(matrix));
	}
	
	/**
	 * METHOD -> recHashSort
	 * ----------------------------------
	 * Recursive hash sort algorithm.
	 * 
	 * @param matrix: values to sort
	 * @return Integer[][]: integer arr containing sorted values
	 */
	static Integer[][] recHashSort(Integer[][] matrix) {
		//initialize all values and overhead
		Integer mappingConst = calcMappingConst(matrix);
		CartesianPair curHash;
		CartesianPair curEntry = new CartesianPair();
		curEntry.r = 0;
		curEntry.c = 0;
		
		return recHashSort(matrix, mappingConst, curEntry);
	}
	
	/**
	 * METHOD -> recHashSort
	 * ----------------------------------
	 * Recursive hash sort algorithm.
	 * 
	 * @param matrix: matrix to sort
	 * @param mappingConst: mapping constant for super hash
	 * @param curEntry: current position in matrix
	 * @return Integer[][]: integer arr containing sorted values
	 */
	static Integer[][] recHashSort(Integer[][] matrix, Integer mappingConst, 
			CartesianPair curEntry) {
		
		Integer curVal = matrix[curEntry.r][curEntry.c];
		
		CartesianPair curHash = superHash(curVal, mappingConst);
		
		//check for halt and push forwards
		if(curEntry.r == curHash.r && curEntry.c == curHash.c) {
			//halt on final value then conclude 
			if(curEntry.r == (matrix.length-1) && curEntry.c == (matrix.length-1)) {
				return matrix;
			}
			//push algorithm forwards
			if(curEntry.c == (matrix.length-1)) {
				curEntry.c = 0;
				curEntry.r++;
			}else {
				curEntry.c++;
			}
		}
		
		//swap value
		curVal = matrix[curHash.r][curHash.c];
		matrix[curHash.r][curHash.c] = matrix[curEntry.r][curEntry.c];
		matrix[curEntry.r][curEntry.c] = curVal;
		
		//conserve memory
		curVal = null;
		curHash = null;
		System.gc();
		
		return recHashSort(matrix, mappingConst, curEntry);
	}
	
	/**
	 * METHOD -> superHash
	 * ----------------------------------
	 * Returns the row and col of the correct hash position for passed integer element.
	 * Integer div corresponds to row
	 * Integer modulus corresponds to col
	 * @param value: values to hash
	 * @return CartesianPair: row and col to swap
	 */
	static CartesianPair superHash(int value, Integer mappingConst) {
		int div = (value - 1) / mappingConst;
		int mod = (value - 1) % mappingConst;
		
		CartesianPair curSwap = new CartesianPair();
		curSwap.r = div;
		curSwap.c = mod;
		
		return curSwap;
	}
	
	/**
	 * METHOD -> calcMappingConst
	 * ----------------------------------
	 * Method that calculates the mapping constant to be used by the super hash function.
	 * @param elements: list of elems-pre-sorted
	 * @return Integer: the mapping const
	 */
	static Integer calcMappingConst(Integer[][] matrix) {
		Integer mappingConst;
		Integer high = matrix[0][0];
		Integer low = matrix[0][0];
		double tempValue;
		
		//calculate high and low of range
		for(Integer[] arr: matrix) {
			for(Integer elem: arr) {
				if(elem > high) {
					high = elem;
				}
				if(elem < low) {
					low = elem;
				}
			}
		}
		
		tempValue = (high - low) + 1;
		tempValue = Math.sqrt(tempValue);
		
		//autobox
		mappingConst = (int) Math.round(tempValue);
		
		return mappingConst;
	}
	
	/**
	 * METHOD -> printMatrix
	 * ----------------------------------
	 * Helper method that prints the matrices.
	 * @param matrix: the matrix to print
	 */
	static void printMatrix(Integer[][] matrix) {
		for(Integer[] arr: matrix) {
			for(Integer elem: arr) {
				System.out.print(elem + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
}

/**
 * CLASS -> CartesianPair
 * ----------------------------------
 * Simple DS used to pass cartesian points through functions.
 * {@value} r int: the row
 * {@value} c int: the column
 */
class CartesianPair {
	//row and col values
	public int r;
	public int c;
}
