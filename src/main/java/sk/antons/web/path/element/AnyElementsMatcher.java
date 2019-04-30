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
