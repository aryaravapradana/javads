package com.datastruct;
public class DoubleHashing<K,V> extends Hashing<K,V> {
    
    public DoubleHashing(int capacity) {
        super(capacity);
    }

    private int hash1(K key) {
        return convertToNumber(key) % table.maxSize();
    }

    private int hash2(K key) {
        return 1 + (convertToNumber(key) % 7);  // Rumus = H2(key) = 1 + (key mod 7)
    }

    @Override
    public void put(K key, V value) {
        HashNode<K,V> N = new HashNode<>(key, value);
        int theKey = hash1(key);
        int step = hash2(key);
        int curKey = theKey;
        boolean firstScan = true;

        while (isCollision(curKey) && curKey >= 0) {
            curKey = (curKey + step) % table.maxSize();
            if (curKey == theKey && !firstScan) {
                curKey = -1;  // artinya table nya penuh
            }
            firstScan = false;
        }

        if (curKey >= 0) {
            table.set(curKey, N);
            incSize();
        } else {
            System.out.println("Table is full!");
        }
    }

    public HashNode<K,V> get(K key) {
        int theKey = hash1(key);
        int step = hash2(key);
        int curKey = theKey;
        boolean firstScan = true;

        while (isCollision(curKey) && convertToNumber(table.get(curKey).key) != convertToNumber(key) && curKey >= 0) {
            curKey = (curKey + step) % table.maxSize();
            if (curKey == theKey && !firstScan) {
                curKey = -1;  // ndak ketemu
            }
            firstScan = false;
        }

        if (curKey >= 0) {
            return table.get(curKey);
        } else {
            System.out.println("Not found!");
            return null;
        }
    }

    public HashNode<K,V> remove(K key) {
        int theKey = hash1(key);
        int step = hash2(key);
        int curKey = theKey;
        boolean firstScan = true;

        while (isCollision(curKey) && convertToNumber(table.get(curKey).key) != convertToNumber(key) && curKey >= 0) {
            curKey = (curKey + step) % table.maxSize();
            if (curKey == theKey && !firstScan) {
                curKey = -1;  // ndak ketemu
            }
            firstScan = false;
        }

        if (curKey >= 0) {
            HashNode<K,V> removedNode = table.get(curKey);
            table.set(curKey, null);
            decSize();
            return removedNode;
        } else {
            System.out.println("Not found!");
            return null;
        }
    }
}