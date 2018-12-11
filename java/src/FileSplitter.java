import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * FileSplitter is the class that reads a file with a given filename and partitions the words
 * into multiple files of given size.
 */
public class FileSplitter {
    private static long PARTITION_SIZE = 1000;
    private static String PARTITION_NAME = "./partition%d";

    private String fileName;
    private Queue<String> partitionNames;

    /**
     * Creates instance of a FileSplitter
     *
     * @param fileName input file to sort
     */
    public FileSplitter(String fileName){
        this.fileName = fileName;
        this.partitionNames = new LinkedList<>();
    }

    /**
     * Splits the file into different partitions
     */
    public void splitFile(){
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(new File(this.fileName)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        List<String> lines = new ArrayList<>();
        String line = "";
        int partitionNo = 0;
        while (line != null){
            long currentPartitionSize = 0;
            try {
                while (currentPartitionSize < PARTITION_SIZE && (line = br.readLine()) != null){
                    lines.add(line);
                    currentPartitionSize += line.length();
                }
                writeUniqueSortedWordsToPartitionFile(readLinesFromPartition(lines), partitionNo++);
                lines.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Reads each block of lines and keeps track of unique words
     *
     * @param lines block of lines
     * @return list of sorted unique words
     */
    private List<String> readLinesFromPartition(List<String> lines){
        Set<String> existingWords = new HashSet<>();
        lines.forEach(line -> existingWords.addAll(Arrays.asList(line.split(" "))));
        List<String> sortedUniqueWords = existingWords.stream().collect(Collectors.toList());
        sortedUniqueWords.sort(String::compareTo);
        return sortedUniqueWords;
    }

    /**
     * Writes the sorted and unique words into an output file
     *
     * @param words list of sorted words
     * @param partitionNo partition number for output filename
     */
    private void writeUniqueSortedWordsToPartitionFile(List<String> words, int partitionNo){
        String partitionName = String.format(PARTITION_NAME, partitionNo);
        try {
            Files.write(Paths.get(partitionName),
                    words.stream().map(word -> word.trim() + "\n").collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        partitionNames.add(partitionName);
    }

    /**
     * Getter function for the partition names
     *
     * @return Queue of partition names
     */
    public Queue<String> getPartitionNames(){
        return this.partitionNames;
    }
}
