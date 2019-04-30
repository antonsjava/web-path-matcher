/*
 * 
 */
package sk.antons.web.path.element;

import java.util.List;

/**
 * Not public class. Just internal implementation.
 * @author antons
 */
public class ElementMatcherFactory {

    public static ElementMatcher matcher(char[] chars, int startindex, int endindex) {
        List<Sequence> sequences = Sequence.split(chars, startindex, endindex);
        if(sequences.isEmpty()) {
            return null;
        } else if(sequences.size() == 1) {
            Sequence s = sequences.get(0);
            if(s.isWild()) {
                if(s.size() == 1) {
                    return new AnyElementMatcher();
                } else {
                    return new AnyElementsMatcher();
                }
            } else {
                return new ExactElementMatcher(chars, startindex, endindex);
            }
        } else if(sequences.size() == 2) {
            Sequence s1 = sequences.get(0);
            Sequence s2 = sequences.get(1);
            if(s1.isWild()) {
                return new EndsElementMatcher(s2.chars(), s2.startpos(), s2.endpos());
            } else {
                return new StartsElementMatcher(s1.chars(), s1.startpos(), s1.endpos());
            }
        } else {
            return new SequenceElementMatcher(sequences);
        }
    }
    
}
