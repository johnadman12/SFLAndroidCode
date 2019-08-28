package stock.com.socialview.src.com.hendraanggrian.appcompat.socialview;

import androidx.annotation.NonNull;

/**
 * Abstract hashtag to be used with {@link
 */
public interface Hashtagable {

    /**
     * Unique id of this hashtag.
     */
    @NonNull
    CharSequence getId();

    /**
     * Optional count, located right to hashtag name.
     */
    int getCount();
}