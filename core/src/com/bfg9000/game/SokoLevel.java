package com.bfg9000.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public class SokoLevel {
    private float screenX, screenY;
    private String levelTitle;
    private String levelAuthor;
    private String levelLimit;
    private int width;
    private int height;
    private List<String> levelData;
    private int totalBoxes = 0;
    private int playerPosX;
    private int playerPosY;
    private List<MapCell> levelMap = new ArrayList<MapCell>();
    private int boxesOnGoal = 0;

    public int getHeight() {
        return height;
    }

    public int getPlayerPosX() {
        return playerPosX;
    }

    public int getPlayerPosY() {
        return playerPosY;
    }

    public int getWidth() {

        return width;
    }

    public void restart() {
        this.setLevelData(levelData);
    }

    public String getLevelTitle() {
        return levelTitle;
    }

    public void setLevelTitle(String levelTitle) {
        this.levelTitle = levelTitle;
    }

    public String getLevelAuthor() {
        return levelAuthor;
    }

    public void setLevelAuthor(String levelAuthor) {
        this.levelAuthor = levelAuthor;
    }

    public String getLevelLimit() {
        return levelLimit;
    }

    public void setLevelLimit(String levelLimit) {
        this.levelLimit = levelLimit;
    }

    public void setLevelData(List<String> levelData) {
        int x=0;
        int y;
        this.levelData = levelData;
        this.height = levelData.size();
        y = 0;
        for (String s: levelData) {
            boolean notWall = true;
            if (s.length() > width) width = s.length();
            for (int i = 0; i<s.length(); i++) {
                char c = s.charAt(i);
                MapCell cell = new MapCell();
                cell.x = i; cell.y = y;

                switch (c) {
                    case Globals.CHAR_FLOOR:
                        if (notWall)
                            cell.obj1 = Globals.ID_SPACE;
                        else {
                            cell.obj1 = Globals.ID_FLOOR;
                        }
                        break;
                    case Globals.CHAR_WALL:
                        cell.obj1 = Globals.ID_WALL;
                        notWall = false;
                        break;
                    case Globals.CHAR_BOX:
                        cell.obj1 = Globals.ID_FLOOR;
                        cell.obj2 = Globals.ID_BOX;
                        totalBoxes++;
                        break;
                    case Globals.CHAR_BOX_ON_GOAL:
                        cell.obj1 = Globals.ID_GOAL;
                        cell.obj2 = Globals.ID_BOX;
                        boxesOnGoal++;
                        totalBoxes++;
                        break;
                    case Globals.CHAR_PLAYER:
                        cell.obj1 = Globals.ID_FLOOR;
                        playerPosX = i;
                        playerPosY = y;
                        break;
                    case Globals.CHAR_PLAYER_ON_GOAL:
                        cell.obj1 = Globals.ID_GOAL;
                        playerPosX = i;
                        playerPosY = y;
                        break;
                    case Globals.CHAR_GOAL:
                        cell.obj1 = Globals.ID_GOAL;
                        break;
                }
                levelMap.add(cell);
            }
            y++;
        }
    }

    public boolean checkAllBoxesOnGoal() {
        if (boxesOnGoal == totalBoxes)
            return true;
        else
            return false;
    }

    public boolean moveBox(MapCell srcCell, MapCell dstCell) {
        if (dstCell.obj1 != Globals.ID_WALL) {
            if (dstCell.obj2 == Globals.ID_BOX)
                return false;
            else {
                dstCell.obj2 = Globals.ID_BOX;
                srcCell.obj2 = Globals.ID_SPACE;
                if (dstCell.obj1 == Globals.ID_GOAL)
                    boxesOnGoal++;
                if (srcCell.obj1 == Globals.ID_GOAL && dstCell.obj1 != Globals.ID_GOAL)
                    boxesOnGoal--;
                return true;
            }
        } else
            return false;
    }


    public void debugPrint() {
        for (String s: levelData) {
            Gdx.app.log("SOKO", s);
        }
        Gdx.app.log("SOKO", "Level width: " + this.width);
        Gdx.app.log("SOKO", "Level height: " + this.height);
        Gdx.app.log("SOKO", "Level author: " + this.levelAuthor);
        Gdx.app.log("SOKO", "Level title: " + this.levelTitle);
        Gdx.app.log("SOKO", "Level limit: " + this.levelLimit);

//        int i = 0;
//        for (MapCell cell: levelMap) {
//            i++;
//            Gdx.app.log("SOKO", "Cell " + i + ": " + "x= " + cell.x + " y= " + cell.y + " id1 = " + cell.obj1 + " id2= " + cell.obj2);
//        }
    }

    private Texture textureFromCell(MapCell c) {
        switch (c.obj1) {
            case Globals.ID_FLOOR:
                if (c.obj2 == Globals.ID_BOX)
                    return Globals.texBox;
                else
                    return Globals.texGround;
            case Globals.ID_GOAL:
                if (c.obj2 == Globals.ID_BOX)
                    return Globals.texDark_box;
                else
                    return Globals.texEndpoint;
            case Globals.ID_WALL:
                return Globals.texWall;
        }

        return null;
    }

    public void render(SpriteBatch batch) {

            int y = height;
            for (MapCell cell: levelMap) {
                if (cell.obj1 != Globals.ID_SPACE) {
                    batch.draw(Globals.texGround, screenX + cell.x*64, screenY + cell.y*64);

                    Texture tex = textureFromCell(cell);
                    if (tex == null) continue;

                    if (cell.obj1 == Globals.ID_GOAL && cell.obj2 != Globals.ID_BOX) {
                        batch.draw(tex, screenX + cell.x*64 + textureFromCell(cell).getWidth()/2, screenY + cell.y*64 + textureFromCell(cell).getHeight()/2);
                    } else
                        batch.draw(tex, screenX + cell.x*64, screenY + cell.y*64);
                }

            }

    }

    public void setScreenXY(float x, float y) {
        this.screenX = x;
        this.screenY = y;
    }

    public float getScreenX() {
        return screenX;
    }

    public float getScreenY() {
        return screenY;
    }

    public MapCell getCell(int cx, int cy) {
        for (MapCell cell: levelMap ) {
            if (cell.x == cx && cell.y == cy) return cell;
        }
        return null;
    }
}
