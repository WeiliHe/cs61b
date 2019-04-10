import org.junit.Test;
import static org.junit.Assert.*;

/*
@author Felix 
*/
public class TestUnionFind {
	@Test
	public void testConnected() {
		UnionFind Set = new UnionFind(10);
		assertFalse(Set.connected(0, 1));
		Set.union(0, 1);
		assertTrue(Set.connected(0, 1));
		assertEquals(2, Set.sizeOf(0));

		Set.union(2, 3);
		Set.union(4, 5);
		Set.union(6, 7);
		Set.union(8, 9);

		Set.union(1, 3);
		assertTrue(Set.connected(0, 2));
		assertEquals(4, Set.sizeOf(2));
		assertEquals(0, Set.parent(2));
	}

	@Test
	public void testConnectedPathComprs() {
		UnionFindPathCompress Set = new UnionFindPathCompress(10);

		Set.union(0, 1);
		Set.union(2, 3);
		Set.union(4, 5);
		Set.union(6, 7);
		Set.union(8, 9);

		Set.union(1, 3);
		assertEquals(4, Set.sizeOf(2));
		Set.union(7, 8);
		Set.union(3, 9);
		assertTrue(Set.connected(0, 2));
		assertEquals(8, Set.sizeOf(2));
		assertEquals(0, Set.parent(2));
		assertEquals(0, Set.parent(3));
		assertEquals(6, Set.parent(9));
	}
}
