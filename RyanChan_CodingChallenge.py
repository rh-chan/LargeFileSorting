"""
Informatica Coding Challenge

Given a very large text file, create a file:
1. distinct words from the original file
2. sorted in ascending order

Assumptions
- Case sensitive
- does not filter words

"""
from collections import deque
import sys
import os


class InputFile:
    PARTITION_SIZE = 1000  # Set partition size to 1000 bytes
    PARTITION_NAME = "partition{0}"

    def __init__(self, filename):
        self.filename = filename
        self.partitions = deque()

    @staticmethod
    def read_lines(lines):
        """
        Read each block of lines keeping track of unique words
        :return: list of sorted unique words
        """
        words = set()
        for line in lines:
            words.update(line.split())
        return sorted(list(words))

    def write_sorted_to_file(self, words, partition_number):
        """
        Writes the sorted, unique words into an output file
        """
        partition_name = self.PARTITION_NAME.format(partition_number)
        file = open(partition_name, 'w')
        for word in words:
            file.write(word + "\n")
        file.close()
        self.partitions.append(partition_name)

    def split_file(self):
        """
        Split input file into partitions
        """
        file, partition = open(self.filename, 'r'), 0
        lines = file.readlines(self.PARTITION_SIZE)
        while lines:
            self.write_sorted_to_file(self.read_lines(lines), partition)
            lines, partition = file.readlines(self.PARTITION_SIZE), partition + 1

    def get_partitions(self):
        """
        :return: a queue for the name of the partitions
        """
        return self.partitions


def merge_two_partitions(first_name, second_name, merged_name):
    """
    Perform merge sort on two partitions. Writes it to a new temporary merged file.
    :return: name of merged file
    """
    first_file, second_file, merged_file = open(first_name, 'r'), open(second_name, 'r'), open(merged_name, 'w')
    line1, line2 = first_file.readline(), second_file.readline()
    while line1 or line2:
        if not line1:
            merged_file.write(line2)
            line2 = second_file.readline()
        elif not line2:
            merged_file.write(line1)
            line1 = first_file.readline()
        elif line1 > line2:
            merged_file.write(line2)
            line2 = second_file.readline()
        else:
            merged_file.write(line1)
            line1 = first_file.readline()
    merged_file.close()
    os.remove(first_name)
    os.remove(second_name)
    return merged_name


def write_merged_to_output(merged_name, output_name):
    """
    Writes all unique values from the final merged file to an output file.
    """
    merged_file, output = open(merged_name, 'r'), open(output_name, 'w')
    line, curr = merged_file.readline(), ""
    while line:
        if curr != line:
            output.write(line)
            curr = line
        line = merged_file.readline()
    output.close()
    os.remove(merged_name)


if __name__ == "__main__":
    if len(sys.argv) > 1:
        filename = sys.argv[1]
    else:
        filename = "large_input.txt"

    file = InputFile(filename)
    file.split_file()
    queue = file.get_partitions()
    merged_template, counter = "{0}", 0

    while len(queue) > 1:
        first_name = queue.popleft()
        second_name = queue.popleft()
        merged_name = merged_template.format(counter)
        queue.append(merge_two_partitions(first_name, second_name, merged_name))
        counter += 1

    write_merged_to_output(queue.popleft(), "output.txt")
