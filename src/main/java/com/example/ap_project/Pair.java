package com.example.ap_project;

public class Pair<T1,T2> {
    private T1 A;
    private T2 B;

    public Pair(T1 a1,T2 a2)
    {
        A=a1;B=a2;

    }
    public T1 first()
    {
        return A;

    }
    public T2 second()
    {
        return B;
    }
}
