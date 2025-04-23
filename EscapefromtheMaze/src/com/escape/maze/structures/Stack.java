package com.escape.maze.structures;

/*<T> aslında "Type Parameter" demektir. Yani bu bir placeholder (yer tutucu) – sen o sınıfı tanımlarken
 *  ne tür veriyle çalışacağını söylemeden önce bir esneklik sağlar. Genellikle veri yapılarıyla birlikte kullanılır. */ 
/* Generic bir yapıdır */ 

public class Stack<T> {
	
    private Node<T> top;
    private int size;

    private static class Node<T> {
        T data;
        Node<T> next;
        Node(T data) {
            this.data = data;
        }
    }

    public void push(T data) {
        Node<T> node = new Node<>(data);
        node.next = top;
        top = node;
        size++;
    }

    public T pop() {
        if (isEmpty()) {
        	return null;
        }
        T data = top.data;
        top = top.next;
        size--;
        return data;
    }

    public T peek() {
        return isEmpty() ? null : top.data;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public int size() {
        return size;
    }
}
