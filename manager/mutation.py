import random
import pandas as pd
from sklearn.preprocessing import MinMaxScaler
import csv

csv_path = "abalone_train.csv"
output_path = "balanced.csv"

sigma = 0.05          
MAX_VALUE = 167        
PROBABILITY = 0.4      

statistics = {
    10: 167, 11: 111, 12: 62, 13: 53, 14: 36, 15: 21, 16: 11, 17: 13,
    18: 12, 19: 7, 20: 3, 21: 4, 22: 2, 23: 1, 27: 1, 29: 1, 3: 4, 4: 21,
    5: 31, 6: 74, 7: 99, 8: 145, 9: 166
}

MAX_CLASS = 29
count_classes = {}

def get_class_from_line(line):
    parts = line.strip().split(",")
    return int(parts[-1]) 

def mutate_value(value):
    try:
        v = float(value)
    except:
        return value 

    if random.random() < PROBABILITY:
        noise = random.uniform(-sigma, sigma) * abs(v)
        v = round(max(0, v + noise) ,3)
    return str(v)

def mutate():
    with open(csv_path, "r") as f:
        original_lines = f.readlines()

    balanced_lines = []

    for line in original_lines:
        balanced_lines.append(line)


    for class_value, count in statistics.items():

        if count >= MAX_VALUE:
            continue  

        class_lines = [l for l in original_lines if get_class_from_line(l) == class_value]
        if class_lines == []:
            continue
        
        faltam = MAX_VALUE - count

        for _ in range(faltam):
            base_line = random.choice(class_lines).strip()
            parts = base_line.split(",")

            new_parts = [mutate_value(p) for p in parts[:-1]] 

            new_parts.append(str(class_value))

            new_line = ",".join(new_parts) + "\n"

            balanced_lines.append(new_line)

            statistics[class_value] += 1  

    with open(output_path, "w") as f:
        for line in balanced_lines:
            f.write(line)

    print("Balanceamento concluído!")

def convertClass():
    with open(output_path, 'r') as infile, open(output_path, 'w') as outfile:
        for line in infile:
            new_line = line.split(",")
            position_to_add_one = int(new_line[8].replace("\n",""))
            if count_classes.get(position_to_add_one) is None:
                  count_classes[position_to_add_one] = 1
            else:
                  count_classes[position_to_add_one] += 1
            line_values = [0] * MAX_CLASS
            line_values[position_to_add_one - 1] = 1
            string_numbers = [str(num) for num in line_values]
            new_line2 = ",".join(string_numbers)
            #line = line.strip()
            #line = line.replace(f"{position_to_add_one}", new_line)
            new_line = new_line[:8] + string_numbers
            new_line = ",".join(new_line) + "\n"
            outfile.write(new_line)

def convertLetterToBinary():
    with open(output_path, 'r') as infile, open(output_path, 'w') as outfile:
        for line in infile:
            line = line.replace("M","1,0,0").replace("F","0,1,0").replace("I","0,0,1")
            outfile.write(line)

def convertOutIntegerToFloat():
    OUT_QNT = 1

    with open(output_path, 'r') as infile, open(output_path, 'w') as outfile:
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

def normalizeData():
    train = pd.read_csv(output_path, header=None, on_bad_lines='skip')

    scaler_all = MinMaxScaler(feature_range=(0, 1))

    train_normalized = pd.DataFrame(
        scaler_all.fit_transform(train),
        columns=train.columns
    )
    train_normalized.to_csv(output_path, header=False, index=False, float_format="%.4f")

    print("dados foram normalizados")

def sortCSV():
    with open(output_path, "r") as f:
        reader = list(csv.reader(f))

    data = reader
    data_sorted = sorted(data, key=lambda row: float(row[-1]))
    with open(output_path, "w", newline="") as f:
        writer = csv.writer(f)
        writer.writerows(data_sorted)

mutate()
sortCSV()
#convertLetterToBinary()
#convertClass()
#normalizeData()