package it.randomtower.popsimulation;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

import it.randomtower.popsimulation.model.Cell;
import it.randomtower.popsimulation.model.World;

public class PopSimulationGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	private Texture texture;
	private World world;
	private Vector3 mousePos = new Vector3(0, 0, 0);
	private Vector3 selectedPos = new Vector3(0, 0, 0);
	private Texture selectedSquare;
	private OrthographicCamera cam;
	private boolean drawSelected;
	private BitmapFont font;
	private TextureRegion ruralTexture;
	private Calendar calendar;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	@Override
	public void create() {
		batch = new SpriteBatch();
		// init graphics
		texture = new Texture(Gdx.files.internal("World_Addl.png"));
		selectedSquare = new Texture(Gdx.files.internal("goldSquare.png"));
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
		cam.update();
		font = new BitmapFont();
		// world
		world = WorldBuilder.build(10, 10, new TextureRegion(texture, 0, 48 * 8, 48, 48),
				new TextureRegion(texture, 48 * 2, 48 * 2, 48, 48));
		ruralTexture = new TextureRegion(texture, 48 * 10, 48 * 5, 48, 48);
		Timer.schedule(new Task() {

			@Override
			public void run() {
				world.update();
				calendar.add(Calendar.DAY_OF_MONTH, 1);
			}
		}, 0, 0.1f);
		calendar = Calendar.getInstance();
		calendar.set(0, 0, 0, 0, 0, 0);
	}

	@Override
	public void render() {
		mousePos.x = Gdx.input.getX();
		mousePos.y = Gdx.input.getY();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(cam.combined);
		cam.update();
		batch.begin();
		batch.enableBlending();
		for (int i = 0; i < world.w; i++) {
			for (int j = 0; j < world.h; j++) {
				Cell cell = world.map[i][j];
				cell.draw(batch, 48 * 3 + i * 48, 48 * 3 + j * 48);
				if (cell.hasPop() && cell.isRural()) {
					batch.draw(ruralTexture, 48 * 3 + i * 48, 48 * 3 + j * 48);
				}
			}
		}
		if (drawSelected) {
			batch.draw(selectedSquare, (selectedPos.x + 3) * 48, (selectedPos.y + 3) * 48);
			CellInfo.draw(batch, font, world.map[(int) selectedPos.x][(int) selectedPos.y]);
		}
		font.draw(batch, "" + sdf.format(calendar.getTime()), 40, 720);

		batch.end();

		if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
			drawSelected = true;
			cam.unproject(mousePos);
			selectedPos.x = Math.min(9, Math.max(0, (int) (mousePos.x / 48) - 3));
			selectedPos.y = Math.min(9, Math.max(0, (int) (mousePos.y / 48) - 3));
		}
	}

	@Override
	public void dispose() {
		batch.dispose();
	}
}
