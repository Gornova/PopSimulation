package it.randomtower.popsimulation;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import it.randomtower.popsimulation.model.Cell;

public class CellBuilder {

	public static Cell build(String name, int x, int y, TextureRegion texture, TextureRegion textureConsumed) {
		Cell cell = new Cell();
		cell.name = name;
		cell.x = x;
		cell.y = y;
		cell.primary = ResourceBuilder.buildPrimary();
		cell.secondary = ResourceBuilder.buildSecondary();
		cell.tertiary = ResourceBuilder.buildTertiary();
		cell.currentTexture = texture;
		cell.textureNormal = texture;
		cell.textureConsumed = textureConsumed;
		return cell;
	}

}
