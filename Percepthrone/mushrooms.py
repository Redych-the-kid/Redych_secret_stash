import random
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import matplotlib.pyplot as plt
from sklearn.model_selection import train_test_split

# Функция активации
def sig(t):
    return 1 / (1 + np.exp(-t))

# Производная функции активации(для backwards propagation)
def sig_deriv(t):
    return sig(t) * (1 - sig(t))

# Softmax(нормализация вектора)
def softmax_batch(t):
    out = np.exp(t)
    return out / np.sum(out, axis=1, keepdims=True)

# получение ошибки
def get_error(z, y):
    return -np.log(np.array([z[j, int(y[j])] for j in range(len(y))]))

def to_full_batch(y, num_classes):
    y_full = np.zeros((len(y), num_classes))
    for j, yj in enumerate(y):
        y_full[j, int(yj)] = 1
    return y_full

# перемешать наборы входных значений
def shuffle_batch(dataset_x_batch, dataset_y_batch):
    for i in range(BATCH_SIZE):
        for j in range(BATCH_SIZE):
            if bool(random.getrandbits(1)) == 1:
                swapper = dataset_x_batch[i]
                dataset_x_batch[i] = dataset_x_batch[j]
                dataset_x_batch[j] = swapper
                swapper = dataset_y_batch[i]
                dataset_y_batch[i] = dataset_y_batch[j]
                dataset_y_batch[j] = swapper

df = pd.read_excel('mushroom-dataset.xlsx')
dataset = np.array(df)

# Делим на тестовую и обучающую выборки
train_dataset, test_dataset = train_test_split(dataset, test_size=0.2)

# Разделяем на целевые и нецелевые
test_dataset = np.hsplit(test_dataset, np.array([len(dataset[0]) - 1]))
train_dataset = np.hsplit(train_dataset, np.array([len(dataset[0]) - 1]))

# Делим на наборы(чтобы не по одному пихать, а по несколько)
BATCH_SIZE = 500
dataset_x_batch = np.array_split(train_dataset[0], BATCH_SIZE, axis=0)
dataset_y_batch = np.array_split(train_dataset[1], BATCH_SIZE, axis=0)

# Параметры
INPUT_DIM = len(train_dataset[0][0]) # Ширина входа
OUT_DIM: int = int(np.max(train_dataset[1])) + 1 # Ширина выхода
H_DIM = INPUT_DIM + 10 # Ширина первого слоя

# веса
W1 = np.random.rand(INPUT_DIM, H_DIM)
b1 = np.random.rand(1, H_DIM)
W2 = np.random.rand(H_DIM, OUT_DIM)
b2 = np.random.rand(1, OUT_DIM)

W1 = (W1 - 0.5) * 2 * np.sqrt(1 / INPUT_DIM)
b1 = (b1 - 0.5) * 2 * np.sqrt(1 / INPUT_DIM)
W2 = (W2 - 0.5) * 2 * np.sqrt(1 / H_DIM)
b2 = (b2 - 0.5) * 2 * np.sqrt(1 / H_DIM)

ALPHA = 0.002 # cкорость обучения
NUM_EPOCHS = 40 # количество эпох

loss_arr = [] #ошибки(для последующего вывода графика)

#Обучение time!
for ep in range(NUM_EPOCHS):
    shuffle_batch(dataset_x_batch, dataset_y_batch)
    for i in range(BATCH_SIZE):
        x = dataset_x_batch[i]
        y = dataset_y_batch[i]

        # Forward
        t1 = x @ W1 + b1
        h1 = sig(t1) # активация
        t2 = h1 @ W2 + b2
        z = softmax_batch(t2)
        E = np.sum(get_error(z, y))

        # Backward
        y_full = to_full_batch(y, OUT_DIM)
        dE_dt2 = z - y_full
        dE_dW2 = h1.T @ dE_dt2
        dE_db2 = np.sum(dE_dt2, axis=0, keepdims=True)
        dE_dh1 = dE_dt2 @ W2.T
        dE_dt1 = dE_dh1 * sig_deriv(t1)
        dE_dW1 = x.T @ dE_dt1
        dE_db1 = np.sum(dE_dt1, axis=0, keepdims=True)

        # Update
        W1 = W1 - ALPHA * dE_dW1
        b1 = b1 - ALPHA * dE_db1
        W2 = W2 - ALPHA * dE_dW2
        b2 = b2 - ALPHA * dE_db2

        loss_arr.append(E)

# функция предсказания
def predict(x):
    t1 = x @ W1 + b1
    h1 = sig(t1)
    t2 = h1 @ W2 + b2
    z = softmax_batch(t2)
    return z

# подсчёт точности
def calc_accuracy(dataset):
    correct = 0
    for i in range(len(dataset[0])):
        x = dataset[0][i]
        y = dataset[1][i]
        z = predict(x)
        y_pred = np.argmax(z)
        if y == y_pred:
            correct += 1
    return correct / len(dataset[0]) * 100

accuracy = calc_accuracy(test_dataset)
print("Accuracy:", accuracy)

plt.plot(loss_arr)
plt.show()