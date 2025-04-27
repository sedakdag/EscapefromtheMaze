package com.escape.maze.structures;

public class CircularLinkedList<T> {
    private Node<T> tail;
    private Node<T> current;
    private int size;

    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {

            this.data = data;
        }
    }

  //Add a new element to the end of the list
    public void add(T data) {
        Node<T> node = new Node<>(data);
        if (tail == null) {
            tail = node;
            tail.next = tail; //Circular
            current = tail;
        } else {
            node.next = tail.next;
            tail.next = node;
            tail = node;
        }
        size++;
    }
    
    public T getCurrent() {
        if (current != null) {
            return current.data;
        }
        return null;
    }
    
    public void moveNext() {
        if (current != null) {
            current = current.next;
        }
    }

    //Rotate the list by moving the tail one step forward
    public void rotate() {
        if (tail != null) {
            tail = tail.next;
        }
    }

    //Convert the circular list to an array
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
    
    //Get the tail node
    public Node<T> getTail() {
        return tail;
    }
    
    //Return the number of elements
    public int size() {
        return size;
    }
    
    //Check if the list is empty
    public boolean isEmpty() {
        return size == 0;
    }
    
    //Remove all elements from the list
    public void clear() {
        tail = null;
        size = 0;
    }
    
    //Check if the list contains a specific element
    public boolean contains(T data) {
        if (tail == null) return false;
        Node<T> current = tail.next;
        for (int i = 0; i < size; i++) {
            if (current.data.equals(data)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }
}
