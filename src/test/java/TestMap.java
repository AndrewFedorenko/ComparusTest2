import comparus.test.LongMap;
import comparus.test.LongMapImpl;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestMap {
    int testDimension = 10000;
    LongMap<Integer> lmi;
    void testPutIsEmpty(){
        assertTrue(lmi.isEmpty());
        for (int i = 0; i < testDimension; i++) {
            lmi.put(i, i);
            assertTrue(lmi.containsKey(i));
            assertTrue(lmi.containsValue(i));
        }
        assertFalse(lmi.isEmpty());
        assertEquals(lmi.size(), testDimension);
    }
    void testContainsKeyValue(){
        for (int i = 0; i < testDimension; i++) {
            assertTrue(lmi.containsKey(i));
            assertTrue(lmi.containsValue(i));
        }
    }
    void testGet(){
        for (int i = 0; i < testDimension; i++) {
            if (lmi.containsKey(i)){
                assertEquals((int) lmi.get(i), i);
            }
        }
    }
    void testRemove(){
        for (int i = 0; i < testDimension; i++) {
            if (lmi.containsKey(i)){
                assertEquals((int) lmi.remove(i),i);
            }
            assertFalse(lmi.containsKey(i));
            assertFalse(lmi.containsValue(i));
        }
    }
    void testClear(){
        lmi.clear();
        assertEquals(lmi.size(),0);
    }
    @Test
    public void test(){

        lmi = new LongMapImpl<>();

        testPutIsEmpty();
        testContainsKeyValue();
        testGet();
        testRemove();
        testClear();

    }
}
