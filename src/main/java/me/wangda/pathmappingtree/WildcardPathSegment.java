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

/**
 * *号通配符Path片段
 * @author wangda
 */
public class WildcardPathSegment extends PathSegment {
    String wildcardStr = "";
    WildcardPosition wildcardPos = null;
    
    String left = "";
    String right = "";
    
    
    public WildcardPathSegment(String s) {
        s = s==null? "": s.trim();
        wildcardStr = s;
        
        // 拆分
        splitByWildcard();
    }
    
    void splitByWildcard() {
        int index = wildcardStr.indexOf("*");
        if (index == -1) {
            wildcardPos = WildcardPosition.UNKNOWN;
        } else if (index == 0) {
            wildcardPos = WildcardPosition.LEFT;
        } else if (index == wildcardStr.length()-1) {
            wildcardPos = WildcardPosition.RIGHT;
        } else if (index > 0) {
            wildcardPos = WildcardPosition.MIDDLE;
        }
        
        if (index > -1) {
            left = wildcardStr.substring(0, index);
            right = wildcardStr.substring(index + 1, wildcardStr.length());
        }
    }
    
    @Override
    public PathSegmentType getType() {
        return PathSegmentType.WILDCARD;
    }

    @Override
    public boolean isMatched(String matchStr) {
        matchStr = matchStr == null? "": matchStr.trim();
        if (wildcardPos == WildcardPosition.LEFT) {
            return matchStr.endsWith(right);
        } else if (wildcardPos == WildcardPosition.RIGHT) {
            return matchStr.startsWith(left);
        } else if (wildcardPos == WildcardPosition.MIDDLE) {
            return matchStr.startsWith(left) && matchStr.endsWith(right);
        } else {
            return false;
        }
    }
    
    @Override
    public boolean isSameSegment(String segStr) {
        segStr = segStr == null? "": segStr.trim();
        return wildcardStr.equals(segStr);
    }
    
    /** 通配符位置 */
    enum WildcardPosition {
        UNKNOWN, LEFT, MIDDLE, RIGHT;
    }

    @Override
    public String getValue() {
        return this.wildcardStr;
    }
}
