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

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import me.wangda.pathmappingtree.HTTPMethod;
import me.wangda.pathmappingtree.PathElement;
import me.wangda.pathmappingtree.PathSegment;
import me.wangda.pathmappingtree.PathSegmentType;

/**
 * PathElement 元素
 * 
 * @author wangda
 */
public class PathElementTest {

    @Test
    public void testFromUrl() {
        PathElement element = PathElement.fromUrl("GET|POST /users/all");
        assertNotNull("PathElement不能为空", element);
        
        // HTTP请求方法
        List<HTTPMethod> methodList = element.method();
        assertEquals(2, methodList.size());
        assertEquals(HTTPMethod.GET.name(), methodList.get(0).name());
        assertEquals(HTTPMethod.POST, methodList.get(1));
        
        // Path
        assertEquals("/users/all", element.path());
        
        // Path解析结果
        List<PathSegment> segments = element.segments();
        assertEquals(2, segments.size());
        
        
        assertEquals("users", segments.get(0).getValue());
        assertEquals("all", segments.get(1).getValue());
    }
    
    @Test
    public void testFromUrl_multiSplit() {
        PathElement element = PathElement.fromUrl("GET /users/id-*//{[a-z]+}");
        assertNotNull("PathElement不能为空", element);
        
        // HTTP请求方法
        List<HTTPMethod> methodList = element.method();
        assertEquals(1, methodList.size());
        assertEquals(HTTPMethod.GET.name(), methodList.get(0).name());
        
        // Path
        assertEquals("/users/id-*//{[a-z]+}", element.path());
        
        // Path解析结果
        List<PathSegment> segments = element.segments();
        assertEquals(3, segments.size());
        
        
        assertEquals(PathSegmentType.NORMAL, segments.get(0).getType());
        assertEquals(PathSegmentType.WILDCARD, segments.get(1).getType());
        assertEquals(PathSegmentType.REGEX, segments.get(2).getType());
    }

    @Test
    public void testMethodString() {
        PathElement element = PathElement.fromUrl("GET|POST /users/all");
        assertEquals("GET|POST", element.methodStr);
    }

    @Test
    public void testMethod() {
        PathElement element = PathElement.fromUrl("GET|POST /users/all");
    }
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

}
