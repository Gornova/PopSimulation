package it.randomtower.popsimulation;

import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

import it.randomtower.popsimulation.model.Cell;
import it.randomtower.popsimulation.model.Population;
import it.randomtower.popsimulation.model.Resource;

// draw cell info
public class CellInfo {

	private static int sx = 48 * 14;
	private static int sy = 48 * 13;

	public static void draw(Batch batch, BitmapFont font, Cell cell) {
		font.draw(batch, "Position " + cell.x + "," + cell.y, sx, sy);
		font.draw(batch, "Resources", sx, sy - 20);
		drawResources(batch, font, cell.primary, sx, sy - 40);
		drawResources(batch, font, cell.secondary, sx, sy - 60);
		drawResources(batch, font, cell.tertiary, sx, sy - 80);

		if (cell.hasPop()) {
			font.draw(batch, "Population", sx, sy - 100);
			drawRural(batch, font, cell.getRurals(), sx, sy - 140);
		}
	}

	private static void drawRural(Batch batch, BitmapFont font, List<Population> rurals, int x, int y) {
		String txt = "Rurals pop " + rurals.size();
		font.draw(batch, txt, x + getPositionOffset(font, txt), y);
		double tot = rurals.stream().mapToDouble(r -> r.requiredValue).sum();
		txt = "Required resource (sum): primary , " + tot + "/day";
		font.draw(batch, txt, x + getPositionOffset(font, txt), y - 20);
		txt = "Vitality (avg) :" + rurals.stream().mapToInt(r -> r.vitality).average().getAsDouble();
		font.draw(batch, txt, x + getPositionOffset(font, txt), y - 40);
		txt = "Technology :"
				+ rurals.stream().map(r -> r.getTecnology().toString()).distinct().collect(Collectors.toList());
		font.draw(batch, txt, x + getPositionOffset(font, txt), y - 60);
		OptionalInt max = rurals.stream().mapToInt(r -> r.stability).max();
		if (max.isPresent()) {
			txt = "Stability (max): " + max.getAsInt();
		} else {
			txt = "Stability (max): ";
		}
		font.draw(batch, txt, x + getPositionOffset(font, txt), y - 80);
	}

	private static void drawResources(Batch batch, BitmapFont font, Resource res, int x, int y) {
		String txt = res.name + " : " + Math.floor(res.value * 1000) / 1000d + "/" + res.maxValue + "  +"
				+ res.increment + "/day";
		font.draw(batch, txt, x + getPositionOffset(font, txt), y);
	}

	private static float getPositionOffset(BitmapFont bitmapFont, String value) {
		GlyphLayout glyphLayout = new GlyphLayout();
		glyphLayout.setText(bitmapFont, value);
		return 300 - glyphLayout.width;
	}

}
