package com.scurab.android.anuitor.sample;

import com.scurab.android.anuitor.C;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static junit.framework.TestCase.assertNotNull;

/**
 * Created by jbruchanov on 15.5.14.
 */
@Config(manifest = C.MANIFEST, sdk = 18)
@RunWith(RobolectricTestRunner.class)
public class SimpleTest {

    @Test
    public void testSimple() {
        assertNotNull(RuntimeEnvironment.application);

        Robolectric.getBackgroundThreadScheduler().unPause();
    }
}
