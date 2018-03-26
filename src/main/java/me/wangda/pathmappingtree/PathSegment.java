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
 * Path片段，也就是一个Path用/分隔后的一段内容
 * @author wangda
 */
public abstract class PathSegment {
    
    protected PathElement parent;
    
    protected boolean isEndSegment = false;
    
    public abstract String getValue();
    
    /**
     * Path片段的类型，区分是哪一种片段
     * 
     * @return
     */
    public abstract PathSegmentType getType();
    
    /**
     * 检查一个字符串是否能匹配上此Path片段
     * 
     * @param matchStr 要匹配的字符串
     * @return 匹配结果，true：匹配成功； false：匹配失败
     */
    public abstract boolean isMatched(String matchStr);
    
    /**
     * 当前片段是不是URL中的最后一个片段
     * @return true:是最后一个； false:不是最后一个
     */
    public boolean isEndSegment() {
        return isEndSegment;
    }
    
    /**
     * 检查字符串是否是同一个PathSegment
     * @param segStr Path片段
     * @return 
     */
    public abstract boolean isSameSegment(String segStr);
    
    public void setParent(PathElement parent) {
        this.parent = parent;
    }
    
    public PathElement getParent() {
        return this.parent;
    }
    
}
