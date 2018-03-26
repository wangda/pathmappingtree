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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 映射树的一个结点
 * 
 * @author wangda
 */
public class PathMappingNode {
    /** 常规片段 */
    List<PathMappingNode> normalSegmentList = new ArrayList<>(16);
    
    /** 通配符片段 */
    List<PathMappingNode> wildcardSegmentList = new ArrayList<>(16);
    
    /** 正则表达式片段 */
    List<PathMappingNode> regexSegmentList = new ArrayList<>(16);
    
    /** 当前结点中的路径片段对象 */
    PathSegment segment;
    
    private boolean isEndNode = false;
    
    /** 是否是Path的结点，如果返回true，表明有Path的终点在此结点上 */
    public boolean isEndNode() {
        return this.isEndNode;
    }

    /**
     * 向Node增加一个Path
     * @param segment Path片段
     */
    public void appendPathElement(PathSegment segment) {
        // 当前结点的Path片段为空：
        // 当前结点不是
        if (this.segment == null || !this.segment.isEndSegment()) {
            this.segment = segment;
        } 
        
        if (segment.isEndSegment()) {
            isEndNode = true;
            this.segment.getParent().appendMethod(segment.getParent().method());
            
        } else {
            PathSegment seg = segment.getParent().nextSegment();
            
            if (seg.getType() == PathSegmentType.NORMAL) {
                addPathElementToList(normalSegmentList, seg);
                
            } else if (seg.getType() == PathSegmentType.WILDCARD) {
                addPathElementToList(wildcardSegmentList, seg);
                
            } else {
                addPathElementToList(regexSegmentList, seg);
            }
        }
    }
    
    /**
     * 将一个Path片段加到一个结点列表中
     * @param nodeList 结点列表
     * @param seg Path片段
     */
    protected void addPathElementToList(List<PathMappingNode> nodeList, PathSegment seg) {
        PathMappingNode node = matchPathSegment(nodeList, seg);
        if (node == null) {
            PathMappingNode newNode = new PathMappingNode();
            newNode.segment = seg;
            newNode.appendPathElement(seg);
            nodeList.add(newNode);
        } else {
            
            node.appendPathElement(seg);
        }
    }
    
    /**
     * 从一个结点列表中匹配是否存在某个路径片段
     * @param segList
     * @param seg
     * @return
     */
    protected PathMappingNode matchPathSegment(List<PathMappingNode> segList, PathSegment seg) {
        for (PathMappingNode node: segList) {
            if (node.segment.isSameSegment(seg.getValue())) {
                return node;
            }
        }
        return null;
    }
    
    /**
     * 
     * @param matchedSegList
     * @return
     */
    public PathElement matchPath(List<String> matchedSegList) {
        // 取最前面的片段，跟当前结点比对如果不一致，直接返回null, 如果一致且不是最后的结点，则跟子结点比对。
        String pathSeg = matchedSegList.get(0);
        boolean isMatched = segment.isMatched(pathSeg);
        
        if (!isMatched) {
            return null;
        }
        
        if (matchedSegList.size() == 1 && isEndNode()) {
            return segment.getParent();
        }
        
        if (matchedSegList.size() > 1) {
            // 本段匹配成功，继续匹配
            matchedSegList.remove(0);
            PathElement ele = null;
            List<String> tempMatchedSegList = clonePathList(matchedSegList);
            // 1. 检查Normal
            ele = matchFromList(normalSegmentList, tempMatchedSegList);
            if (ele != null) {
                return ele;
            }
            // 2. 检查通配符
            tempMatchedSegList = clonePathList(matchedSegList);
            ele = matchFromList(wildcardSegmentList, tempMatchedSegList);
            if (ele != null) {
                return ele;
            }
            // 3. 检查正则表达式
            tempMatchedSegList = clonePathList(matchedSegList);
            ele = matchFromList(regexSegmentList, tempMatchedSegList);
            if (ele != null) {
                return ele;
            }
        }
        return null;
    }
    
    /**
     * 
     * @param nodeList
     * @param pathSegList
     * @return
     */
    private PathElement matchFromList(List<PathMappingNode> nodeList, List<String> pathSegList) {
        for (PathMappingNode node: nodeList) {
            PathElement ele = node.matchPath(pathSegList);
            if (ele != null) {
                return ele;
            }
        }
        return null;
    }
    
    /**
     * 
     * @param list
     * @return
     */
    protected List<String> clonePathList(List<String> list) {
        LinkedList<String> cloneList = new LinkedList<>();
        for (String s: list) {
            cloneList.add(s);
        }
        return cloneList;
    }
    
    /**
     * 树形结构的字符串
     * @param level
     * @return
     */
    public String toString(int level) {
        StringBuilder intent = new StringBuilder(32);
        for (int i=0; i<level; i++) {
            intent.append("    ");
        }
        
        StringBuilder nodeStr = new StringBuilder(128);
        
        nodeStr.append(intent.toString()).append("/").append(segment.getValue()); 
        if (isEndNode()) {
            String methods = segment.getParent().methodString();
            nodeStr.append(" ").append(methods);
        }
        nodeStr.append("\r\n");
        
        for (PathMappingNode node: normalSegmentList) {
            nodeStr.append(node.toString(level+1));
        }
        
        for (PathMappingNode node: wildcardSegmentList) {
            nodeStr.append(node.toString(level+1));
        }
        
        for (PathMappingNode node: regexSegmentList) {
            nodeStr.append(node.toString(level+1));
        }
        
        return nodeStr.toString();
    }
}
