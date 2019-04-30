/*
 * Copyright 2019 Anton Straka
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package sk.antons.web.path.element;

import java.util.ArrayList;
import java.util.List;

/**
 * Not public class. Just internal implementation.
 * @author antons
 */
public class Sequence {

    private boolean wild = false;
    private char[] chars = null;
    private int startpos = -1;
    private int endpos = -1;
    private int size = -1;

    private Sequence(char[] chars, int startpos, int endpos, boolean wild) {
        this.chars = chars;
        this.startpos = startpos;
        this.endpos = endpos;
        this.wild = wild;
        this.size = endpos - startpos;
    }

    public char[] chars() {
        return chars;
    }

    public int startpos() {
        return startpos;
    }

    public int endpos() {
        return endpos;
    }

    public boolean isWild() {
        return wild;
    }

    public int size() {
        return size;
    }

    public static List<Sequence> split(char[] chars, int startpos, int endpos) {
        List<Sequence> rv = new ArrayList<Sequence>();
        int laststart = -1;
        boolean inwild = false;
        for(int i = startpos; i < endpos; i++) {
            char c = chars[i];
            if(inwild) {
                if(c != '*') {
                    if(laststart > -1) {
                        Sequence s = new Sequence(chars, laststart, i, inwild);
                        rv.add(s);
                    }
                    laststart = i;
                    inwild = false;
                }
            } else {
                if(c == '*') {
                    if(laststart > -1) {
                        Sequence s = new Sequence(chars, laststart, i, inwild);
                        rv.add(s);
                    }
                    laststart = i;
                    inwild = true;
                }
            }
            if(laststart < 0) laststart = i;
        }
        if(laststart > -1) {
            Sequence s = new Sequence(chars, laststart, endpos, inwild);
            rv.add(s);
        }
        return rv;
    }

    public int index(char[] chars, int startpos, int endpos) {
        if(wild) {
            return startpos;
        } else {
            if((endpos - startpos) < size) return -1;
            for(int i = startpos; i < endpos - size + 1; i++) {
                boolean match = true;
                for(int j = 0; j < size; j++) {
                    char c = this.chars[this.startpos + j];
                    if((c == '?') || (c == chars[i + j])) continue;
                    match = false;
                    break;
                }
                if(match) return i;
            }
            return -1;
        }
    }

    @Override
    public String toString() {
        return "Sequence{" + "wild=" + wild + ", chars=" + new String(chars, startpos, endpos - startpos) + '}';
    }

}
