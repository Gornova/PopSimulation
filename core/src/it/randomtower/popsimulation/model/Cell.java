package it.randomtower.popsimulation.model;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * A cell is basic unit in world map
 */
public class Cell {

	public String name;
	public int x;
	public int y;
	public Resource primary;
	public Resource secondary;
	public Resource tertiary;
	public TextureRegion currentTexture;
	public TextureRegion textureNormal;
	public boolean consumed = false;
	public TextureRegion textureConsumed;

	private List<Population> ruralPops = new ArrayList<>();
	private List<Population> urbanPops = new ArrayList<>();
	private List<Population> rulingPops = new ArrayList<>();
	private List<Population> toRemove = new ArrayList<>();
	private List<Population> toAdd = new ArrayList<>();

	public void update(World world) {
		for (Population pop : toAdd) {
			switch (pop.type) {
			case RURAL:
				ruralPops.add(pop);
				break;
			case URBAN:
				urbanPops.add(pop);
				break;
			case RULING:
				rulingPops.add(pop);
				break;
			default:
				break;
			}
		}
		toAdd.clear();
		primary.update();
		secondary.update();
		tertiary.update();
		for (Population pop : ruralPops) {
			int c = pop.consume(this);
			if (c == 0) {
				// TODO
				// no more resources! nomadic people move randomly, agricolture
				// attack others ?
				world.moveRandom(pop);
				pop.stability = 0;
			}

		}
		if (primary.value <= 20) {
			consumed = true;
		} else {
			consumed = false;
		}
		if (!toRemove.isEmpty()) {
			for (Population removablePop : toRemove) {
				switch (removablePop.type) {
				case RURAL:
					ruralPops.remove(removablePop);
					break;
				case URBAN:
					urbanPops.remove(removablePop);
					break;
				case RULING:
					rulingPops.remove(removablePop);
					break;
				default:
					break;
				}
			}
			toRemove.clear();
		}
		ruralPops.removeIf(r -> r.death);
		ruralPops.forEach(r -> r.update(world));
	}

	public void add(Population pop) {
		toAdd.add(pop);
	}

	public boolean hasPop() {
		return !ruralPops.isEmpty() || !urbanPops.isEmpty() || !rulingPops.isEmpty();
	}

	public boolean isRural() {
		return ruralPops.size() > urbanPops.size() && ruralPops.size() > rulingPops.size();
	}

	public List<Population> getRurals() {
		return ruralPops;
	}

	public void remove(Population pop) {
		toRemove.add(pop);
	}

	public void draw(SpriteBatch batch, int x, int y) {
		if (!consumed) {
			batch.draw(textureNormal, x, y);
		} else {
			batch.draw(textureNormal, x, y);
			batch.draw(textureConsumed, x, y);
		}

	}

}
