package com.escape.maze.structures;

public class Queue<T> {
    private Node<T> front, rear;
    private int size;

    private static class Node<T> {
        T data;
        Node<T> next;
        Node(T data) {

            this.data = data;
        }
    }
    // kuyruğun sonuna ekleme
    public void enqueue(T data) {
        Node<T> newNode = new Node<>(data);
        if (isEmpty()) {
            front= rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
    }
    // kuyruğun başındaki elemanı çıkarıp dönme
    public T dequeue() {
        if (isEmpty()) {
            return null;
        }
        T data = front.data;
        front = front.next;
        if (front == null) {
            rear = null;
        }

        size--;
        return data;
    }
    // kuyruğun başındaki elemanı gösterme
    public T peek() {
        return front != null ? front.data : null;
    }
    //başa dönme
    public boolean isEmpty() {
        return front == null;
    }
    //toplam eleman sayısı
    public int size() {
        return size;
    }
}
