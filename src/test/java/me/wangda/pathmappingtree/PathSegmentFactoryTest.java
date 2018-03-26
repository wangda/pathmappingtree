package me.wangda.pathmappingtree;

import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import me.wangda.pathmappingtree.PathSegment;
import me.wangda.pathmappingtree.PathSegmentFactory;
import me.wangda.pathmappingtree.PathSegmentType;

public class PathSegmentFactoryTest {

    @Test
    public void PathSegment() {
        Optional<PathSegment> seg = PathSegmentFactory.newPathSegment("");
        assertFalse(seg.isPresent());
        
        seg = PathSegmentFactory.newPathSegment("123");
        assertTrue(seg.isPresent());
        assertEquals(PathSegmentType.NORMAL, seg.get().getType());
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
