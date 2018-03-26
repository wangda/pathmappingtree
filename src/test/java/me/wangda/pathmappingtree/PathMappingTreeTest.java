package me.wangda.pathmappingtree;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import me.wangda.pathmappingtree.PathElement;
import me.wangda.pathmappingtree.PathMappingTree;

public class PathMappingTreeTest {
    
    @Test
    public void testAddPath() {
        PathMappingTree tree = new PathMappingTree();
        tree.addPath(PathConstants.path[0]);
        tree.addPath(PathConstants.path[1]);
        tree.addPath(PathConstants.path[2]);
        tree.addPath(PathConstants.path[3]);
        tree.addPath(PathConstants.path[4]);
        System.out.println(tree.toString());
    }
    
    @Test
    public void testMatchPath() {
        PathMappingTree tree = new PathMappingTree();
        tree.addPath(PathConstants.path[0]);
        tree.addPath(PathConstants.path[1]);
        tree.addPath(PathConstants.path[2]);
        tree.addPath(PathConstants.path[3]);
        tree.addPath(PathConstants.path[4]);
        tree.addPath(PathConstants.path[5]);
        tree.addPath(PathConstants.path[6]);
        tree.addPath(PathConstants.path[7]);
        System.out.println(tree.toString());
        
        PathElement element = tree.matchPath("/users/sss", "get");
        assertNull(element);
        
        element = tree.matchPath("/users/sss", "delete");
        System.out.println(element == null? "null": element.toString());
        assertNotNull(element);
        
        element = tree.matchPath("/users/123456");
        System.out.println(element == null? "null": element.toString());
        assertNotNull(element);
        
        System.out.println(element == null? "null": element.toString());
        assertNotNull(element);
        
    }
    
    @Test
    public void testMatchPathPerformance() {
        PathMappingTree tree = new PathMappingTree();
        tree.addPath(PathConstants.path[0]);
        tree.addPath(PathConstants.path[1]);
        tree.addPath(PathConstants.path[2]);
        tree.addPath(PathConstants.path[3]);
        tree.addPath(PathConstants.path[4]);
        tree.addPath(PathConstants.path[5]);
        tree.addPath(PathConstants.path[6]);
        tree.addPath(PathConstants.path[7]);
        tree.addPath(PathConstants.path[8]);
        System.out.println(tree.toString());
        
        long start = System.currentTimeMillis();
        for (int i=0; i<100000; i++) {
            PathElement element = tree.matchPath("/vehicle/12345/info/test");
        }
        long elapse = System.currentTimeMillis() - start;
        System.out.println("耗时：" + elapse + "ms");
        
        assertTrue(elapse < 1000); // 1秒内完成10万次匹配
    }

}
