import random
import pandas as pd
from sklearn.preprocessing import MinMaxScaler
import csv
from pathlib import Path


csv_path = "balanced.csv"
output_path = "../samples/abalone-train.csv"


MAX_CLASS = 29
count_classes = {}

def convertClass(input_path, output_path):
    count_classes = {}

    with open(input_path, 'r') as infile, open(output_path, 'w') as outfile:
        for line in infile:
            new_line = line.strip().split(",")
            print(new_line)
            class_value = int(new_line[10])

            count_classes[class_value] = count_classes.get(class_value, 0) + 1

            one_hot = [0] * MAX_CLASS
            one_hot[class_value - 1] = 1

            combined = new_line[:8] + [str(v) for v in one_hot]

            outfile.write(",".join(combined) + "\n")

    print("Classes convertidas:", count_classes)


def convertLetterToBinary(input_path, output_path):
    with open(input_path, 'r') as infile, open(output_path, 'w') as outfile:
        for line in infile:
            line = line.replace("M","1,0,0").replace("F","0,1,0").replace("I","0,0,1")
            outfile.write(line)

def convertOutIntegerToFloat(input_path, output_path):
    with open(input_path, 'r') as infile, open(output_path, 'w') as outfile:
        for line in infile:
            parts = line.strip().split(",")

            for i in range(len(parts)-1, len(parts)):
                try:
                    num = float(parts[i])
                    if num == 0:
                        parts[i] = "0.005"
                    else:
                        parts[i] = str(num - 0.005)
                except:
                    pass

            outfile.write(",".join(parts) + "\n")


def normalizeData(input_path, output_path):
    train = pd.read_csv(input_path, header=None, on_bad_lines='skip')


    scaler_all = MinMaxScaler(feature_range=(0, 1))


    train_normalized = pd.DataFrame(
        scaler_all.fit_transform(train),
    columns=train.columns
)

    train_normalized.to_csv(output_path, header=False, index=False, float_format="%.4f")


step1 = "step1.csv"
step2 = "step2.csv"
step3 = "step3.csv"

convertLetterToBinary(csv_path, step1)
convertClass(step1, step2)
convertOutIntegerToFloat(step2, step3)
normalizeData(step3, output_path)

Path(step1).unlink()
Path(step2).unlink()
Path(step3).unlink()

print("🔥 Dataset final normalizado salvo em:", output_path)