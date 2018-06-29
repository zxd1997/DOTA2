package com.example.zxd1997.dota2.Fragments.Main;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zxd1997.dota2.Beans.WikiContent;
import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.OKhttp;
import com.google.gson.Gson;

public class HeroesFragment extends Fragment {
    private final int PARSE = 1;
    private final int IMAGE = 2;
    TextView text;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case PARSE: {
                    WikiContent wikiContent = new Gson().fromJson(msg.obj.toString(), WikiContent.class);
                    Spanned spanned;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        spanned = Html.fromHtml(wikiContent.getParse().getText(), Html.FROM_HTML_MODE_COMPACT);
                    } else
//                        spanned = Html.fromHtml(wikiContent.getParse().getText(), new NetworkImageGetter(), null);
                        spanned = Html.fromHtml(wikiContent.getParse().getText());
                    text.setText(spanned);
                    break;
                }
                case IMAGE: {
                    break;
                }
            }
            return true;
        }
    });

    public HeroesFragment() {
    }

    public static HeroesFragment newInstance() {
        return new HeroesFragment();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();
        System.runFinalization();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_heroes, container, false);
        text = view.findViewById(R.id.hero_text);
        OKhttp.getFromService("http://dota.huijiwiki.com/api.php?action=parse&format=json&formatversion=2&pageid=3728", handler, PARSE);
        return view;
    }

//    private final class NetworkImageGetter implements Html.ImageGetter {
//        @Override
//        public Drawable getDrawable(String source) { // TODO Auto-generated method stub
//            LevelListDrawable d = new LevelListDrawable();
//            new LoadImage().execute(source, d);
//            return d;
//        }
//    }
//
//    /**** 异步加载图片 **/
//    private final static class LoadImage extends AsyncTask<Object, Void, Bitmap> {
//        private LevelListDrawable mDrawable;
//
//        @Override
//        protected Bitmap doInBackground(Object... params) {
//            String source = (String) params[0];
//            mDrawable = (LevelListDrawable) params[1];
//            try {
//                InputStream is = new URL(source).openStream();
//                return BitmapFactory.decodeStream(is);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Bitmap bitmap) {
//            if (bitmap != null) {
//                BitmapDrawable d = new BitmapDrawable(bitmap);
//                mDrawable.addLevel(1, 1, d);
//                mDrawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
//                mDrawable.setLevel(1);
//                handl er
//            }
//        }
//    }


}
