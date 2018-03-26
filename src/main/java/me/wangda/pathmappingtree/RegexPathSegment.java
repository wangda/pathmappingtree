/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */
package me.wangda.pathmappingtree;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式Path片段
 * @author wangda
 */
public class RegexPathSegment extends PathSegment {
    
    String regex = "";
    Pattern pattern = null;
    
    public RegexPathSegment(String regex) {
        this.regex = regex == null? "": regex.trim();
        if (this.regex != null && this.regex.length() > 0) {
            pattern = Pattern.compile(this.regex);
        }
    }
    
    @Override
    public PathSegmentType getType() {
        return PathSegmentType.REGEX;
    }

    @Override
    public boolean isMatched(String matchStr) {
        if (pattern == null) {
            return false;
        }
        Matcher m = pattern.matcher(matchStr);
        return m.matches();
    }

    @Override
    public boolean isSameSegment(String segStr) {
        segStr = segStr == null? "": segStr.trim();
        return regex.equals(segStr);
    }

    @Override
    public String getValue() {
        return this.regex;
    }

}
