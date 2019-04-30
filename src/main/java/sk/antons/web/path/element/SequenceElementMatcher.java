/*
 * 
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
