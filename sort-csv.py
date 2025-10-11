import sys
import csv

reader = csv.reader(open("abalone.csv"), delimiter=",")
output_path = "abalone-converted.csv"

sortedList = sorted(reader, key=lambda row: row[0])

with open(output_path, 'w') as outfile:
    for line in sortedList:
        line.append("\n")
        separator = "," 
        lines = separator.join(line)
        print(lines[len(lines) - 2])
        print()
        lines = lines[len(lines) - 2].replace(",","") + lines[0:len(lines) - 2]+"\n"
        outfile.write(lines)