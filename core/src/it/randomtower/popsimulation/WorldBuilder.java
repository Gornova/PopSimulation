package it.randomtower.popsimulation;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import it.randomtower.popsimulation.model.Population;
import it.randomtower.popsimulation.model.World;

public class WorldBuilder {

	public static World build(int w, int h, TextureRegion grass, TextureRegion desert) {
		World world = new World(10, 10);
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				world.map[i][j] = CellBuilder.build("", i, j, grass, desert);
			}
		}
		int x = 5;
		int y = 5;
		Population rural = PopulationBuilder.buildRural(x, y, TecnologyLevel.NOMAD);
		world.map[x][y].add(rural);
		Population rural2 = PopulationBuilder.buildRural(x, y, TecnologyLevel.AGRICOLTURE);
		world.map[x][y].add(rural2);
		return world;
	}

}
