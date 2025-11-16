import csv
from sklearn.model_selection import train_test_split

with open("abalone.csv", "r") as f:
    data = list(csv.reader(f, delimiter=","))

train_size = 0.75
val_size = 0.0
test_size = 0.25

x_train, x_remain = train_test_split(data, test_size=(val_size + test_size), random_state=42)

def save_csv(data, filename):
    with open(filename, mode='w', newline='') as f:
        writer = csv.writer(f)
        writer.writerows(data)

save_csv(x_train, "abalone_train.csv")
save_csv(x_remain, "abalone_test.csv")

print("Arquivos gerados com sucesso!")
