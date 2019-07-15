package cpsc433;

// A class to quickly sort an array of Nodes by score
// This was implemented using the 'Quick Sort' algorithm
public final class NodeQuickSort{
	private NodeQuickSort(){
		int num = 0;
	}

	/**
	 * Takes in an array of Nodes and sorts them from lowest score to highest score
	 *
	 * @param	nArray	Array of Nodes to be sorted
	 * @param	low		The lower index for the array to be partitioned at
	 * @param	high	The upper index for the array to be partitioned at
	 */
	public static void sort(Node[] nArray, int low, int high){
		if (low < high){
			int p = partition(nArray, low, high);
			sort(nArray, low, p-1);
			sort(nArray, p + 1, high);
		}
	}

	/**
	 * Creates a pivot element in an array and puts everything less than 
	 * or equal to the pivot on one side and everything else on the other
	 *
	 * @param	nArray	The array to perform the partition in
	 * @param	low 	The lower index for the array to be partitioned at
	 * @param	high	The upper index fot the array to be partitioned at
	 */
	private static int partition(Node[] nArray, int low, int high){
		Node pivot = nArray[high];
		int i = low;
		Node temp;

		for(int j = low; j <= (high - 1); j++){

			if (nArray[j].score <= pivot.score){
				temp = nArray[i];
				nArray[i] = nArray[j];
				nArray[j] = temp;

				i++;
			}
		}
		temp = nArray[i];
		nArray[i] = nArray[high];
		nArray[high] = temp;

		return i;
	}
}

