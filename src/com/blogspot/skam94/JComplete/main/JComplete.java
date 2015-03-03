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
package com.blogspot.skam94.JComplete.main;


import com.blogspot.skam94.JComplete.main.datatypes.BKtree;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class JComplete {
    private BKtree dictionary;
    private int maxLenght;

    /**
     * @param source absolute path to a file containing a dictionary
     */
    public JComplete(String source) {
        dictionary = new BKtree();
        maxLenght = -1;
        try {
            Files.lines(Paths.get(source)).forEach(string -> {
                dictionary.add(string);
                if (string.length() > maxLenght) {
                    maxLenght = string.length();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getAnyMatch(String query) {
        List<String> list = new ArrayList<>();
        dictionary.search(query, maxLenght).stream().filter(string -> string.contains(query.toLowerCase())).forEach(string -> list.add(string));
        return list;
    }

    public List<String> getMatchStartsWith(String query) {
        List<String> list = new ArrayList<>();
        dictionary.search(query, maxLenght).stream().filter(string -> string.startsWith(query.toLowerCase())).forEach(string -> list.add(string));
        return list;
    }

    public List<String> getClosest(String query, int limit) {
        return dictionary.search(query, limit);
    }


}
