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
package sk.antons.web.path;

import sk.antons.web.path.element.ElementMatcher;
import sk.antons.web.path.element.ElementMatcherFactory;

/**
 * Web path matching utility. Pattern to be matched can use 
 * <li>individual '?' character to represent any character
 * <li>individual '*' character to represent any sequence of characters except '/'
 * <li>path segment with only '*' character (more than one) to represent any sequence of path elements
 * 
 * <li>PathMatcher.instance("/foo/**") represents paths like /foo or /foo/bar/one
 * <li>PathMatcher.instance("/foo/*.xml") represents paths like /foo/a.xml or /foo/bar.xml
 * <li>PathMatcher.instance("/foo/?.xml") represents paths like /foo/a.xml or /foo/b.xml
 * 
 * @author antons
 */
public class PathMatcher {

    private String pattern;
    private int maxSize = 64;
    private char[] patternChars = null;
    private int[] elements = null;
    private int elementSize = 0;
    private int matcherSize = 0;
    private ElementMatcher[] matchers = null;

    /**
     * Creates PathMatcher with given pattern and defined max of path segments.
     * @param pattern pattern for matching paths
     * @param maxSize maximal number path segments 
     */
    public PathMatcher(String pattern, int maxSize) { 
        this.pattern = pattern; 
        this.maxSize = maxSize; 
        init();
    }
    
    /**
     * Creates PathMatcher with given pattern and defined max of path segments.
     * It limits path to 64 segments.
     * @param pattern pattern for matching paths
     * @return  new PathMatcher instance 
     */
    public static PathMatcher instance(String pattern) { return new PathMatcher(pattern, 64); }
    
    /**
     * 
     * Creates PathMatcher with given pattern and defined max of path segments.
     * @param pattern pattern for matching paths
     * @param maxSize maximal size of path segments.
     * @return  new PathMatcher instance 
     */
    public static PathMatcher instance(String pattern, int maxSize) { return new PathMatcher(pattern, maxSize); }
    
    private void init() {
        if(pattern == null) throw new IllegalArgumentException("Null path pattern is not allowed");
        if(pattern.length() == 0) throw new IllegalArgumentException("Empty path pattern is not allowed");
        patternChars = pattern.toCharArray();
        elements = new int[maxSize+1];
        matchers = new ElementMatcher[maxSize];
        elementSize = elementize(patternChars, elements);
        for(int i = 0; i < elementSize; i++) {
            ElementMatcher m = ElementMatcherFactory.matcher(patternChars, elements[i]+1, elements[i+1]);
            if(m != null) matchers[matcherSize++] = m;
        }
    }

    private int elementize(char[] chars, int[] elements) {
        int pos = 0;
        int startpos = 0;
        if(chars.length == 0) return pos;
        if(chars[0] == '/') {
            elements[pos++] = 0;
            startpos = 1;
        } else {
            elements[pos++] = -1;
        }
        int len = chars.length;
        for(int i = startpos; i < len; i++) {
            if(chars[i] == '/') elements[pos++] = i;
        }
        if(chars[len-1] != '/') {
            elements[pos++] = len;
        }
        return pos-1;
    }
    
    /**
     * Returns true if given path matches pattern.
     * @param path path to be matched
     * @return true if path matches
     */
    public boolean match(String path) {
        if(path == null) path = "/";
        char[] partChars = path.toCharArray();
        int[] parts = new int[maxSize+1];
        int partSize = elementize(partChars, parts);
        return matchImpl(partChars, parts, partSize, 0, 0); 
    }

    private boolean matchImpl(char[] chars, int[] parts, int partSize, int partIndex, int matcherIndex) {
        if((partIndex >= partSize) && (matcherIndex >= matcherSize)) return true;
        //if(partIndex >= partSize) return false;
        if(matcherIndex >= matcherSize) return false;
        ElementMatcher matcher = matchers[matcherIndex];
        if(matcher.isWild()) {
            for(int i = partSize-1; i >= 0; i--) {
                boolean result = matchImpl(chars, parts, partSize, partIndex+i, matcherIndex+1);
                if(result) return true;
            }
            return false;
        } else {
            if(matcher.match(chars, parts[partIndex]+1,parts[partIndex+1])) {
                return matchImpl(chars, parts, partSize, partIndex+1, matcherIndex+1);
            } else {
                return false;
            }
        }
    }
        
}
