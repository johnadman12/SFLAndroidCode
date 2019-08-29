package stock.com.ui.pojo;

import androidx.annotation.NonNull;

/**
 * Abstract hashtag to be used with {@link
 */
public interface Hashtagable {

    /**
     * Unique id of this hashtag.
     */
    @NonNull
    int getId();

    /**
     * Optional count, located right to hashtag name.
     */
    String getName();
    String getsymbol();
    int getmarketId();
}