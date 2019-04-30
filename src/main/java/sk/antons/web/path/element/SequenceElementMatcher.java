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

import java.util.List;

/**
 * Not public class. Just internal implementation.
 * @author antons
 */
public class SequenceElementMatcher extends SingleElementMatcher {
    
    private List<Sequence> sequences;
    private int size;

    public SequenceElementMatcher(List<Sequence> sequences) {
        this.sequences = sequences;
        this.size = sequences.size();
    }
    
    @Override
    public boolean match(char[] chars, int startpos, int endpos) {
        return matchImpl(chars, startpos, endpos, 0);
    }
    
    private boolean matchImpl(char[] chars, int startpos, int endpos, int index) {
        if(index >= size) {
            if(startpos < endpos) return false;
            else return true;
        }
        Sequence s = sequences.get(index);
        if(s.isWild()) {
            for(int i = startpos; i < endpos; i++) {
                boolean result = matchImpl(chars, i, endpos, index+1);
                if(result) return true;
            }
            return false;
        } else {
            int pos = s.index(chars, startpos, endpos);
            while(pos > -1) {
                boolean result = matchImpl(chars, pos+s.size(), endpos, index+1);
                if(result) return true;
                pos = s.index(chars, pos+1, endpos);
            }
            return false;
        }
    }
    
}
