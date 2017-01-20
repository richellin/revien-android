package richellin.revien.android;

import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricTestRunner;

/**
 * Created by sangjun_lee on 2017/01/20.
 */

public class CustomRobolectricTestRunner extends RobolectricTestRunner {
  public CustomRobolectricTestRunner(Class<?> testClass) throws InitializationError {
    super(testClass);
    String buildVariant =
        (BuildConfig.FLAVOR.isEmpty() ? "" : BuildConfig.FLAVOR + "/") + BuildConfig.BUILD_TYPE;
    System.setProperty("android.package", BuildConfig.APPLICATION_ID);
    System.setProperty("android.manifest",
        "build/intermediates/manifests/full/" + buildVariant + "/AndroidManifest.xml");
    System.setProperty("android.resources", "build/intermediates/res/merged/" + buildVariant);
    System.setProperty("android.assets", "build/intermediates/assets/" + buildVariant);
  }
}
