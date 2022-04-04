package model.world;

import java.awt.Point;

public class Cover {

	private int currentHP;
	private Point location;

	public Cover(int x, int y) {
		location = new Point(x, y);
		currentHP = (int)(Math.random()*899)+100;
	}

	public int getCurrentHP() {
		return currentHP;
	}

	public void setCurrentHP(int currentHP) {
		this.currentHP = currentHP;
	}

	public Point getLocation() {
		return location;
	}
}
