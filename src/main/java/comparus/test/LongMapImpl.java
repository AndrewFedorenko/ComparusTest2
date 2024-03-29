package comparus.test;

/**
 * appears analog of hashMap with keys of fixed type "long"
 * Includes hash table containing in each cell list of pairs<key-value>.
 */
public class LongMapImpl<V> implements LongMap<V>{
    private final int HASH_TABLE_DIMENSION = 16;
    private final float FILL_KOEF_MIN = 0.01f;
    private final float FILL_KOEF_MAX = 1f;
    private int keysNumber = 0;
    private Node<V>[] table;
    private static class Node<V>{
        private final long key;
        private V value;
        private Node<V> nextItem;
        Node(long key, V value, Node<V> nextItem){
            this.key = key;
            this.value = value;
            this.nextItem = nextItem;
        }
    }
    public LongMapImpl(){
    }
    private void initTable(){
        table = (Node<V>[]) new Node[HASH_TABLE_DIMENSION];
    }
    private int hash(long key){
        return (int)key% table.length;
    }
    private void expandTable(){
        processTable(table.length*2);
    }
    private void compressTable(){
        if (table.length == HASH_TABLE_DIMENSION)
            return;
        int curLen;
        if (keysNumber == 0){
            table = (Node<V>[]) new Node[HASH_TABLE_DIMENSION];
            return;
        } else {
            curLen = table.length;
            while (curLen > HASH_TABLE_DIMENSION && curLen / keysNumber > FILL_KOEF_MAX) {
                curLen = curLen / 2;
            }
        }
        processTable(curLen);
    }
    /**
     * resorts table according new hash
     */
    private void processTable(int newSize){
        int curHash;
        Node<V>[] oldTable = table;
        table = (Node<V>[]) new Node[newSize];
        for (int i = 0; i < oldTable.length; i++) {
            if(oldTable[i] != null){
                Node<V> item = oldTable[i];
                while (item != null){
                    curHash = hash(item.key);
                    table[curHash] = new Node<>(item.key, item.value, table[curHash]);
                    item = item.nextItem;
                }
            }
        }
    }
    /**
     * if key already exists, changes Values node of corresponding to given key index of hash table
     */
    public V put(long key, V value) {
        if (table == null){
            initTable();
        } else {
            if(keysNumber != 0 && (float) table.length / keysNumber<FILL_KOEF_MIN){
                expandTable();
            }
        }

        int currentHash = hash(key);

        Node<V> item = table[currentHash];
        while (item != null){
            if(item.key == key){
                V oldValue = item.value;
                item.value = value;
                return oldValue;
            }
            item = item.nextItem;
        }
        table[currentHash] = new Node<>(key, value, table[currentHash]);
        keysNumber++;
        return null;
    }
    public V get(long key) {
        if(table == null)
            return null;

        int currentHash = hash(key);

        Node<V> item = table[currentHash];
        while (item != null){
            if(item.key == key){
                return item.value;
            }
            item = item.nextItem;
        }
        return null;
    }
    /**
     * create hash and look into hash table cell indexed as hash
     * if cell is null, return null
     * else try to find key in list within cell
     * if found - remove, else return null
     */
    public V remove(long key) {
        if(table == null)
            return null;
        int currentHash = hash(key);
        Node<V> item = table[currentHash];
        Node<V> prevItem = null;
        while (item != null){
            if(item.key == key){
                V val = item.value;
                if(prevItem == null){
                    table[currentHash] = item.nextItem;
                } else {
                    prevItem.nextItem = item.nextItem;
                }
                keysNumber--;
                if(keysNumber == 0||(float)table.length/keysNumber>FILL_KOEF_MAX){
                    compressTable();
                }
                return val;
            }
            prevItem = item;
            item = item.nextItem;
        }
        return null;
    }
    public boolean isEmpty() {
        return keysNumber == 0;
    }
    public boolean containsKey(long key) {
        if (table == null)
            return false;

        int currentHash = hash(key);
        if(table[currentHash] == null)
            return false;
        Node<V> item = table[currentHash];
        while (item != null) {
            if (item.key == key)
                return true;
            item = item.nextItem;
        }
        return false;
    }
    public boolean containsValue(V value) {
        if (table == null){
            return false;
        }
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                Node<V> item = table[i];
                while (item != null) {
                    if (item.value == null && value == null || item.value != null && item.value.equals(value))
                        return true;
                    item = item.nextItem;
                }
            }
        }
        return false;
    }
    public long[] keys() {
        long[] keysTable = new long[keysNumber];
        if (table != null) {
            int k = 0;
            for (Node<V> vNode : table) {
                if (vNode != null) {
                    Node<V> item = vNode;
                    while (item != null) {
                        if (k > (keysNumber - 1)) {
                            System.out.println(" " + k + " out of bounds");
                        } else {
                            keysTable[k] = item.key;
                        }
                        item = item.nextItem;
                        k++;
                    }
                }
            }
        }
        return keysTable;
    }
    public V[] values() {
        V[]valuesTable = (V[]) new Object[keysNumber];
        if (table != null) {
            int k = 0;
            for (Node<V> vNode : table) {
                if (vNode != null) {
                    Node<V> item = vNode;
                    while (item != null) {
                        valuesTable[k] = item.value;
                        item = item.nextItem;
                        k++;
                    }
                }
            }
        }
        return valuesTable;
    }
    public long size() {
        return keysNumber;
    }
    public void clear() {
        table=null;
        keysNumber = 0;
    }
    @Override
    public String toString(){
        if (table == null){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Node<V> vNode : table) {
            if (vNode != null) {
                Node<V> item = vNode;
                while (item != null) {
                    sb.append("[")
                            .append(item.key)
                            .append("->")
                            .append(item.value == null ? "null" : item.value.toString())
                            .append("]");
                    item = item.nextItem;
                }
            }
        }
        return sb.toString();
    }
}