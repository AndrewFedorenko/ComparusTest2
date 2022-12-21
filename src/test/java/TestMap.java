import comparus.test.LongMap;
import comparus.test.LongMapImpl;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class TestMap {
    @Test
    public void test(){
        int testDimension = 10000;

        LongMap<Integer> lmi = new LongMapImpl<>();
        assertTrue(lmi.isEmpty());

        // putting
        for (int i = 0; i < testDimension; i++) {
            assertEquals((int) lmi.put(i, i), i);
            assertTrue(lmi.containsKey(i));
            assertTrue(lmi.containsValue(i));
        }
        assertFalse(lmi.isEmpty());
        assertEquals(lmi.size(), testDimension);
        // get and remove
        for (int i = 0; i < testDimension; i++) {
            if (lmi.containsKey(i)){
                assertEquals((int) lmi.get(i), i);
                assertEquals((int) lmi.remove(i),i);
            }
            assertFalse(lmi.containsKey(i));
            assertFalse(lmi.containsValue(i));
        }
        assertEquals(lmi.size(), lmi.keys().length);
        // put
        assertEquals(12, (int) lmi.put(2, 12));

    }
}
