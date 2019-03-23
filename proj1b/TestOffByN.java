import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    @Test
    public void testEqualsChars() {
        CharacterComparator offbyn = new OffByN(5);
    	assertTrue(offbyn.equalChars('a', 'f'));
    	assertTrue(offbyn.equalChars('f', 'a'));
    	assertFalse(offbyn.equalChars('a', 'b'));
    }
    // Your tests go here.
} 