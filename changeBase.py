input_path = 'base.csv'
output_path = 'base_numeric.csv'

with open(input_path, 'r') as infile, open(output_path, 'w') as outfile:
    for line in infile:
        line = line.replace('no', '0').replace('yes', '1')
        outfile.write(line)