input_path = 'trash/abalone.csv'
output_path = 'abalone-converted-2.csv'

with open(input_path, 'r') as infile, open(output_path, 'w') as outfile:
    for line in infile:
        line = line.replace("F","0,0,1").replace("I","0,1,0").replace("M","1,0,0")
        outfile.write(line)