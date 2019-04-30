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
package sk.antons.web.path.element;


import java.util.List;
import sk.antons.web.path.*;
import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author antons
 */
public class SequenceTest {
	private static Logger log = Logger.getLogger(SequenceTest.class.getName());

    
    @Test
	public void split() throws Exception {
        String pattern = "";
        List<Sequence> list = Sequence.split(pattern.toCharArray(), 0, pattern.length());
        Assert.assertTrue("1.1", list.isEmpty());
        
        pattern = "jablko";
        list = Sequence.split(pattern.toCharArray(), 0, pattern.length());
        Assert.assertTrue("2.1", list.size() == 1);
        Assert.assertFalse("2.2", list.get(0).isWild());
        
        pattern = "*";
        list = Sequence.split(pattern.toCharArray(), 0, pattern.length());
        Assert.assertTrue("3.1", list.size() == 1);
        Assert.assertTrue("3.2", list.get(0).isWild());
        
        pattern = "***";
        list = Sequence.split(pattern.toCharArray(), 0, pattern.length());
        Assert.assertTrue("4.1", list.size() == 1);
        Assert.assertTrue("4.2", list.get(0).isWild());
        
        pattern = "*a**b*";
        list = Sequence.split(pattern.toCharArray(), 0, pattern.length());
        Assert.assertTrue("4.1", list.size() == 5);
        Assert.assertTrue("4.2", list.get(0).isWild());
        Assert.assertFalse("4.3", list.get(1).isWild());
        Assert.assertTrue("4.4", list.get(2).isWild());
        Assert.assertFalse("4.5", list.get(3).isWild());
        Assert.assertTrue("4.5", list.get(4).isWild());
        
        pattern = "a*b**c";
        list = Sequence.split(pattern.toCharArray(), 0, pattern.length());
        Assert.assertTrue("5.1", list.size() == 5);
        Assert.assertFalse("5.2", list.get(0).isWild());
        Assert.assertTrue("5.3", list.get(1).isWild());
        Assert.assertFalse("5.4", list.get(2).isWild());
        Assert.assertTrue("5.5", list.get(3).isWild());
        Assert.assertFalse("5.5", list.get(4).isWild());
    }
    
    @Test
	public void index() throws Exception {
        String pattern = "abc";
        List<Sequence> list = Sequence.split(pattern.toCharArray(), 0, pattern.length());
        Sequence s = list.get(0);
        
        String text = "0123456789";
        Assert.assertEquals("1.1", -1, s.index(text.toCharArray(), 0, text.length()));
        
        text = "abc0123456789";
        Assert.assertEquals("2.1", 0, s.index(text.toCharArray(), 0, text.length()));
        Assert.assertEquals("2.2", -1, s.index(text.toCharArray(), 1, text.length()));
        
        text = "0123456789abc";
        Assert.assertEquals("3.1", 10, s.index(text.toCharArray(), 0, text.length()));
        Assert.assertEquals("3.2", -1, s.index(text.toCharArray(), 12, text.length()));
        
        text = "01234abc56789abc";
        Assert.assertEquals("4.1", 5, s.index(text.toCharArray(), 0, text.length()));
        Assert.assertEquals("4.2", 13, s.index(text.toCharArray(), 6, text.length()));
        Assert.assertEquals("4.3", -1, s.index(text.toCharArray(), 14, text.length()));
    }
    
}
