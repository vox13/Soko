package com.bfg9000.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;

public class InputHandler implements InputProcessor {
    private Player player;
    private Camera camera;


    public InputHandler(Player player, Camera cam) {
        this.player = player;
        this.camera = cam;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                player.move(Direction.LEFT);
                break;
            case Input.Keys.RIGHT:
                player.move(Direction.RIGHT);
                break;
            case Input.Keys.UP:
                player.move(Direction.UP);
                break;
            case Input.Keys.DOWN:
                player.move(Direction.DOWN);
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 vec = camera.unproject(new Vector3(screenX, screenY, 0));

        player.move(vec);

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
