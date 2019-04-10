import org.junit.Test;
import static org.junit.Assert.*;

/*
@author Felix 
*/
public class TestUnionFind {
	@Test
	public void testConnected() {
		UnionFind set = new UnionFind(10);
		assertFalse(set.connected(1, 0));
		set.union(1, 0);
		assertTrue(set.connected(0, 1));
		assertEquals(2, set.sizeOf(0));

		set.union(3, 2);
		set.union(5, 4);
		set.union(7, 6);
		set.union(9, 8);

		set.union(3, 1);
		assertTrue(set.connected(0, 2));
		assertEquals(4, set.sizeOf(2));
		assertEquals(0, set.parent(2));
	}

	@Test
	public void testConnectedPathComprs() {
		UnionFindPathCompress set = new UnionFindPathCompress(10);

		set.union(1, 0);
		set.union(3, 2);
		set.union(5, 4);
		set.union(7, 6);
		set.union(9, 8);

		set.union(3, 1);
		assertEquals(4, set.sizeOf(2));
		set.union(8, 7);
		set.union(9, 3);
		assertTrue(set.connected(0, 2));
		assertEquals(8, set.sizeOf(2));
		assertEquals(0, set.parent(2));
		assertEquals(0, set.parent(3));
		assertEquals(6, set.parent(9));
	}
}
