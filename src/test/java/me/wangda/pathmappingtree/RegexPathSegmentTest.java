package me.wangda.pathmappingtree;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import me.wangda.pathmappingtree.RegexPathSegment;

public class RegexPathSegmentTest {
    
    @Test
    public void testIsMatched_null() {
        RegexPathSegment regex = new RegexPathSegment(null);
        assertEquals("", regex.regex);
        assertNull(regex.pattern);
    }
    
    @Test
    public void testIsMatched_date() {
        // yyyy-mm-dd 格式日期字符串
        String dateRegex =  "([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8])))";
        RegexPathSegment regex = new RegexPathSegment(dateRegex);
        assertTrue(regex.isMatched("2016-11-12"));
        assertFalse(regex.isMatched("test"));
    }

    @Test
    public void testIsSameSegment() {
        // email正则
        RegexPathSegment regex = new RegexPathSegment("[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?");
        assertTrue(regex.isSameSegment("[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?"));
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
