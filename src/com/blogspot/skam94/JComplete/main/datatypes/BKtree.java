/*
 * This file is part of JComplete.
 *
 * JComplete is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JComplete is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JComplete.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright 2015 Stratos Kamadanis
 */
package com.blogspot.skam94.JComplete.main.datatypes;

import java.util.ArrayList;
import java.util.List;

public class BKtree {
    private BKnode root;

    public BKtree(String word) {
        root = new BKnode(word.toLowerCase());
    }

    public BKtree() {
        root = null;
    }

    public void add(String word) {
        word = word.toLowerCase();
        if (root == null) {
            root = new BKnode(word.toLowerCase());
            return;
        }
        BKnode currentNode = root;
        int distance = levenshtein(word, currentNode.getWord());
        while (distance != 0 && currentNode.containsKey(distance)) {
            currentNode = currentNode.getChild(distance);
            distance = levenshtein(word, currentNode.getWord());
        }
        if (distance == 0) {
            return;
        }
        currentNode.addChild(distance, word);
    }

    public List<String> search(String query, int distance) {
        String word = query.toLowerCase();
        List<String> list = new ArrayList<>();

        list.addAll(search(root, word, distance));

        return list;

    }

    public List<String> searchStartsWith(String query, int distance) {
        String word = query.toLowerCase();
        List<String> listTemp = new ArrayList<>();

        listTemp.addAll(search(root, word, distance));
        List<String> list = new ArrayList<>();
        listTemp.stream().filter(string -> string.startsWith(word)).forEach(string -> list.add(string));

        return list;
    }

    private List<String> search(BKnode beginning, String word, int distance) {
        List<String> list = new ArrayList<>();

        BKnode currentNode = beginning;
        int currentDistance = levenshtein(word, currentNode.getWord());

        int minDistance = currentDistance - distance;
        int maxDistance = currentDistance + distance;
        if (currentDistance <= distance) {
            list.add(currentNode.getWord());
        }
        currentNode.getKeys().stream().filter(key -> between(key, minDistance, maxDistance)).forEach(key -> {
            list.addAll(search(currentNode.getChild(key), word, distance));
        });

        return list;
    }

    private int levenshtein(String string1, String string2) {
        int maxi = string1.length() + 1;
        int maxj = string2.length() + 1;
        int[][] distance = new int[maxi][maxj];

        for (int i = 1; i < maxi; i++) {
            distance[i][0] = i;
        }

        for (int j = 1; j < maxj; j++) {
            distance[0][j] = j;
        }

        for (int j = 1; j < maxj; j++) {
            for (int i = 1; i < maxi; i++) {
                if (string1.charAt(i - 1) == string2.charAt(j - 1)) {
                    distance[i][j] = distance[i - 1][j - 1];
                } else {
                    distance[i][j] = min(distance[i - 1][j] + 1,
                            distance[i][j - 1] + 1,
                            distance[i - 1][j - 1] + 1
                    );
                }
            }
        }
        return distance[maxi - 1][maxj - 1];
    }

    private int min(int x, int y, int z) {
        if (x < y) {
            return Math.min(x, z);
        } else {
            return Math.min(y, z);
        }
    }

    private boolean between(int key, int min, int max) {
        return key <= max && key >= min;
    }
}
