package com.javarush.task.task20.task2028;

import java.io.Serializable;
import java.net.PortUnreachableException;
import java.util.*;

/* 
Построй дерево(1)
*/

public class CustomTree extends AbstractList<String> implements Cloneable, Serializable {

    Entry<String> root;
    int count;

    public CustomTree() {
        this.root = new Entry<>("0");
    }

    @Override
    public String get(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public String set(int index, String element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, String element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends String> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(String s) {
        Queue<Entry<String>> queue = new LinkedList<>();
            queue.add(root);

            while (!queue.isEmpty()) {
                Entry<String> temp = queue.poll();
                if(temp.leftChild != null){
                    queue.add(temp.leftChild);
                }else {
                    if(temp.rightChild == null || Integer.parseInt(temp.rightChild.elementName) > Integer.parseInt(s)){
                        temp.leftChild = new Entry<>(s);
                        temp.leftChild.parent = temp;
                        count++;
                        return true;
                    }
                }

                if(temp.rightChild != null){
                    queue.add(temp.rightChild);
                }else {
                    if(temp.leftChild.leftChild == null || Integer.parseInt(temp.leftChild.leftChild.elementName) > Integer.parseInt(s)) {
                        temp.rightChild = new Entry<>(s);
                        temp.rightChild.parent = temp;
                        count++;
                        return true;
                    }
                }
            }

        return false;
    }

    public String getParent(String s){
        Queue<Entry<String>> queue = new LinkedList<>();
        queue.add(root);
        String result = "null";

        while (!queue.isEmpty()) {
            Entry<String> temp = queue.poll();

            if(temp.leftChild != null){
                if(temp.leftChild.elementName.equals(s)){
                    result = temp.elementName;
                    break;
                }
                queue.add(temp.leftChild);
            }

            if(temp.rightChild != null){
                if(temp.rightChild.elementName.equals(s)){
                    result = temp.elementName;
                    break;
                }
                queue.add(temp.rightChild);
            }
        }
        return result;
    }

    public boolean remove(Object o) {
        if(o instanceof String) {
            String s = (String) o;
            Queue<Entry<String>> queue = new LinkedList<>();
            queue.add(root);

            while (!queue.isEmpty()) {
                Entry<String> temp = queue.poll();
                if(temp.leftChild != null){
                    if(temp.leftChild.elementName.equals(s)){
                        temp.leftChild = null;
                        count = sizeQueue();
                        return true;
                    }
                    queue.add(temp.leftChild);
                }

                if(temp.rightChild != null){
                    if(temp.rightChild.elementName.equals(s)){
                        temp.rightChild = null;
                        count = sizeQueue();
                        return true;
                    }
                    queue.add(temp.rightChild);
                }
            }


        } else {
            throw new UnsupportedOperationException();
        }
        return false;
    }


    public int sizeQueue() {
        Queue<Entry<String>> queue = new LinkedList<>();
        queue.add(root);
        int count = 0;
        while (!queue.isEmpty()) {
            Entry<String> temp = queue.poll();

            if(temp.leftChild != null){
                queue.add(temp.leftChild);
                count++;
            }
            if(temp.rightChild != null){
                queue.add(temp.rightChild);
                count++;
            }
        }
        return count;
    }

    static class Entry<T> implements Serializable {
        String elementName;
        boolean availableToAddLeftChildren, availableToAddRightChildren;
        Entry<T> parent, leftChild, rightChild;

        public Entry(String elementName) {
            this.elementName = elementName;
            availableToAddLeftChildren = true;
            availableToAddRightChildren = true;
        }

        public boolean isAvailableToAddChildren() {
            return availableToAddLeftChildren || availableToAddRightChildren;
        }
    }
}
