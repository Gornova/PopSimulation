package it.randomtower.popsimulation.model;

import java.util.Random;

import it.randomtower.popsimulation.PopulationBuilder;
import it.randomtower.popsimulation.TecnologyLevel;

public class Population {

	public String name;
	public PopulationType type;
	public ResourceType required;
	public double requiredValue;
	public int vitality = 100;
	public int vitalityBonus = 1;
	public int maxVitality = 100;
	public int x;
	public int y;
	public boolean death;
	public int stability;
	public int maxStability = 100;
	private TecnologyLevel tecnology;

	private Random rnd = new Random();

	/**
	 * @return > 0 if okay, 0 if problem, -1 if death
	 */
	public int consume(Cell cell) {
		// consume required resource type from this cell
		switch (required) {
		case PRIMARY:
			if (cell.primary.value - requiredValue >= 0) {
				cell.primary.value -= requiredValue;
				if (vitality + vitalityBonus <= maxVitality) {
					vitality += vitalityBonus;
				}
				return 1;
			}
			// suffer damage
			vitality -= 5;
			if (vitality <= 0) {
				death = true;
				return -1;
			}
			return 0;
		case SECONDARY:
			break;
		case TERTIARY:
			break;
		default:
			break;
		}
		return 1;
	}

	public void advanceTecnology(TecnologyLevel tecnology) {
		this.tecnology = tecnology;
		switch (this.getTecnology()) {
		case AGRICOLTURE:
			vitalityBonus = 3;
			requiredValue -= 0.5;
			break;

		default:
			break;
		}

	}

	public TecnologyLevel getTecnology() {
		return tecnology;
	}

	public void update(World world) {
		if (stability + 1 <= maxStability) {
			stability++;
		}
		if (stability >= 80 && rnd.nextFloat() < 0.10) {
			world.map[x][y].add(PopulationBuilder.build(type, x, y, tecnology));
			stability = stability / 2;
		}
	}

}
