package com.escape.maze.structures;

public class SinglyLinkedList<T> {


    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {

            this.data = data;
        }
    }
    private Node<T> head;
    private int size;

    public void add(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
        } else {
            Node<T> current = head;
            while (current.next != null)
                current = current.next;
            current.next = newNode;
        }
        size++;
    }

    public T get(int index) {
        if (index < 0 || index >= size) return null;
        Node<T> current = head;
        for (int i = 0; i < index; i++)
            current = current.next;
        return current.data;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return head == null;
    }
}
