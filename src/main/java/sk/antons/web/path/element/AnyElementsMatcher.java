/*
 * 
 */
package sk.antons.web.path.element;

/**
 * Not public class. Just internal implementation.
 * @author antons
 */
public class AnyElementsMatcher implements ElementMatcher {

    @Override
    public boolean isWild() {
        return true;
    }

    @Override
    public boolean match(char[] chars, int startpos, int endpos) {
        return true;
    }
    
}
