import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Queue;

/**
 * PartitionMerger merges all partitions into a final sorted output files
 */
public class PartitionMerger {

    private static String MERGED_NAME = "merged%d";

    private Queue<String> partitionNames;
    private String outputName;

    /**
     * Creates instance of PartitionMerger
     *
     * @param partitionNames the queue for all the partition names
     * @param outputName the file name of the output
     */
    public PartitionMerger(Queue<String> partitionNames, String outputName){
        this.partitionNames = partitionNames;
        this.outputName = outputName;
    }

    /**
     * Merges are all sorted partitioned files. Pops values fr
     */
    public void mergeSortedFiles(){
        int counter = 0;
        while (partitionNames.size() > 1){
            String firstFile = partitionNames.remove();
            String secondFile = partitionNames.remove();
            String mergedName = String.format(MERGED_NAME, counter);
            mergeTwoFiles(new File(firstFile), new File(secondFile), new File(mergedName));
            partitionNames.add(mergedName);
            counter++;
        }
        writeMergedFileToOutputFile(new File(partitionNames.remove()));
    }

    /**
     * Merges two files by alphabetical order.
     *
     * @param firstFile the first file to merge
     * @param secondFile the second file to merge
     * @param mergedFile the merged output file
     */
    private void mergeTwoFiles(File firstFile, File secondFile, File mergedFile){
        try {
            BufferedReader br1 = new BufferedReader(new FileReader(firstFile));
            BufferedReader br2 = new BufferedReader(new FileReader(secondFile));
            BufferedWriter bw = new BufferedWriter(new FileWriter(mergedFile));
            String line1 = br1.readLine();
            String line2 = br2.readLine();
            while (line1 != null || line2 != null){
                if (line1 == null){
                    bw.write(line2);
                    line2 = br2.readLine();
                } else if (line2 == null){
                    bw.write(line1);
                    line1 = br1.readLine();
                } else if (line1.compareTo(line2) > 0){
                    bw.write(line2);
                    line2 = br2.readLine();
                } else {
                    bw.write(line1);
                    line1 = br1.readLine();
                }
                bw.newLine();
            }
            bw.close();
            br1.close();
            br2.close();
            firstFile.delete();
            secondFile.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes all unique values from the final merged file to an output file
     *
     * @param mergedFile the merged file
     */
    private void writeMergedFileToOutputFile(File mergedFile){
        try {
            BufferedReader br = new BufferedReader(new FileReader(mergedFile));
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File(this.outputName)));
            String line;
            String currWord = "";
            while ((line = br.readLine()) != null){
                if (!currWord.equals(line) && !line.equals("")) {
                    bw.write(line + "\n");
                    currWord = line;
                }
            }
            bw.close();
            br.close();
            mergedFile.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
