package utility.geometry;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PointTest {

	@Test
	public void manhattenDistanceTest() {
		Point p1 = new Point(0, 10);
		Point p2 = new Point(5, -1);
		
		int distanceP1P2 = p1.manhattanDistance(p2);
		int distanceP2P1 = p2.manhattanDistance(p1);
		
		assertEquals(distanceP1P2, 16);
		assertEquals(distanceP2P1, 16);
	}

}
