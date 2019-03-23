import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offbyone = new OffByOne();
    @Test
    public void testEqualsChars() {
    	assertTrue(offbyone.equalChars('x', 'y'));
    	assertFalse(offbyone.equalChars('j', 'y'));
    	assertTrue(offbyone.equalChars('&', '%'));
    }
    // Your tests go here.
} 

/** Uncomment this class once you've created your CharacterComparator interface and OffByOne class. **/