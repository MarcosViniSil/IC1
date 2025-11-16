import pandas as pd
from sklearn.preprocessing import MinMaxScaler
import joblib

train = pd.read_csv("abalone-train2.csv", header=None, on_bad_lines='skip')


scaler_all = MinMaxScaler(feature_range=(0, 1))


train_normalized = pd.DataFrame(
    scaler_all.fit_transform(train),
    columns=train.columns
)


# === 3️⃣ Salvar os novos CSVs ===
train_normalized.to_csv("abalone-train.csv", header=False, index=False, float_format="%.4f")


print("✅ Todos os dados foram normalizados com sucesso!")
