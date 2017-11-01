package com.bfg9000.game;

import com.badlogic.gdx.graphics.Texture;

public class Globals {
    public static int gameScreenWidth = 700;
    public static int gameScreenHeight = 572;
    public static Texture texPlayer, texWall, texGround, texBox, texDark_box, texEndpoint;
    public static char[] SOKO_CHARS = {' ','.','#','@','*','+','$'};

    public static final char CHAR_FLOOR = ' ';
    public static final char CHAR_GOAL = '.';
    public static final char CHAR_WALL = '#';
    public static final char CHAR_BOX = '$';
    public static final char CHAR_PLAYER = '@';
    public static final char CHAR_BOX_ON_GOAL = '*';
    public static final char CHAR_PLAYER_ON_GOAL = '+';

    public static final int ID_FLOOR = 1;
    public static final int ID_GOAL = 2;
    public static final int ID_WALL = 3;
    public static final int ID_BOX = 4;
    public static final int ID_PLAYER = 5;
    public static final int ID_SPACE = 8;

    public static boolean isLevelChar(char c) {
        boolean found = false;
        for (char cc: SOKO_CHARS)
            if (c == cc) { found = true; break; }
        return found;
    }

}
