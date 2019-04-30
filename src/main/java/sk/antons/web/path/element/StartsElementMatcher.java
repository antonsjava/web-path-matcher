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

/**
 * Not public class. Just internal implementation.
 * @author antons
 */
public class StartsElementMatcher extends SingleElementMatcher {
    
    private char[] chars;
    private int startpos;
    private int endpos;
    private int size;

    public StartsElementMatcher(char[] chars, int startpos, int endpos) {
        this.chars = chars;
        this.startpos = startpos;
        this.endpos = endpos;
        this.size = endpos - startpos;
    }
    
    @Override
    public boolean match(char[] chars, int startpos, int endpos) {
        if((endpos - startpos) < size) return false;
        for(int i = 0; i < size; i++) {
            char c = this.chars[this.startpos + i];
            if((c == '?') || (c == chars[startpos + i])) continue;
            return false;
        }
        return true;
    }
    
}
