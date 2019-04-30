/*
 * 
 */
package sk.antons.web.path.element;

/**
 * Not public class. Just internal implementation.
 * @author antons
 */
public abstract class SingleElementMatcher implements ElementMatcher {

    @Override
    public boolean isWild() {
        return false;
    }
    
}
