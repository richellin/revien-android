package richellin.revien.android;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

/**
 * Created by sangjun_lee on 2017/01/19.
 */

@RunWith(CustomRobolectricTestRunner.class)
@Config(application = RevienApplicationTest.class, constants = BuildConfig.class, sdk = 21)
@Ignore public class AbstractRobolectricTestCase {

  public Context context;
  public RevienApplication application;

  @Before public void setUp() {
    context = RuntimeEnvironment.application.getApplicationContext();
    application = (RevienApplication) RuntimeEnvironment.application;
  }

  @After public void tearDown() {
    context = null;
    application = null;
  }
}
