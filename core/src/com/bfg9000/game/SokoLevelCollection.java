package com.bfg9000.game;

import java.util.ArrayList;
import java.util.List;

public class SokoLevelCollection {
    private List<SokoLevel> levels;
    private String collectionName;

    public SokoLevelCollection() {
        levels = new ArrayList<SokoLevel>();
    }

    public void Parse(String lines) {
        String[] linesArray = lines.split("\\r?\\n");
        List<String> buf = new ArrayList<String>();
        SokoLevel oneLevel = new SokoLevel();

        for (int i=0; i < linesArray.length; i++ ) {
            String s = linesArray[i];
            if (!s.isEmpty() && Globals.isLevelChar(s.charAt(0))) {
                buf.add(s);
            } else {
                if (s.toLowerCase().contains("author:"))
                    oneLevel.setLevelAuthor(s.substring("author:".length()));
                if (s.toLowerCase().contains("title:"))
                    oneLevel.setLevelTitle(s.substring("title:".length()));
                if (s.toLowerCase().contains("limit:"))
                    oneLevel.setLevelLimit(s.substring("limit:".length()));
            }

            if (i == (linesArray.length - 1) ) {
                if (buf.size() != 0) {
                    oneLevel.setLevelData(buf);
                    levels.add(oneLevel);
                    buf = null;
                }
            } else if (s.isEmpty()) {
                if (buf.size() != 0) {
                    oneLevel.setLevelData(buf);
                    levels.add(oneLevel);
                    oneLevel = new SokoLevel();
                    buf.clear();
                }

            }
        }
    }

    public void printLevel(int i) {
        levels.get(i).debugPrint();
        //levels.get(i).debugPrint2();
    }

    public SokoLevel getLevel(int i) {
        return levels.get(i);
    }
}
