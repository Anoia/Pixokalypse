package potentialField;

import java.util.HashSet;

import util.GridPoint2;

public class CircleDynamicPotentialField extends DynamicPotentialField {

	public static void main(String[] arg) {
		CircleDynamicPotentialField c = new CircleDynamicPotentialField(9, true);
		c.printASCII();
	}
	public Boolean pushing;

	public int radius;

	public CircleDynamicPotentialField(int radius, Boolean pushing) {
		super(radius * 2 + 1, radius * 2 + 1);
		this.pushing = pushing;
		this.radius = radius;
		createCircle(radius);
	}

	private void createCircle(int radius) {

		GridPoint2 center = new GridPoint2(radius, radius);

		HashSet<GridPoint2> done = new HashSet<GridPoint2>();
		HashSet<GridPoint2> todo;

		fieldArray[center.x][center.y] = pushing ? radius : 0;
		done.add(center);

		for (int i = 1; i <= radius; i++) {
			todo = getEmptyNeighbours(done);
			setAllPoints(todo, i);
			done = todo;
		}

		// temp
		for (int i = 0; i < fieldArray.length; i++) {
			for (int j = 0; j < fieldArray[0].length; j++) {
				if (fieldArray[i][j] == -1) {
					fieldArray[i][j] = 0;
				}
			}
		}

	}

	private HashSet<GridPoint2> getEmptyNeighbours(HashSet<GridPoint2> points) {
		HashSet<GridPoint2> neighbours = new HashSet<GridPoint2>();

		for (GridPoint2 point : points) {
			if (fieldArray[point.x - 1][point.y] == -1) {
				neighbours.add(new GridPoint2(point.x - 1, point.y));
			}
			if (fieldArray[point.x][point.y - 1] == -1) {
				neighbours.add(new GridPoint2(point.x, point.y - 1));
			}
			if (fieldArray[point.x + 1][point.y] == -1) {
				neighbours.add(new GridPoint2(point.x + 1, point.y));
			}
			if (fieldArray[point.x][point.y + 1] == -1) {
				neighbours.add(new GridPoint2(point.x, point.y + 1));
			}
		}

		return neighbours;
	}

	public void printASCII() {
		for (int i = 0; i < fieldArray.length; i++) {
			System.out.println("");
			for (int j = 0; j < fieldArray[i].length; j++) {
				System.out.print(fieldArray[i][j]);
			}
		}
	}

	private void setAllPoints(HashSet<GridPoint2> points, int value) {
		value = pushing ? radius - value : value;
		for (GridPoint2 point : points) {
			fieldArray[point.x][point.y] = value;
		}
	}

}
