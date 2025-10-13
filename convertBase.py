input_path = 'abalone_test.csv'
output_path = 'abalone_test-converted.csv'

num_classes = 30  # total de posições na codificação
with open(input_path, 'r') as infile, open(output_path, 'w') as outfile:
    for line in infile:
        for i in range(30):  # de 0 a 29
            pattern = f"{i}.995"
            one_hot = ",".join(["0.995" if j == i else "0" for j in range(num_classes)])
            line = line.replace(pattern, one_hot)
        outfile.write(line)