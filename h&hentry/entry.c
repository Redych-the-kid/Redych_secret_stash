#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <string.h>
#include <assert.h>

#define MAX_SIZE 10 //Максимальный размер массива
#define MAX_NUMBER 200 //Максимальный размер числа

void a_sort(int *p, size_t size) {
    assert(p != NULL);
    int t = 0;
    for(size_t i = 0; i < size; ++i) {
        for(size_t j = 0; j < size; ++j) {
            if(*(p + j) > *(p + i)) {
                t = *(p + i);
                *(p + i) = *(p + j);
                *(p + j) = t;
            }
        }
    }
}

void b_sort(int *p, size_t size) {
    assert(p != NULL);
    int t = 0;
    for(size_t i = 0; i < size; ++i) {
        for(size_t j = 0; j < size; ++j) {
            if(*(p + j) < *(p + i)) {
                t = *(p + i);
                *(p + i) = *(p + j);
                *(p + j) = t;
            }
        }
    }
}

int insizes(int *arr, size_t size, size_t len) {
    for(int i = 0; i <= len; ++i) {
        if(arr[i] == size) {
            return 0;
        }
    }
    return 1;
}

int main(void) {
    srand(time(NULL));
    size_t n;
    printf("Введите кол-во массивов:");
    scanf("%lu", &n);
    int *arr[n];
    int sizes[n];
    memset(sizes, 0, sizeof(sizes));
    size_t len = 0;
    for(size_t i = 0; i < n; ++i) {
        size_t size = rand() % MAX_SIZE + 1;
        if(!insizes(sizes, size, len)) {
            while(!insizes(sizes, size, len)) {
                size = rand() % MAX_SIZE + 1;
            }
        }
        sizes[len] = size;
        len++;
        arr[i] = calloc(size, sizeof(int));
        int *p = arr[i];
        for(size_t j = 0; j < size; ++j) {
            *p = rand() % MAX_NUMBER + 1;
            p++;
        }
    }

    for(size_t i = 0; i < n; ++i) {
        if(i % 2 == 0) {
            a_sort(arr[i], sizes[i]);
        }
        else {
            b_sort(arr[i], sizes[i]);
        }
    }

    for(size_t i = 0; i < n; ++i) {
        int *p = arr[i];
        for(int j = 0; j < sizes[i]; ++j) {
            printf("%d ",*p);
            p++;
        }
        printf("\n");
    }

    for(size_t i = 0; i < n; ++i) {
        free(arr[i]);
    }

    return 0;
}
