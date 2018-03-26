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
 * Path片段类型，共三种：常规、正则表达式、通配符
 * @author wangda
 */
public enum PathSegmentType {
    /** 常规URL片段 */
    NORMAL, 
    /** 正规表达式URL片段，使用{}来标识，如：{[1-9]\d{5}(?!\d)}表示邮政编码 */
    REGEX, 
    /** 使用*号通配符的URL片段，如：/user-* 表示以user-开头的字符串 */
    WILDCARD;
}
