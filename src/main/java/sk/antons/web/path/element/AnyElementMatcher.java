/*
 * 
 */
package sk.antons.web.path.element;

/**
 * Not public class. Just internal implementation.
 * @author antons
 */
public class AnyElementMatcher extends SingleElementMatcher {

    @Override
    public boolean match(char[] chars, int startpos, int endpos) {
        return true;
    }
    
}
