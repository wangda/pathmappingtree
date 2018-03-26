package me.wangda.pathmappingtree;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import me.wangda.pathmappingtree.WildcardPathSegment;
import me.wangda.pathmappingtree.WildcardPathSegment.WildcardPosition;

/**
 * 通配符片段测试
 * 
 * @author wangda
 */
public class WildcardPathSegmentTest {

    @Test
    public void testIsMatched_Left() {
        WildcardPathSegment segment = new WildcardPathSegment("user-*");
        assertEquals(WildcardPosition.RIGHT, segment.wildcardPos);
        assertTrue(segment.isMatched("user-1"));
    }
    
    @Test
    public void testIsMatched_Middle() {
        WildcardPathSegment segment = new WildcardPathSegment("user-*-info");
        assertTrue(segment.isMatched("user-1-info"));
    }
    
    @Test
    public void testIsMatched_Right() {
        WildcardPathSegment segment = new WildcardPathSegment("*user");
        assertTrue(segment.isMatched("all-user"));
    }
    
    @Test
    public void testIsMatched_null() {
        WildcardPathSegment segment = new WildcardPathSegment(null);
        assertEquals("", segment.wildcardStr);
        assertFalse(segment.isMatched("all-user"));
    }
    
    @Test
    public void testIsMatched() {
        WildcardPathSegment segment = new WildcardPathSegment("user-*");
        assertTrue(segment.isMatched("user-1"));
    }

    @Test
    public void testIsSameSegment() {
        WildcardPathSegment segment = new WildcardPathSegment("user-*");
        assertTrue(segment.isSameSegment("user-*"));
        assertFalse(segment.isSameSegment("user-"));
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
