package com.example.ap_project;

public class Pair<T1, T2> {
    private final T1 A;
    private final T2 B;

    public Pair(T1 a1, T2 a2) {
        A = a1;
        B = a2;

    }

    public T1 first() {
        return A;

    }

    public T2 second() {
        return B;
    }
}
