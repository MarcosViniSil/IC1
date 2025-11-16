input_path = 'balanced-converted.csv'
output_path = 'balanced-converted-1.csv'

MAX_CLASS = 29

with open(input_path, 'r') as infile, open(output_path, 'w') as outfile:
        for line in infile:
            line = line.replace("M","1,0,0").replace("F","0,1,0").replace("I","0,0,1")
            outfile.write(line)