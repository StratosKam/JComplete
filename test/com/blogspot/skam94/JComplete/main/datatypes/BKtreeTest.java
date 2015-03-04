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

public class BKtreeTest {

    public static void main(String[] args) throws Exception {
        BKtree tree=new BKtree();
        tree.add("kitten");
        tree.add("sitting");
        tree.add("sitten");
        tree.add("sittin");
        tree.search("sitt",2).stream().forEach(result-> System.out.println(result));
        System.out.println("-");
        tree.search("sitt",3).stream().forEach(result-> System.out.println(result));
        System.out.println("-");
        tree.search("kitt",2).stream().forEach(result-> System.out.println(result));
        System.out.println("-");
        tree.search("kitt",3).stream().forEach(result-> System.out.println(result));
    }
}