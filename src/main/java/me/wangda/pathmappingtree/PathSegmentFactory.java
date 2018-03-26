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

import java.util.Optional;

/**
 * 根据真实的字符串，生成PathSegment的工厂类
 * @author wangda
 */
public class PathSegmentFactory {
    
    private static String START_CHAR = "{";
    
    private static String END_CHAR = "}";
    
    public static Optional<PathSegment> newPathSegment(String seg) {
        seg = seg == null? "": seg.trim();
        if (seg.startsWith(START_CHAR) && seg.endsWith(END_CHAR)) { // 正则表达式
            String regexStr = seg.substring(1, seg.length()-1);
            return Optional.of((PathSegment)new RegexPathSegment(regexStr));
        } else if (seg.indexOf("*") > -1) { // 通配符
            return Optional.of((PathSegment)new WildcardPathSegment(seg));
        } else if (seg.length() > 0) {
            return Optional.of((PathSegment)new NormalPathSegment(seg));
        } else {
            return Optional.ofNullable(null);
        }
    }
}
