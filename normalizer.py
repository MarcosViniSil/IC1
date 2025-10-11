import pandas as pd
from sklearn.preprocessing import MinMaxScaler
import joblib

# === 1️⃣ Ler os arquivos CSV ===
train = pd.read_csv("inflamations-train-converted.csv", header=None)
test = pd.read_csv("inflamations-test-converted.csv", header=None)

# === 2️⃣ Criar e aplicar o scaler em TODOS os dados de treino ===
scaler_all = MinMaxScaler(feature_range=(0, 1))

# Fit apenas no treino (para evitar vazamento de dados)
train_normalized = pd.DataFrame(
    scaler_all.fit_transform(train),
    columns=train.columns
)

# Transformar o teste com o mesmo scaler
test_normalized = pd.DataFrame(
    scaler_all.transform(test),
    columns=test.columns
)

# === 3️⃣ Salvar os novos CSVs ===
train_normalized.to_csv("inflamations_train_normalized.csv", header=False, index=False, float_format="%.4f")
test_normalized.to_csv("inflamations_test_normalized.csv", header=False, index=False, float_format="%.4f")

# === 4️⃣ Salvar o scaler completo ===
joblib.dump(scaler_all, "scaler_all.pkl")

print("✅ Todos os dados foram normalizados com sucesso!")
