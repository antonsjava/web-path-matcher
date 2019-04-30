/*
 * 
 */
package sk.antons.web.path.element;

/**
 * Not public class. Just internal implementation.
 * @author antons
 */
public interface ElementMatcher {
    boolean isWild();
    boolean match(char[] chars, int startpos, int endpos);
}
