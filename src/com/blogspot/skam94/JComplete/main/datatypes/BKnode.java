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

import java.util.HashMap;
import java.util.Set;

public class BKnode {
    private String word;
    private HashMap<Integer, BKnode> children;

    public BKnode(String word) {
        this.word = word;
        children = new HashMap<>();
    }

    public String getWord() {
        return word;
    }

    public boolean containsKey(int key) {
        return children.containsKey(key);
    }

    public void addChild(int key, String word) {
        children.put(key, new BKnode(word));
    }

    public BKnode getChild(int key) {
        return children.get(key);
    }

    public Set<Integer> getKeys() {
        return children.keySet();
    }
}