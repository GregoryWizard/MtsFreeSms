package com.gregorywizard.mtsfreesms;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import org.apache.http.util.ByteArrayBuffer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    Button btn_Send;
    ImageView ibtn_Captcha;
    Document doc = null;
    Elements imageSrc = null;
    ImageView iv;
    Bitmap bmp;
    Bitmap bitmap;
    ProgressDialog pDialog;

    static String webURL = "http://www.mts.ua/en/online-services/send-sms/";
    String number        = "+38050";
    String numbe2        = "9947599";
    String text          = "hello gregory";
    String captchaString = webURL+"/?r=site/captcha&amp;v=5643b46803141&amp;widgetId=messager&amp;width=115&amp;height=42&amp;backColor=0xffffff&amp;foreColor=0xff0000\"";


    URLConnection connect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        btn_Send = (Button) findViewById(R.id.btn_Send);
        ibtn_Captcha = (ImageView) findViewById(R.id.ibtn_Captcha);

        new LoadImage().execute(captchaString);

        ibtn_Captcha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoadImage().execute(captchaString);
            }
        });
       // String lll = webURL+"/?r=site/captcha&amp;v=5643b46803141&amp;widgetId=messager&amp;width=115&amp;height=42&amp;backColor=0xffffff&amp;foreColor=0xff0000\"";
        //Bitmap myBitmap = getBitmapFromUrl(lll);
        //ibtn_Captcha.setImageBitmap(myBitmap);
        /*
        try {
            connect = new URL(webURL).openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        //iv = (ImageView) findViewById(R.id.imageView);
        //bmp = getBitmapFromUrl("http://icons.iconarchive.com/icons/crountch/one-piece-jolly-roger/72/Luffys-flag-2-icon.png");
        //iv.setImageBitmap(bmp);

        //new LoadImagefromUrl( ).execute(ibtn_Captcha, "https://upload.wikimedia.org/wikipedia/commons/5/53/Opera_O.png");
        //new DownloadImage().execute();

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    public void sendClick(View view) throws IOException {
        String number = "+38050";
        String numbe2 = "9947599";
        String text   = "hello gregory";
        String lll = webURL+"/?r=site/captcha&amp;v=5643b46803141&amp;widgetId=messager&amp;width=115&amp;height=42&amp;backColor=0xffffff&amp;foreColor=0xff0000\"";

        //HttpClient httpClient = new DefaultHttpClient();
        //HttpPost httpPost = new HttpPost("http://www.mts.ua/en/online-services/send-sms/");

        // URL url = new URL(webURL);

        //Elements imageSrc;

        //Document doc = Jsoup.connect(webURL).get();
        //imageSrc = doc.select(".captchaImageCont");
        //btn_Send.setText(image.attr("src").toString());
        //ibtn_Captcha.setBackground();

    }

    public void captchaImageLoad(View view) {

    }

    /*
    private class Da"tag"rabber extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            // NO CHANGES TO UI TO BE DONE HERE
            try {
                doc = Jsoup.connect(webURL).get();
                imageSrc = doc.select(".captchaImageCont");

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //This is where we update the UI with the acquired data
            if(imageSrc != null) {
                //btn_Send.setText(imageSrc.attr("src").toString());
                //String img = webURL + imageSrc.attr("src").toString();
                String lll = "pp.vk.me/c622619/v622619571/49f76/p25_MThWjdk.jpg";
                //ibtn_Captcha.setImageURI(Uri.parse(lll));

                Bitmap myBitmap = getBitmapFromUrl(lll);
                ibtn_Captcha.setImageBitmap(myBitmap);
            }
        }
    }*/


    public class DownloadImage extends AsyncTask<Void, Void, Boolean> {
        Bitmap myBitmap;

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                InputStream in = new java.net.URL("https://upload.wikimedia.org/wikipedia/commons/5/53/Opera_O.png").openStream();
                myBitmap = BitmapFactory.decodeStream(in);

            } catch(Exception e) {
                e.printStackTrace();
                Log.d("ERROR_DOWNLOAD_IMAGE",captchaString);
                return false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean)
                ibtn_Captcha.setImageBitmap(myBitmap);
        }

    }

    private class LoadImagefromUrl extends AsyncTask< Object, Void, Bitmap > {
        ImageView ivPreview = null;

        @Override
        protected Bitmap doInBackground( Object... params ) {
            this.ivPreview = (ImageView) params[0];
            String url = (String) params[1];
            System.out.println(url);
            return loadBitmap( url );
        }

        @Override
        protected void onPostExecute( Bitmap result ) {
            super.onPostExecute( result );
            ivPreview.setImageBitmap( result );
        }
    }

    public Bitmap loadBitmap( String url ) {
        URL newurl = null;
        Bitmap bitmap = null;
        try {
            newurl = new URL( url );
            bitmap = BitmapFactory.decodeStream( newurl.openConnection().getInputStream());
        } catch ( MalformedURLException e ) {
            e.printStackTrace( );
            Log.d("MalformedURLException","1");
        } catch ( IOException e ) {
            Log.d("IOException", "2");
            e.printStackTrace( );
        }
        return bitmap;
    }

    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Loading Image ....");
            pDialog.show();

        }
        protected Bitmap doInBackground(String... args) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream)new URL(args[0]).getContent());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap image) {

            if(image != null){
                ibtn_Captcha.setImageBitmap(image);
                pDialog.dismiss();

            }else{

                pDialog.dismiss();
                Toast.makeText(MainActivity.this, "Image Does Not exist or Network Error", Toast.LENGTH_SHORT).show();

            }
        }
    }


}
