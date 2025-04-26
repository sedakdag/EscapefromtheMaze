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
    
    //Add to the end of the queue
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
    
    //Remove and return the front element of the queue
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
    
    //Check if the queue contains a specific element
    public boolean contains(T data) {
        Node<T> current = front;
        while (current != null) {
            if (current.data.equals(data)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }
    
    //Return the front element without removing it
    public T peek() {
        return front != null ? front.data : null;
    }
    
    //Check if the queue is empty
    public boolean isEmpty() {
        return front == null;
    }
    
    //Return the total number of elements
    public int size() {
        return size;
    }
    
    //Remove all elements from the queue
    public void clear() {
        front = null;
        rear = null;
        size = 0;
    }
}
