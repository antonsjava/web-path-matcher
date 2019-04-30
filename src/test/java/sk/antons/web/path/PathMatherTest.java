/*
 * Copyright 2018 Anton Straka
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


import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author antons
 */
public class PathMatherTest {
	private static Logger log = Logger.getLogger(PathMatherTest.class.getName());

    
    @Test
	public void exactMatch() throws Exception {
        PathMatcher matcher = PathMatcher.instance("/");
        Assert.assertTrue("1", matcher.match("/"));
        Assert.assertFalse("2", matcher.match("/jablko"));
        
        matcher = PathMatcher.instance("/jablko");
        Assert.assertTrue("3", matcher.match("/jablko"));
        Assert.assertTrue("4", matcher.match("/jablko/"));
        Assert.assertFalse("5", matcher.match("/"));
        Assert.assertFalse("6", matcher.match("/jablko/hruska"));
        
        matcher = PathMatcher.instance("/?ab?ko");
        Assert.assertTrue("7", matcher.match("/jablko"));
        Assert.assertTrue("8", matcher.match("/habtko"));
    }
    
    @Test
	public void startsMatch() throws Exception {
        PathMatcher matcher = PathMatcher.instance("/jablko*");
        Assert.assertTrue("1.1", matcher.match("/jablko"));
        Assert.assertTrue("1.2", matcher.match("/jablkovod"));
        Assert.assertFalse("1.3", matcher.match("/jablkovod/delo"));
        Assert.assertFalse("1.4", matcher.match("/jablkvod"));
    }
    
    @Test
	public void endsMatch() throws Exception {
        PathMatcher matcher = PathMatcher.instance("/*.jablko");
        Assert.assertTrue("1.1", matcher.match("/moje.jablko"));
        Assert.assertTrue("1.2", matcher.match("/.jablko"));
        Assert.assertFalse("1.3", matcher.match("/jablko/delo"));
        Assert.assertFalse("1.4", matcher.match("/moje.jablkow"));
    }
    
    @Test
	public void sequenceMatch() throws Exception {
        PathMatcher matcher = PathMatcher.instance("/moje.*.jablko");
        Assert.assertTrue("1.1", matcher.match("/moje.dobre.jablko"));
        Assert.assertTrue("1.2", matcher.match("/moje..jablko"));
        Assert.assertFalse("1.3", matcher.match("/moje..jablko/delo"));
        Assert.assertFalse("1.4", matcher.match("/moje..jablka"));
    }
    
    @Test
	public void multiMatch1() throws Exception {
        PathMatcher matcher = PathMatcher.instance("/jablko/**");
        Assert.assertTrue("1.1", matcher.match("/jablko"));
        Assert.assertTrue("1.2", matcher.match("/jablko/"));
        Assert.assertTrue("1.3", matcher.match("/jablko/anieco"));
        Assert.assertTrue("1.4", matcher.match("/jablko/anieco/aine"));
        Assert.assertFalse("1.5", matcher.match("/moje/delo"));
    }
    
    @Test
	public void multiMatch2() throws Exception {
        PathMatcher matcher = PathMatcher.instance("/jablko/**/*.xml");
        Assert.assertTrue("1.1", matcher.match("/jablko/a.xml"));
        Assert.assertTrue("1.2", matcher.match("/jablko/toto/a.xml"));
        Assert.assertTrue("1.3", matcher.match("/jablko/toto/tamto/a.xml"));
        Assert.assertFalse("1.4", matcher.match("/jablko/toto/tamto/a.json"));
        Assert.assertFalse("1.5", matcher.match("/moje/delo"));
    }
    
    @Test
	public void multiMatch3() throws Exception {
        PathMatcher matcher = PathMatcher.instance("**/*.xml");
        Assert.assertTrue("1.1", matcher.match("/a.xml"));
        Assert.assertTrue("1.2", matcher.match("/jablko/a.xml"));
        Assert.assertTrue("1.3", matcher.match("/jablko/toto/a.xml"));
        Assert.assertTrue("1.4", matcher.match("/jablko/toto/tamto/a.xml"));
        Assert.assertFalse("1.5", matcher.match("/jablko/toto/tamto/a.json"));
        Assert.assertFalse("1.6", matcher.match("/moje/delo"));
    }
}
