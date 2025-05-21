package com.datastruct;
/*
 * Modified from: http://www.java2s.com/ref/java/java-data-structures-234-tree.html
 * 
*/

class DataItem<K, V> {
    private K key;
    private V value;

    public DataItem(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public void displayItem() {
        System.out.print("/" + key + ":" + value);
    }
}

class nmNode<K extends Comparable<K>, V> {
    private static final int ORDER = 4; //2-3 Tree, ORDER = 4
    private int numItems;
    private nmNode<K, V> parent;
    private nmNode<K, V>[] childArray = (nmNode<K, V>[]) new nmNode[ORDER];
    private DataItem<K, V>[] itemArray = (DataItem<K, V>[]) new DataItem[ORDER - 1];

   // kita tambahin cara untuk menentukan parentnya 
    public void setParent(nmNode<K, V> parent) {
      this.parent = parent;
   }

    public void connectChild(int childNum, nmNode<K, V> child) {
        childArray[childNum] = child;
        if (child != null)
            child.parent = this;
    }

    public nmNode<K, V> disconnectChild(int childNum) {
        nmNode<K, V> tempNode = childArray[childNum];
        childArray[childNum] = null;
        return tempNode;
    }

    public nmNode<K, V> getChild(int childNum) {
        return childArray[childNum];
    }

    public nmNode<K, V> getParent() {
        return parent;
    }

    public boolean isLeaf() {
        return (childArray[0] == null) ? true : false;
    }

    public int getNumItems() {
        return numItems;
    }

    public DataItem<K, V> getItem(int index) { // get DataItem at index
        return itemArray[index];
    }

    public boolean isFull() { 
        return (numItems == ORDER - 1) ? true : false;
    }

    public int findItem(K key) {
        for (int j = 0; j < ORDER - 1; j++) {
            if (itemArray[j] == null)
                break;
            else if (itemArray[j].getKey().compareTo(key) == 0)
                return j;
        }
        return -1;
    }

    //re-arrange items in a node after disconnect a child
    public void deleteItem(K key) {
        for (int j = 0; j < numItems; j++) {
            if (itemArray[j] == null)
                break;
            else if (itemArray[j].getKey().compareTo(key) == 0) {
                for (int k = j; k < numItems - 1; k++) {
                    itemArray[k] = itemArray[k + 1];
                }
                numItems--;
                break;
            }
        }
    }

    public int insertItem(DataItem<K, V> newItem) {
        numItems++;
        K newKey = newItem.getKey();

        for (int j = ORDER - 2; j >= 0; j--) {
            if (itemArray[j] == null)
                continue;
            else {
                K itsKey = itemArray[j].getKey();
                if (newKey.compareTo(itsKey) < 0)
                    itemArray[j + 1] = itemArray[j];
                else {
                    itemArray[j + 1] = newItem;
                    return j + 1;
                }
            }
        }
        itemArray[0] = newItem;
        return 0;
    }

    public DataItem<K, V> removeItem() {
        DataItem<K, V> temp = itemArray[numItems - 1];
        itemArray[numItems - 1] = null;
        numItems--;
        return temp;
    }

    public void displayNode() {
        for (int j = 0; j < numItems; j++)
            itemArray[j].displayItem();
        System.out.println("/");
    }
}

public class nmTree<K extends Comparable<K>, V> {
    private nmNode<K, V> root = new nmNode<>();

    public V find(K key) {
        nmNode<K, V> curNode = root;
        while (true) {
            int childNumber = curNode.findItem(key);
            if (childNumber != -1)
                return curNode.getItem(childNumber).getValue();
            else if (curNode.isLeaf())
                return null;
            else
                curNode = getNextChild(curNode, key);
        }
    }

    public void insert(K key, V value) {
        nmNode<K, V> curNode = root;
        DataItem<K, V> tempItem = new DataItem<>(key, value);

        while (true) {
            if (curNode.isLeaf())
                break;
            else
                curNode = getNextChild(curNode, key);
        }

        curNode.insertItem(tempItem);
        // dilanggar dahulu max items per nmNode dari 2 menjadi 3 
        if (curNode.isFull()) 
        {
            split(curNode); // split it
            curNode = curNode.getParent();
            // untuk setiap nmNode yang melanggar max items per nmNode
            while (curNode != null && curNode.isFull()){
                split(curNode); // split it
                curNode = curNode.getParent();
            }
        }
    }

