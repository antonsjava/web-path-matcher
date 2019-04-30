/*
 * 
 */
package sk.antons.web.path.element;

/**
 * Not public class. Just internal implementation.
 * @author antons
 */
public class EndsElementMatcher extends SingleElementMatcher {
    
    private char[] chars;
    private int startpos;
    private int endpos;
    private int size;

    public EndsElementMatcher(char[] chars, int startpos, int endpos) {
        this.chars = chars;
        this.startpos = startpos;
        this.endpos = endpos;
        this.size = endpos - startpos;
    }
    
    @Override
    public boolean match(char[] chars, int startpos, int endpos) {
        if((endpos - startpos) < size) return false;
        int diff = endpos - startpos - size;
        for(int i = 0; i < size; i++) {
            char c = this.chars[this.startpos + i];
            if((c == '?') || (c == chars[startpos + diff + i])) continue;
            return false;
        }
        return true;
    }
    
}
