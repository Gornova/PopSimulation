package it.randomtower.popsimulation;

import it.randomtower.popsimulation.model.Population;
import it.randomtower.popsimulation.model.PopulationType;
import it.randomtower.popsimulation.model.ResourceType;

public class PopulationBuilder {

	public static Population buildRural(int x, int y, TecnologyLevel tecnology) {
		Population pop = new Population();
		pop.name = "rural";
		pop.required = ResourceType.PRIMARY;
		pop.requiredValue = 1.0;
		pop.type = PopulationType.RURAL;
		pop.x = x;
		pop.y = y;
		pop.advanceTecnology(tecnology);
		return pop;
	}

	public static Population build(PopulationType type, int x, int y, TecnologyLevel tecnology) {
		switch (type) {
		case RURAL:
			return buildRural(x, y, tecnology);
		default:
			break;
		}
		throw new IllegalArgumentException("unsupported type");
	}

}
