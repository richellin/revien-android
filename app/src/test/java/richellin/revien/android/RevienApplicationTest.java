package richellin.revien.android;

/**
 * Created by sangjun_lee on 2017/01/20.
 */

public class RevienApplicationTest extends RevienApplication {
  @Override public void initRealm() {
    // do nothing: Reaml.init() throws java.lang.UnsatisfiedLinkError for testing
  }


}
