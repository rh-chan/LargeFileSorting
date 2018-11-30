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


class InputFile:
    def __init__(self, filename):
        self.filename = filename
        self.partitions = deque()

    def split_file(self):
        """
        Split input file into partitions
        """
        pass

    def partitions(self):
        """
        :return: a queue for the name of the partitions
        """
        return self.partitions


def merge_two_partitions(first_name, second_name, merged_name):
    """
    Perform merge sort on two partitions. Writes it to a new temporary merged file.
    :return: name of merged file
    """
    return merged_name


def write_merged_to_output(merged_name, output_name):
    """
    Writes all unique values from the final merged file to an output file.
    """
    pass


if __name__ == "__main__":
    pass
