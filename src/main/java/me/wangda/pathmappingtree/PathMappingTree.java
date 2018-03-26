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

import java.util.LinkedList;
import java.util.List;

import me.wangda.pathmappingtree.util.AssertUtils;

/**
 * Url路径映射树
 * 
 * @author wangda
 */
public class PathMappingTree extends PathMappingNode{
    
    /**
     * 向映射树中增加一个路径
     * @param path 增加的路径
     */
    public void addPath(String path) {
        AssertUtils.hasLength(path);
        
        PathElement ele = PathElement.fromUrl(path);
        PathSegment seg = ele.nextSegment();
        if (seg.getType() == PathSegmentType.NORMAL) {
            addPathElementToList(normalSegmentList, seg);
            
        } else if (seg.getType() == PathSegmentType.WILDCARD) {
            addPathElementToList(wildcardSegmentList, seg);
            
        } else if (seg.getType() == PathSegmentType.REGEX) {
            addPathElementToList(regexSegmentList, seg);
            
        }
    }
    
    /**
     * 匹配一个路径
     * @param path 要匹配的路径
     * @return 匹配正确的Path，如果没有匹配到，返回null
     */
    public PathElement matchPath(String path) {
        List<String> pathSegList = splitPath(path);
        
        PathElement pathElement = null;
        // 1. 常规
        for (PathMappingNode node: normalSegmentList) {
            List<String> tempList = clonePathList(pathSegList);
            pathElement = node.matchPath(tempList);
            if (pathElement != null) {
                return pathElement;
            }
        }

        // 2. 通配符
        for (PathMappingNode node: wildcardSegmentList) {
            List<String> tempList = clonePathList(pathSegList);
            pathElement = node.matchPath(tempList);
            if (pathElement != null) {
                return pathElement;
            }
        }
        
        // 3. 正则表达式
        for (PathMappingNode node: regexSegmentList) {
            List<String> tempList = clonePathList(pathSegList);
            pathElement = node.matchPath(tempList);
            if (pathElement != null) {
                return pathElement;
            }
        }
        
        return null;
    }
    
    /**
     * 匹配一个路径
     * @param path 路径
     * @param method HTTP方法
     * @return 匹配正确的Path，如果没有匹配到，返回null
     */
    public PathElement matchPath(String path, String method) {
        PathElement element = matchPath(path);
        if (element == null) {
            return null;
        }
        
        HTTPMethod compareMethod = HTTPMethod.parse(method);
        for (HTTPMethod m: element.method()) {
            if (m == HTTPMethod.ALL || m == compareMethod) {
                return element;
            }
        }
        
        return null;
    }
    
    private List<String> splitPath(String path) {
        List<String> result = new LinkedList<>();
        String regStr = "[/]+";
        String[] segArray = path.split(regStr);
        if (segArray.length == 0) {
            return result;
        }
        
        for (String s: segArray) {
            if (s != null && s.length() > 0) {
                result.add(s.trim());
            }
        }
        
        return result;
    }

    /**
     * 打印整个树
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (PathMappingNode node: normalSegmentList) {
            sb.append(node.toString(0));
        }
        
        for (PathMappingNode node: wildcardSegmentList) {
            sb.append(node.toString(0));
        }
        
        for (PathMappingNode node: regexSegmentList) {
            sb.append(node.toString(0));
        }
        
        return sb.toString();
    }

}
