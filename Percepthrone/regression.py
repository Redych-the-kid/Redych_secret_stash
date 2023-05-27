import random
import numpy as np
import pandas as pd
from pathlib import Path
import matplotlib.pyplot as plt
from sklearn.metrics import r2_score
from sklearn.model_selection import train_test_split

#Функция активации
def sig(t):
    return 1 / (1 + np.exp(-t))

# Производная функции активации
def sig_deriv(t):
    return sig(t) * (1 - sig(t))

# Для графика
def MSE(z, y):
    a = np.array([(z[j] - y[j]) ** 2 for j in range(len(y))])
    return a / len(y)

# Перемешать наборы входных значений
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
         
#предсказание
def predict(x):
    t1 = x @ W1 + b1
    h1 = sig(t1)
    t2 = h1 @ W2 + b2
    z = t2
    return z

#подсчёт точности
def calc_accuracy(dataset):
    correct = 0
    for i in range(len(dataset[0])):
        x = dataset[0][i]
        y = dataset[1][i]
        z = predict(x)
        y_pred = z
        correct += abs(y_pred[0] - y[0])
        print('y_pred', y_pred, 'y', y[0])
    acc = 100 - correct * 100 / len(dataset[0])
    return acc

def var(y_pred, y_true):
    u_y = 0
    for i in range(len(y_pred)):
        u_y += y_pred[i]
    u_y /= len(y_true)

    a = np.array([(y_true[j] - u_y) ** 2 for j in range(len(y_true))])
    return a / len(y_true)

def R2(y_pred, y_true):
    return 1 - (np.sum(MSE(y_pred, y_true)) / np.sum(var(y_pred, y_true)))

#Читаем датасет с файла
dataFrame = pd.read_csv("dataset-lab1_kgf.csv", thousands='.', decimal=',', skiprows=0)
dataset = np.array(dataFrame)


# Нормирование

dataset /= np.nanmax(np.abs(dataset), axis=0)

TEST_SIZE = 5

# Перемешиваем датасет
for i in range(len(dataset)):
    for j in range(len(dataset)):
        if bool(random.getrandbits(1)) == 1:
            swapper = dataset[i]
            dataset[i] = dataset[j]
            dataset[j] = swapper

# Делим на тренировочную и обучающую выборки
dataset_train, dataset_test = train_test_split(dataset, test_size=0.2)

# Разделяем на целевые и нецелевые
dataset_test = np.hsplit(dataset_test, np.array([len(dataset_test[0]) - 1]))
dataset_train = np.hsplit(dataset_train, np.array([len(dataset_train[0]) - 1]))

# Делим на наборы(чтобы не по одному пихать, а по несколько)
BATCH_SIZE = 5
dataset_x_batch = np.array_split(dataset_train[0], BATCH_SIZE, axis=0)
dataset_y_batch = np.array_split(dataset_train[1], BATCH_SIZE, axis=0)

INPUT_DIM = len(dataset_train[0][0]) #Размер входного вектора
OUT_DIM = 1 #размер выходного вектора
H_DIM = 25 #кол-во нейронов в первом слое

# Веса и смещение по умолчанию заполняем случайными числами
W1 = np.random.rand(INPUT_DIM, H_DIM)
b1 = np.random.rand(1, H_DIM)
W2 = np.random.rand(H_DIM, OUT_DIM)
b2 = np.random.rand(1, OUT_DIM)

W1 = (W1 - 0.5) * 2 * np.sqrt(1 / INPUT_DIM)
b1 = (b1 - 0.5) * 2 * np.sqrt(1 / INPUT_DIM)
W2 = (W2 - 0.5) * 2 * np.sqrt(1 / H_DIM)
b2 = (b2 - 0.5) * 2 * np.sqrt(1 / H_DIM)

ALPHA = 0.01 # скорость обучения
NUM_EPOCHS = 2000 # количество эпох

loss_arr_mse = []
loss_arr_r2 = []

for ep in range(NUM_EPOCHS):
    shuffle_batch(dataset_x_batch, dataset_y_batch)
    for i in range(BATCH_SIZE):
        x = dataset_x_batch[i]
        y = dataset_y_batch[i]
        # Forward
        t1 = x @ W1 + b1
        h1 = sig(t1)
        t2 = h1 @ W2 + b2
        z = t2
        E_mse = np.sum(MSE(z, y))
        E_r2 = R2(z, y)
        # Backward
        dE_dt2 = z - y
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
        # For metrics
        loss_arr_mse.append(E_mse)
        loss_arr_r2.append((E_r2))

accuracy = calc_accuracy(dataset_test)
print("Accuracy on test:", accuracy)
accuracy = calc_accuracy(dataset_train)
print("Accuracy on training:", accuracy)
plt.plot(loss_arr_r2)
plt.show()
plt.plot(loss_arr_mse)
plt.show()