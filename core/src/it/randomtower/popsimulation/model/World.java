package it.randomtower.popsimulation.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World {

	public Cell[][] map;
	public int w;
	public int h;
	private Random rnd = new Random();

	public enum DIR {
		LEFT, RIGHT, UP, DOWN, NONE
	}

	public World(int w, int h) {
		map = new Cell[w][h];
		this.w = w;
		this.h = h;
	}

	public void update() {
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				map[i][j].update(this);
			}
		}

	}

	public void moveRandom(Population pop) {
		List<DIR> values = new ArrayList<DIR>();
		randomMove(values, pop.x, pop.y);
		int tx;
		int ty;
		if (values.size() > 0) {
			DIR value = values.get(rnd.nextInt(values.size()));
			switch (value) {
			case LEFT:
				tx = pop.x - 1;
				ty = pop.y;
				map[pop.x][pop.y].remove(pop);
				map[tx][ty].add(pop);
				pop.x = tx;
				pop.y = ty;
				break;
			case RIGHT:
				tx = pop.x + 1;
				ty = pop.y;
				map[pop.x][pop.y].remove(pop);
				map[tx][ty].add(pop);
				pop.x = tx;
				pop.y = ty;
				break;
			case DOWN:
				tx = pop.x;
				ty = pop.y - 1;
				map[pop.x][pop.y].remove(pop);
				map[tx][ty].add(pop);
				pop.x = tx;
				pop.y = ty;
				break;
			case UP:
				tx = pop.x;
				ty = pop.y + 1;
				map[pop.x][pop.y].remove(pop);
				map[tx][ty].add(pop);
				pop.x = tx;
				pop.y = ty;
				break;

			default:
				break;
			}
			return;
		}
	}

	private List<DIR> randomMove(List<DIR> values, int cx, int cy) {
		if (canMove(cx - 1, cy)) {
			values.add(DIR.LEFT);
		}
		if (canMove(cx + 1, cy)) {
			values.add(DIR.RIGHT);
		}
		if (canMove(cx, cy - 1)) {
			values.add(DIR.DOWN);
		}
		if (canMove(cx, cy + 1)) {
			values.add(DIR.UP);
		}
		return values;
	}

	private boolean canMove(int tx, int ty) {
		// check world limit
		if (tx < 0 || tx >= w || ty < 0 || ty >= h) {
			return false;
		}
		return true;
	}

}
