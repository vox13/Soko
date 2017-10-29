package com.bfg9000.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SokoGame extends ApplicationAdapter {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private FileHandle levelFile;
	private int currentLevel = 0;
	SokoLevelCollection gameLevels;
	Player player = new Player();
	
	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch = new SpriteBatch();
		batch.setProjectionMatrix(camera.combined);

		Globals.texPlayer = new Texture("Player.png");
		player.setTexture(Globals.texPlayer);
		Globals.texWall = new Texture("Wall.png");
		Globals.texGround = new Texture("GroundGravel_Concrete.png");
		Globals.texBox = new Texture("Crate_Brown.png");
		Globals.texDark_box = new Texture("CrateDark_Brown.png");
		Globals.texEndpoint = new Texture("EndPoint_Brown.png");


		levelFile = Gdx.files.internal("maps/Pufiban.txt");
//		levelFile = Gdx.files.internal("maps/level1.txt");
		String textData = levelFile.readString();

		gameLevels = new SokoLevelCollection();
		gameLevels.Parse(textData);

		currentLevel = 12;

        gameLevels.printLevel(currentLevel);
		gameLevels.getLevel(currentLevel).setScreenXY(0,0);
		player.setMap(gameLevels.getLevel(currentLevel));
		player.debugPrint();

		Gdx.input.setInputProcessor(new InputHandler(player, camera));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		gameLevels.getLevel(currentLevel).render(batch);
		player.render(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		Globals.texPlayer.dispose();
		Globals.texWall.dispose();
		Globals.texGround.dispose();
		Globals.texBox.dispose();
		Globals.texDark_box.dispose();
		Globals.texEndpoint.dispose();
		batch.dispose();
	}
}
