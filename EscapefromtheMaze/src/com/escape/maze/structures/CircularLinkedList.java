package com.escape.maze.structures;

public class CircularLinkedList<T> {
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
        }
    }

    public void add(T data) {
        Node<T> node = new Node<>(data);
        if (tail == null) {
            tail = node;
            node.next = node;
        } else {
            node.next = tail.next;
            tail.next = node;
            tail = node;
        }
        size++;
    }

    public void rotate() {
        if (tail != null) {
            tail = tail.next;
        }
    }

    public T[] toArray() {
        T[] arr = (T[]) new Object[size];
        if (tail == null) return arr;
        Node<T> current = tail.next;
        for (int i = 0; i < size; i++) {
            arr[i] = current.data;
            current = current.next;
        }
        return arr;
    }

    public Node<T> getTail() {
        return tail;
    }

    public int size() {
        return size;
    }
}