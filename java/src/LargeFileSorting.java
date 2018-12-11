/**
 * Given a very large text file, LargeFileSorting creates a file:
 *      1. distinct words from the original file
 *      2. sorted in ascending order
 *
 * Assumptions:
 * - Case Sensitive
 * - Does not filter words
 * - Has at least 1000 bytes of available memory for partitioning
 */
public class LargeFileSorting {

    private static String DEFAULT_INPUT = "input.txt";
    private static String OUTPUT = "out-%s";


    public static void main(String[] args){
        String input = args.length == 1 ? args[0] : DEFAULT_INPUT;
        FileSplitter fileSplitter = new FileSplitter(input);
        fileSplitter.splitFile();
        PartitionMerger partitionMerger = new PartitionMerger(
                fileSplitter.getPartitionNames(), String.format(OUTPUT, input));
        partitionMerger.mergeSortedFiles();
    }
}
