package com.javarush.task.task37.task3707;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

public class AmigoSet<E> extends AbstractSet<E> implements Set<E>, Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    private static final Object PRESENT = new Object();
    private transient HashMap<E, Object> map;

    public AmigoSet() {
        map = new HashMap<>();
    }

    public AmigoSet(Collection<? extends E> collection) {
        int capacity = (int)Math.max((Math.ceil(collection.size()/.75f)), 16);
        map = new HashMap<>(capacity);
        addAll(collection);
    }

    @Override
    public Iterator<E> iterator() {
        return map.keySet().iterator();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean add(E e) {
        if(!map.containsKey(e)){
            map.put(e, PRESENT);
            return true;
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return map.containsKey(o);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public boolean remove(Object o) {
        return map.remove(o) == PRESENT;
    }

    @Override
    public Object clone() {
        try {
            AmigoSet<E> newAmigoSet = (AmigoSet<E>) super.clone();
            newAmigoSet.map = (HashMap<E, Object>) map.clone();
            return newAmigoSet;
        }catch (Exception e){
            throw new InternalError(e);
        }
    }

    private void writeObject(ObjectOutputStream outputStream) throws IOException {
        outputStream.defaultWriteObject();

        outputStream.writeInt(HashMapReflectionHelper.<Integer>callHiddenMethod(map, "capacity"));
        outputStream.writeFloat((float)HashMapReflectionHelper.<Float>callHiddenMethod(map, "loadFactor"));
        outputStream.writeInt(map.size());

        for(E e : map.keySet()){
            outputStream.writeObject(e);
        }


    }

    private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        inputStream.defaultReadObject();

        int capacity = inputStream.readInt();
        float loadFactor = inputStream.readFloat();
        int size = inputStream.readInt();

        map = new HashMap<>(capacity, loadFactor);

        for (int i = 0; i < size; i++) {
            E e = (E) inputStream.readObject();
            map.put(e, PRESENT);
        }


    }
}
