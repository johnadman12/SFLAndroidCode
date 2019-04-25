package stock.com.ui.dashboard.home.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import stock.com.R;

/**
 * Created by GIGAMOLE on 8/18/16.
 */
public class Utils {

    public static void setupItem(final View view, final LibraryObject libraryObject) {


        final ImageView img = (ImageView) view.findViewById(R.id.image);
        img.setImageResource(libraryObject.getRes());
    }

    public static class LibraryObject {

        private String mTitle;
        private int mRes;

        public LibraryObject(final int res, final String title) {
            mRes = res;
            mTitle = title;
        }

        public String getTitle() {
            return mTitle;
        }

        public void setTitle(final String title) {
            mTitle = title;
        }

        public int getRes() {
            return mRes;
        }

        public void setRes(final int res) {
            mRes = res;
        }
    }
}
