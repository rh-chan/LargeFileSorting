# LargeFileSorting
Coding Challenge for Informatica

## Question
Given a large text file, that may not fit in memory, create a file:
- distinct words from the original file
- sorted in ascending order

## Constraints
- May not fit in memory

## Assumptions
- Case sensitive
- Does not filter words (such as "Hi!" vs "Hi")
- Has 1000 Bytes of available memory for partition (can also change value to be smaller)

## Test Cases
- Empty input file "empty_input.txt"
- Input file fits into memory "small_input.txt" / "small_input_2.txt"
- Input file that does not fit into memory
    - generic large text file "large_input.txt"
    - generic very large text file "large_input_2.txt"
    - very large textfile with only 3 distinct elements [a, b, r], "large_input_3.txt"

## Solutions
Case 1: Empty input file
- Return empty output file

Case 2: Input file that fits in memory
1. For each line in input.txt:
2.     For each word on the line:
3.          add each word to a set without duplicates
4. Sort the words in the set and write to output.xt

Case 3: Input file that does not fit into memory
1. Divide the file into partitions
2. Perform Case 2 on each partition
3. Merge every two partition until only one temp file
4. Find all unique files in that temp file (since it is sorted just need to store into one variable)

## How To Run
`python RyanChan_CodingChallenge.py large_input.txt`

Can replace argument with any test file. If argument are not given, automatically use `large_input.txt`

## Sources
Large Text File: http://corpus.canterbury.ac.nz/descriptions/
Very Large Text File: https://norvig.com/big.txt
