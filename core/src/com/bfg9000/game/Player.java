package com.bfg9000.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class Player {
    private int x;
    private int y;
    SokoLevel map;
    private Texture texture;
    private Sprite sprite;

    private boolean testBounds(Direction dir) {
        int newX, newY;
        switch (dir) {
            case LEFT:
                newX = x - 1;
                if (newX < 0)
                    return false;
                break;
            case RIGHT:
                newX = x + 1;
                if (newX > map.getWidth())
                    return false;
                break;
            case UP:
                newY = y - 1;
                if (newY < 0)
                    return false;
                break;
            case DOWN:
                newY = y + 1;
                if (newY > map.getHeight())
                    return false;
                break;
        }
        return true;
    }

    private boolean checkBounds(int x, int y) {
        if (x < 0)
            return false;
        else if (x > map.getWidth())
            return false;

        if (y < 0)
            return false;
        else if (y > map.getHeight())
            return false;

        return  true;
    }

    public void setTexture(Texture texture) {
        sprite = new Sprite(texture);
        sprite.flip(false, true);
        this.texture = texture;
    }

    public void setMap(SokoLevel map) {
        x = map.getPlayerPosX();
        y = map.getPlayerPosY();
        this.map = map;
    }

    public void move(Vector3 vec) {
        int mX = (int) ((vec.x - map.getScreenX()) / 64);
        int mY = (int) ((vec.y - map.getScreenY()) / 64);

        int dX = (int) Math.signum(mX - this.x);
        int dY = (int) Math.signum(mY - this.y);

        Gdx.app.log("SOKO", "x= " + mX + " y= " + mY);
        Gdx.app.log("SOKO", "dx= " + dX + " dy= " + dY);

        switch (dX) {
            case 1:
                move(Direction.RIGHT);
                break;
            case -1:
                move(Direction.LEFT);
                break;
        }

        switch (dY) {
            case 1:
                move(Direction.DOWN);
                break;
            case -1:
                move(Direction.UP);
                break;
        }


    }


    public void move(Direction direction) {
        int nX, nY;
        MapCell cell;
        switch (direction) {
            case LEFT:
                nX = x - 1;
                if ( testBounds(Direction.LEFT) ) {
                cell = map.getCell(nX, y);
                if (cell.obj1 != Globals.ID_WALL) {
                    if (cell.obj2 == Globals.ID_BOX) {
                        if (checkBounds(nX - 1, y)) {
                            MapCell cell2 = map.getCell(nX - 1, y);
                            if (map.moveBox(cell, cell2))
                                this.x = nX;
                        }
                    } else
                        this.x = nX;
                }
                }
                break;

            case RIGHT:
                nX = x + 1;
                if ( testBounds(Direction.RIGHT) ) {
                    cell = map.getCell(nX, y);
                    if (cell.obj1 != Globals.ID_WALL) {
                        if (cell.obj2 == Globals.ID_BOX) {
                            if (checkBounds(nX + 1, y)) {
                                MapCell cell2 = map.getCell(nX + 1, y);
                                if (map.moveBox(cell, cell2))
                                    this.x = nX;
                            }
                        } else
                            this.x = nX;
                    }
                }
                break;

            case UP:
                nY = y - 1;
                if ( testBounds(Direction.UP) ) {
                    cell = map.getCell(x, nY);
                    if (cell.obj1 != Globals.ID_WALL) {
                        if (cell.obj2 == Globals.ID_BOX) {
                            if (checkBounds(x, nY - 1)) {
                                MapCell cell2 = map.getCell(x, nY - 1);
                                if ( map.moveBox(cell, cell2) )
                                    this.y = nY;
                            }
                        } else
                            this.y = nY;
                    }
                }
                break;

            case DOWN:
                nY = y + 1;
                if ( testBounds(Direction.DOWN) ) {
                    cell = map.getCell(x, nY);
                    if (cell.obj1 != Globals.ID_WALL) {
                        if (cell.obj2 == Globals.ID_BOX) {
                            if (checkBounds(x, nY + 1)) {
                                MapCell cell2 = map.getCell(x, nY + 1);
                                if ( map.moveBox(cell, cell2) )
                                    this.y = nY;
                            }
                        } else {
                            this.y = nY;
                        }
                    }

                }
                break;

        }
        if ( map.checkAllBoxesOnGoal() )
            Gdx.app.log("SOKO", "You Win!");

    }

    public void render(SpriteBatch batch) {
        batch.draw(sprite,map.getScreenX() + x*64 + sprite.getWidth()/2, map.getScreenY() + y*64);
    }

    public void debugPrint() {
        Gdx.app.log("SOKO", "Player position: " + this.x + "," + this.y);
    }

    public void setPosition(int px, int py) {
        x = px;
        y = py;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
