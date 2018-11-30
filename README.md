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

## Test Cases
- Empty input file
- Input file fits into memory
- Input file that does not fit into memory

## Possible Solutions
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

## How To Run
`python RyanChan_CodingChallenge.py large_input.txt`

If argument are not given, automatically use `large_input.txt`