package com.scurab.android.uitor.tools;

import org.junit.Test;

import java.util.HashMap;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class HttpToolsTest {

    @Test
    public void testNullQs() {
        HashMap<String, String> map = HttpTools.parseQueryString(null);
        assertNotNull(map);
        assertEquals(0, map.size());
    }

    @Test
    public void testEmptyQs() {
        HashMap<String, String> map = HttpTools.parseQueryString("");
        assertNotNull(map);
        assertEquals(0, map.size());
    }

    @Test
    public void testValues() {
        HashMap<String, String> map = HttpTools.parseQueryString("a=1&b=2&c=3&d=&e=5");
        assertNotNull(map);
        assertEquals(5, map.size());
        assertEquals("1", map.get("a"));
        assertEquals("2", map.get("b"));
        assertEquals("3", map.get("c"));
        assertEquals("", map.get("d"));
        assertEquals("5", map.get("e"));
    }
}