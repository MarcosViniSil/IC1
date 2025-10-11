input_path = 'abalone-converted-2.csv'
output_path = 'abalone-converted-3.csv'

OUT_QNT = 1

with open(input_path, 'r') as infile, open(output_path, 'w') as outfile:
    for line in infile:
        
        lines = line.split(",")
        for i in range(len(lines) - OUT_QNT,len(lines)):
            try:
                num = float(lines[i])
                if num == 0:
                    lines[i] = str(0.005)
                else:
                    lines[i] = str(num - 0.005)
            except ValueError:
                pass  
        lines.append("\n")
        separator = "," 
        line = separator.join(lines)
        line = line[len(line)-2].replace(",","")+line[0:len(line)-2]+"\n"
        outfile.write(line)