package me.wangda.pathmappingtree;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ PathElementTest.class, PathMappingNodeTest.class, PathMappingTreeTest.class,
        PathSegmentFactoryTest.class, RegexPathSegmentTest.class, WildcardPathSegmentTest.class })
public class AllTests {

}
