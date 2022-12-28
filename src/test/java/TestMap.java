import comparus.test.LongMap;
import comparus.test.LongMapImpl;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestMap {
    int testDimension = 10000;
    LongMap<Integer> lmi = new LongMapImpl<>();
    void fillMap(){
        if (lmi.size() == 0){
            for (int i = 0; i < testDimension; i++) {
                lmi.put(i, i);
            }
        }
    }
    @Test
    public void testPutIsEmpty(){
        if (lmi.size() != 0){
            lmi.clear();
        }
        assertTrue(lmi.isEmpty());
        for (int i = 0; i < testDimension; i++) {
            lmi.put(i, i);
            assertTrue(lmi.containsKey(i));
            assertTrue(lmi.containsValue(i));
        }
        assertFalse(lmi.isEmpty());
        assertEquals(lmi.size(), testDimension);
    }
    @Test
    public void testContainsKeyValue(){
        fillMap();
        lmi.put(testDimension, null);
        for (int i = 0; i < testDimension; i++) {
            assertTrue(lmi.containsKey(i));
            assertTrue(lmi.containsValue(i));
        }
    }
    @Test
    public void testGet(){
        fillMap();
        for (int i = 0; i < testDimension; i++) {
            if (lmi.containsKey(i)){
                assertEquals((int) lmi.get(i), i);
            }
        }
    }
    @Test
    public void testRemove(){
        fillMap();
        for (int i = 0; i < testDimension; i++) {
            if (lmi.containsKey(i)){
                assertEquals((int) lmi.remove(i),i);
            }
            assertFalse(lmi.containsKey(i));
            assertFalse(lmi.containsValue(i));
        }
    }
    @Test
    public void testClear(){
        lmi.clear();
        assertEquals(lmi.size(),0);
    }
}
