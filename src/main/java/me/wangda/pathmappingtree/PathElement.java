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
import java.util.List;
import java.util.Optional;

import me.wangda.pathmappingtree.util.AssertUtils;

/**
 * 一个URL Path元素
 * 
 * @author wangda
 */
public class PathElement {
    List<HTTPMethod> allowMethodList = new ArrayList<HTTPMethod>();
    
    List<PathSegment> segmentList = new ArrayList<PathSegment>();
    
    int segIndex = 0;
    
    Object data = null;
    
    String rawPath = "";
    
    String path = "";
    
    String methodStr = "";
    
    /**
     * 根据一个URL构造一个PathElement对象
     * @param url
     * @return
     */
    public static PathElement fromUrl(String url) {
        AssertUtils.hasLength(url);
        
        String[] s = url.split("[ ]+");
        if (s.length > 2) {
            throw new IllegalArgumentException("参数格式不正确：" + url);
        }
        
        PathElement element = new PathElement();
        element.rawPath = url;
        if (s.length == 1) {
            element.path = url.trim();
        } else {
            element.path = s[1];
            element.methodStr = s[0];
        }
        
        element.parse();
        return element;
    }
    
    /**
     * 解析URL为对象
     */
    private void parse() {
        parseMethod();
        
        parsePath();
    }
    
    /**
     * 解析HTTP方法 
     */
    private void parseMethod() {
        if (methodStr == null || methodStr.length() == 0) {
            this.appendMethod(HTTPMethod.ALL);
            return;
        }
        
        String[] methodArray = methodStr.split("\\|");
        for (String s: methodArray) {
            this.appendMethod(s);
        }
    }
    
    /**
     * 解析URL路径
     */
    private void parsePath() {
        String regStr = "[/]+";
        String[] segArray = path.split(regStr);
        if (segArray.length == 0) {
            return;
        }
        
        for (String s: segArray) {
            Optional<PathSegment> seg = PathSegmentFactory.newPathSegment(s);
            if (seg.isPresent()) {
                seg.get().setParent(this);
                this.segmentList.add(seg.get());
            }
        }
        
        this.segmentList.get(this.segmentList.size()-1).isEndSegment = true;
    }
    
    /**
     * 设置一个唯一的HTTP方法
     * @param methodName
     */
    public void method(String methodName) {
        HTTPMethod method = HTTPMethod.parse(methodName);
        allowMethodList.clear();
        allowMethodList.add(method);
    }
    
    /**
     * 获取全部的HTTP方法
     * @return
     */
    public List<HTTPMethod> method() {
        return this.allowMethodList;
    }
    
    /**
     * 获取以,为分隔符的全部HTTP方法
     * @return
     */
    public String methodString() {
        StringBuilder sb = new StringBuilder();
        for (int index = 0; index < this.allowMethodList.size(); index++) {
            if (index > 0) {
                sb.append("|");
            }
            sb.append(this.allowMethodList.get(index).name());
        }
        
        return sb.toString();
    }
    
    /**
     * 增加一个HTTP方法
     * @param methodName
     */
    public void appendMethod(String methodName) {
        HTTPMethod method = HTTPMethod.parse(methodName);
        appendMethod(method);
    }
    
    /**
     * 增加一个HTTP方法
     * @param method
     */
    public void appendMethod(HTTPMethod method) {
        for (HTTPMethod item: allowMethodList) {
            if (item.equals(method)) {
                return;
            }
        }
        
        allowMethodList.add(method);
    }
    
    /**
     * 批量增加HTTP方法
     * @param methodList
     */
    public void appendMethod(List<HTTPMethod> methodList) {
        for (HTTPMethod m: methodList) {
            appendMethod(m);
        }
    }
    
    /**
     * 
     * @return
     */
    public String path() {
        return this.path;
    }
    
    /**
     * 将Path解析为的片段列表
     * @return
     */
    public List<PathSegment> segments() {
        return this.segmentList;
    }
    
    /**
     * 设置数据对象
     * @param data
     */
    public void data(Object data) {
        this.data = data;
    }
    
    /**
     * 返回数据对象
     * @return
     */
    public Object data() {
        return this.data;
    }
    
    /**
     * 是否还有片段
     * @return
     */
    public boolean hasSegment() {
        return segIndex < segmentList.size();
    }
    
    /**
     * 下一个Path片段
     * @return
     */
    public PathSegment nextSegment() {
        if (segIndex >= segmentList.size()) {
            throw new IndexOutOfBoundsException("已经没有Path段");
        }
        return segmentList.get(segIndex++);
    }
    
    /**
     * 重置Path片段
     */
    public void reset() {
        segIndex = 0;
    }
    
    public String toString() {
        return methodString() + " " + path;
    }
}
