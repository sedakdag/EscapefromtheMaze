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

    //Add an element to the end of the list
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
    
    //Remove the element at a specific index
    public T remove(int index) {
        if (index < 0 || index >= size) return null;
        if (index == 0) {
            T data = head.data;
            head = head.next;
            size--;
            return data;
        }
        Node<T> current = head;
        for (int i = 0; i < index - 1; i++) {
            current = current.next;
        }
        T data = current.next.data;
        current.next = current.next.next;
        size--;
        return data;
    }
    
    //Remove all elements from the list
    public void clear() {
        head = null;
        size = 0;
    }

    //Check if the list contains a specific element (search)
    public boolean contains(T data) {
        Node<T> current = head;
        while (current != null) {
            if (current.data.equals(data)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }
    
    //Get the element at a specific index
    public T get(int index) {
        if (index < 0 || index >= size) return null;
        Node<T> current = head;
        for (int i = 0; i < index; i++)
            current = current.next;
        return current.data;
    }
    
    
    //Return the number of elements in the list
    public int size() {
        return size;
    }

    //Check if the list is empty
    public boolean isEmpty() {
        return head == null;
    }
}
