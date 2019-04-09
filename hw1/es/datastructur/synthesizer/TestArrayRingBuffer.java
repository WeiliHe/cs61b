package es.datastructur.synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void testEquals() {
        ArrayRingBuffer arb = new ArrayRingBuffer(10);
        ArrayRingBuffer bra = new ArrayRingBuffer(11);
        bra.enqueue(0.5);
        assertFalse(arb.equals(bra));
        arb.enqueue(0.5);
        assertTrue(arb.equals(bra));
    }
}