    private nmNode<K, V> adoptMerge(nmNode<K, V> curNode, int index) {
        boolean left = true;
        nmNode<K, V> parentNode = curNode.getParent();
        if (parentNode == null) {
            if (curNode.getNumItems() == 0) {
                root = curNode.getChild(0);
                root.setParent (null);
            }
            return root;
        }

        int numItems = parentNode.getNumItems();
        int j = 0;
        nmNode<K, V> siblingNode = null;
        int curPos = 0;

        while (j < numItems && parentNode.getChild(j) != curNode) {
            siblingNode = parentNode.getChild(j);
            j++;
            curPos = j;
        }

        if (j == 0) {
            siblingNode = parentNode.getChild(j + 1);
            left = false;
        } else if (j < numItems) {
            siblingNode = parentNode.getChild(j + 1);
            if (siblingNode.getNumItems() > 1)
                left = false;
            else
                siblingNode = parentNode.getChild(j - 1);
        } else {
            siblingNode = parentNode.getChild(j - 1);
        }

        if (siblingNode.getNumItems() > 1) {
            if (left) {
                DataItem<K, V> parentItem = parentNode.getItem(j - 1);
                DataItem<K, V> siblingItem = siblingNode.getItem(siblingNode.getNumItems() - 1);

                curNode.getItem(index).setKey(parentItem.getKey());
                curNode.getItem(index).setValue(parentItem.getValue());
                parentItem.setKey(siblingItem.getKey());
                parentItem.setValue(siblingItem.getValue());

                if (!curNode.isLeaf()) {
                    nmNode<K, V> child = siblingNode.disconnectChild(siblingNode.getNumItems());
                    curNode.connectChild(curNode.getNumItems(), child);
                }
                siblingNode.removeItem();
            } else {
                DataItem<K, V> parentItem = parentNode.getItem(j);
                DataItem<K, V> siblingItem = siblingNode.getItem(0);

                curNode.getItem(index).setKey(parentItem.getKey());
                curNode.getItem(index).setValue(parentItem.getValue());
                parentItem.setKey(siblingItem.getKey());
                parentItem.setValue(siblingItem.getValue());

                if (!curNode.isLeaf()) {
                    nmNode<K, V> child = siblingNode.disconnectChild(0);
                    curNode.connectChild(0, child);
                }
                siblingNode.deleteItem(siblingItem.getKey());
            }
        } else {
            DataItem<K, V> parentItem = parentNode.getItem(Math.max(j - 1, 0));
            siblingNode.insertItem(parentItem);

            for (int i = 0; i < curNode.getNumItems(); i++) {
                siblingNode.insertItem(curNode.getItem(i));
            }

            for (int i = 0; i <= curNode.getNumItems(); i++) {
                nmNode<K, V> child = curNode.disconnectChild(i);
                if (child != null)
                    siblingNode.connectChild(siblingNode.getNumItems(), child);
            }

            parentNode.deleteItem(parentItem.getKey());
            parentNode.disconnectChild(curPos);

            for (int i = curPos; i < parentNode.getNumItems(); i++) {
                nmNode<K, V> child = parentNode.disconnectChild(i + 1);
                parentNode.connectChild(i, child);
            }

            if (parentNode.getNumItems() == 0) {
                if (parentNode == root) {
                    root = siblingNode;
                    siblingNode.setParent(null);
                } else {
                    adoptMerge(parentNode, 0);
                }
            }
        }

        return root;
    }
    public void delete(K key) {
      nmNode<K, V> curNode = root;
      while (true)
     {
         if (curNode.isLeaf())
            break;
         else
            curNode = getNextChild(curNode, key);
      }
      if(curNode.isLeaf()) { 
         if(curNode.getNumItems() > 1) 
            curNode.deleteItem(key);
         else {
            int index = curNode.findItem(key);
            root = adoptMerge(curNode, index);
         }   
      }
   }   
    private void split(nmNode<K, V> thisNode) {
        DataItem<K, V> itemB, itemC;
        nmNode<K, V> parent, child2, child3;
        int itemIndex;

        itemC = thisNode.removeItem();
        itemB = thisNode.removeItem();
        child2 = thisNode.disconnectChild(2);
        child3 = thisNode.disconnectChild(3);

        nmNode<K, V> newRight = new nmNode<>();

        if (thisNode == root) {
            root = new nmNode<>();
            parent = root;
            parent.connectChild(0, thisNode);
        } else {
            parent = thisNode.getParent();
        }

        itemIndex = parent.insertItem(itemB);
        int n = parent.getNumItems();

        for (int j = n - 1; j > itemIndex; j--) {
            nmNode<K, V> temp = parent.disconnectChild(j);
            parent.connectChild(j + 1, temp);
        }

        parent.connectChild(itemIndex + 1, newRight);
        newRight.insertItem(itemC);
        newRight.connectChild(0, child2);
        newRight.connectChild(1, child3);
    }

    private nmNode<K, V> getNextChild(nmNode<K, V> theNode, K key) {
        int j;
        int numItems = theNode.getNumItems();

        for (j = 0; j < numItems; j++) {
            if (key.compareTo(theNode.getItem(j).getKey()) < 0)
                return theNode.getChild(j);
        }
        return theNode.getChild(j);
    }

    public void displayTree() {
        recDisplayTree(root, 0, 0);
    }

    private void recDisplayTree(nmNode<K, V> thisNode, int level, int childNumber) {
        System.out.print("level=" + level + " child=" + childNumber + " ");
        thisNode.displayNode();

        int numItems = thisNode.getNumItems();
        for (int j = 0; j < numItems + 1; j++) {
            nmNode<K, V> nextNode = thisNode.getChild(j);
            if (nextNode != null)
                recDisplayTree(nextNode, level + 1, j);
        }
    }
}