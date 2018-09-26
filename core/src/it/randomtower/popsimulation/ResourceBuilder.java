package it.randomtower.popsimulation;

import it.randomtower.popsimulation.model.Resource;
import it.randomtower.popsimulation.model.ResourceType;

public class ResourceBuilder {

	public static Resource buildPrimary() {
		Resource res = new Resource();
		res.name = "primary";
		res.value = 100;
		res.maxValue = 100;
		res.increment = 0.01;
		res.type = ResourceType.PRIMARY;
		return res;
	}

	public static Resource buildSecondary() {
		Resource res = new Resource();
		res.name = "secondary";
		res.value = 0;
		res.maxValue = 100;
		res.increment = 0.00;
		res.type = ResourceType.SECONDARY;
		return res;
	}

	public static Resource buildTertiary() {
		Resource res = new Resource();
		res.name = "tertiary";
		res.value = 0;
		res.maxValue = 100;
		res.increment = 0.00;
		res.type = ResourceType.TERTIARY;
		return res;
	}

}
