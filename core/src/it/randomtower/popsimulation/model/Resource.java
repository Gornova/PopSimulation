package it.randomtower.popsimulation.model;

public class Resource {

	public String name;
	public double value;
	public double maxValue;
	public double increment;
	public ResourceType type;

	public void update() {
		// normal growth
		if (value < maxValue) {
			value += increment;
		}

	}

}
