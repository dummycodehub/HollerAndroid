package Utilities;

/**
 * Created by rakeshkoplod on 04/01/16.
 */
public class Utility {

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

}
