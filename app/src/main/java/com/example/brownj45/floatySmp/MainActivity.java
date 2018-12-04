package com.example.brownj45.floatySmp;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.brownj45.floatySmp.feed.VideoFeed;
import com.example.brownj45.floatySmp.feed.VideoFeedItem;
import com.example.brownj45.floatySmp.parser.BBCNewsVideoParser;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener, View.OnDragListener {

    //Some change to test SVN
    private static final String LOGCAT = MainActivity.class.getSimpleName();
    private WebView webView;
    ListView media;
    //final VideoFeedItem[] items;
    // http://www.bbc.co.uk/news/bigscreen/top_stories/iptvfeed.xml
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.videoWebView);
        webView.setWebChromeClient(new android.webkit.WebChromeClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://www.wyell.com/dash/dashwv.html");

        String bbbPlaylist = "{" +
                "holdingImageURL:'http://peach.blender.org/wp-content/uploads/bbb-splash.png'," +
                "title:'BBC R&D Dash stream - Big Buck Bunny'," +
                "items: [ { href:'http://rdmedia.bbc.co.uk/dash/ondemand/bbb/avc3/1/client_manifest-common_init.mpd' } ]" +
                "}";

        String parliamentPlaylist = "{" +
                "holdingImageURL:'http://www.bbc.co.uk/iplayer/images/tv/bbc_parliament_640_360.jpg'," +
                "title:'Video Factory Dash stream - BBC Parliament'," +
                "items: [ { href:'http://vs-hls-uk-live.edgesuite.net/pool_1/live/bbc_parliament/bbc_parliament.isml/bbc_parliament.mpd' } ]" +
                "}";

        String bbcOnePlaylist = "{" +
                "holdingImageURL:'http://www.bbc.co.uk/iplayer/images/tv/bbc_one_london_640_360.jpg'," +
                "title:'BBC R&D Dash stream - BBC One HD'," +
                "items: [ { href:'http://modavpkg1.eng.cloud.bbc.co.uk/usp/live/bbc1hd/bbc1hd.isml/bbc1hd.mpd' } ]" +
                "}";

        String bbbLiveLoopPlaylist = "{" +
                "holdingImageURL:'http://peach.blender.org/wp-content/uploads/bbb-splash.png'," +
                "title:'BBC R&D Dash Live Loop stream - Big Buck Bunny'," +
                "items: [ { href:'http://dash.bidi.int.bbc.co.uk/d/pseudolive/bbb/client_manifest.mpd' } ]" +
                "}";


        final VideoFeedItem[] items = {
                new VideoFeedItem("Big Buck Bunny OD", bbbPlaylist),
                new VideoFeedItem("Big Buck Bunny Live", bbbLiveLoopPlaylist),
                new VideoFeedItem("BBC One HD", bbcOnePlaylist),
                new VideoFeedItem("BBC Parliment", parliamentPlaylist),
                new VideoFeedItem("China and Russia in huge gas deal", "'http://playlists.bbc.co.uk/news/business-27502186A/playlist.sxml'"),
                new VideoFeedItem("Inside yacht family's 'ops' room", "'http://playlists.bbc.co.uk/news/world-us-canada-27498543A/playlist.sxml'"),
                new VideoFeedItem("Prince 'compared Putin to Hitler'", "'http://playlists.bbc.co.uk/news/world-us-canada-27498513A/playlist.sxml'"),
                new VideoFeedItem("Changes to child maintenance to begin","'http://playlists.bbc.co.uk/news/uk-27499036A/playlist.sxml'"),
                new VideoFeedItem("Mubarak jailed for embezzlement", "'http://playlists.bbc.co.uk/news/world-middle-east-27498547A/playlist.sxml'"),
                new VideoFeedItem("Royal Mail to trial Sunday deliveries", "'http://playlists.bbc.co.uk/news/uk-27499037A/playlist.sxml'"),
                new VideoFeedItem("CCTV footage at Hillsborough inquest", "'http://playlists.bbc.co.uk/news/uk-27504141A/playlist.sxml'"),
                new VideoFeedItem("Disease threat after Balkan floods", "'http://playlists.bbc.co.uk/news/world-europe-27497854A/playlist.sxml'"),
                new VideoFeedItem("Nigeria bombings toll 'passes 100'", "'http://playlists.bbc.co.uk/news/world-africa-27497853A/playlist.sxml'"),
                new VideoFeedItem("Can animals beat students' exam stress?", "'http://playlists.bbc.co.uk/news/education-27498519A/playlist.sxml'"),
                new VideoFeedItem("New French trains are 'too fat'", "'http://playlists.bbc.co.uk/news/world-europe-27502184A/playlist.sxml'"),
                new VideoFeedItem("World's first artificial surfing lake", "'http://playlists.bbc.co.uk/news/science-environment-27490880A/playlist.sxml'"),
        };

        media = (ListView) findViewById(R.id.media_listview);
        ArrayAdapter<VideoFeedItem> adapter = new ArrayAdapter<VideoFeedItem>(this,  android.R.layout.simple_list_item_1, items);
        media.setAdapter(adapter);

        media.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //String item = ((TextView)view).getText().toString();
                String item = items[position].getTitle();
                String playlist = items[position].getPlaylistUrl();
                Toast.makeText(getBaseContext(), item, Toast.LENGTH_LONG).show();
                webView.loadUrl("javascript:mp.loadPlaylist(" + playlist  + ");");
                webView.setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.videoWebView).setOnTouchListener(this);
        findViewById(R.id.topBit).setOnDragListener(this);
        findViewById(R.id.bottomBit).setOnDragListener(this);
        findViewById(R.id.trayBit).setOnDragListener(this);

//	    Display display = getWindowManager().getDefaultDisplay();
//	    Point size = new Point();
//	    display.getSize(size);
//	    int width = size.x;
//	    int height = size.y;
//		Log.d(LOGCAT, "#### screen Width: " + width);
//		Log.d(LOGCAT, "#### screen Hieght: " + height);


//		LinearLayout topBit = (LinearLayout) findViewById(R.id.videoWebView);
//		int videoWidth = topBit.getWidth();
//		Log.d(LOGCAT, "#### videoWidth: " + videoWidth);

//		LayoutParams params = (LayoutParams) topBit.getLayoutParams();
//		params.width = topBit.getWidth()/2;
//		params.height = topBit.getHeight()/2;

    }

    @Override
    public boolean onDrag(View layoutview, DragEvent dragevent) {

//		 Log.d(LOGCAT, "#### e.getRawY: " + dragevent.getY());
        View draggableView = (View) dragevent.getLocalState();

        boolean droppableArea = false;
        int action = dragevent.getAction();

        switch (action) {
            case DragEvent.ACTION_DRAG_STARTED:
                Log.d(LOGCAT, "Drag event started");
                Log.d(LOGCAT, "#### e.getRawY: " + dragevent.getY());

                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                droppableArea = true;
                Log.d(LOGCAT, "Drag event entered into "+layoutview.toString());
                Log.d(LOGCAT, "Drag event entered into ID "+layoutview.getId());
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                droppableArea = false;
                Log.d(LOGCAT, "Drag event exited from "+layoutview.toString());
                Log.d(LOGCAT, "Drag event exited from ID "+layoutview.getId());
                break;
            case DragEvent.ACTION_DROP:

                Log.d(LOGCAT, "### Dropped in " +layoutview.getId());
                Log.d(LOGCAT, "### Dropped in " +layoutview.getId());

                //View draggableView = (View) dragevent.getLocalState();
                ViewGroup previousOwner = (ViewGroup) draggableView.getParent();

                Log.d(LOGCAT, "PREVIOUS OWNER: " +  previousOwner.getId());
                Log.d(LOGCAT, "TOP: " +  R.id.topBit);
                Log.d(LOGCAT, "BOTTOM: " +  R.id.bottomBit);
                Log.d(LOGCAT, "TRAY: " +  R.id.trayBit);
                Log.d(LOGCAT, "PREVIOUS OWNER: " +  layoutview.getId());

                previousOwner.removeView(draggableView);
                LinearLayout newOwner = (LinearLayout) layoutview;

                if (newOwner.getId() != previousOwner.getId()) {
                    if(newOwner.getId() == R.id.trayBit || newOwner.getId() == R.id.bottomBit)  {
                        newOwner = (LinearLayout) findViewById(R.id.trayBit);
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) draggableView.getLayoutParams();
                        LinearLayout topBit = (LinearLayout) findViewById(R.id.topBit);
                        params.width = topBit.getWidth()/2;
                        params.height = topBit.getHeight()/2;
                        params.leftMargin = (topBit.getWidth()/2) - 20;

                        webView.loadUrl("javascript:mp.updateUiConfig({controls:{enabled:false}});");
                        RelativeLayout wholeLayout = (RelativeLayout) findViewById(R.id.center);

                        if(wholeLayout.getY() != -400) {
                            wholeLayout.setY(-400);
                            wholeLayout.getHeight();
                            wholeLayout.getLayoutParams().height = wholeLayout.getHeight() + 400;
                        }
                        Log.d(LOGCAT, "#### layoutparms.heght: " +  wholeLayout.getHeight() );
                        Log.d(LOGCAT, "#### wholeLayout.X.toString();: " +  wholeLayout.getY() );
                    }
                    else {
                        RelativeLayout wholeLayout = (RelativeLayout) findViewById(R.id.center);
                        wholeLayout.setY(0);
                        wholeLayout.getLayoutParams().height = wholeLayout.getHeight() - 400;
                        webView.loadUrl("javascript:mp.updateUiConfig({controls:{enabled:true}});");
                        draggableView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    }
                }

                newOwner.addView(draggableView);
                draggableView.setVisibility(View.VISIBLE);

                break;

            case DragEvent.ACTION_DRAG_ENDED:
                Log.d(LOGCAT, "Drag ended");
                Log.d(LOGCAT, "containsDragable " + droppableArea);
                Log.d(LOGCAT, "Drag eended "+layoutview.toString());
                Log.d(LOGCAT, "Drag eend "+layoutview.getId());

                if(!droppableArea) {
                    draggableView.setVisibility(View.VISIBLE);
                }

                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        Log.d(LOGCAT, "onTouchEvent: "+ motionEvent.getAction());


//		switch (motionEvent.getAction()) {
//			case MotionEvent.ACTION_MOVE:
//				Log.d(LOGCAT, "ACTION_MOVE");
//			      DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
//				  view.startDrag(null, shadowBuilder, view, 0);
//				  view.setVisibility(View.INVISIBLE);
//				return false;
//	        case MotionEvent.ACTION_DOWN:
//				Log.d(LOGCAT, "ACTION_DOWN");
//				return false;
//	        case MotionEvent.ACTION_UP:
//				Log.d(LOGCAT, "ACTION_UP");
//				return true;
//			default:
//				return false;
//		}

        if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDrag(null, shadowBuilder, view, 0);
            view.setVisibility(View.INVISIBLE);
            return true;
        }
        else {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                view.setVisibility(View.VISIBLE);
            }
            return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch(menuItem.getItemId()) {
            case R.id.menu_refresh :
                refresh();
                break;
            case R.id.menu_about :
                about();
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void refresh() {
        ConnectivityManager connectivityManager = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        boolean internetConnected = networkInfo!=null ? networkInfo.isConnectedOrConnecting() : false ;
        if(internetConnected) {
            new RetrieveBBCNewsFeed().execute("http://www.bbc.co.uk/news/bigscreen/top_stories/iptvfeed.xml");
        }
        else
        {
            new AlertDialog.Builder(this, android.R.style.Theme_Material_Light_Dialog_Alert )
                    .setTitle("Internet Connection Needed")
                    .setMessage("This app needs Internet connection to retrieve RSS.")
                    .setPositiveButton("OK",new DialogInterface.OnClickListener()	{
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();
        }

    }

    private void about() {
        Toast.makeText(MainActivity.this, "SMP Floaty 2014, Author Wyell Hanna", Toast.LENGTH_LONG).show();
    }

    private class RetrieveBBCNewsFeed extends AsyncTask<String,Void,VideoFeed>
    {
        @Override
        protected VideoFeed doInBackground(String... urls)
        {
            BBCNewsVideoParser bbcNewsRSSParser = new BBCNewsVideoParser();
            return bbcNewsRSSParser.parse(urls[0]);
        }

        @Override
        protected void onPostExecute(VideoFeed feed) {

            media = (ListView) findViewById(R.id.media_listview);
            ArrayAdapter<VideoFeedItem> adapter = new ArrayAdapter<VideoFeedItem>(getBaseContext(),  android.R.layout.simple_list_item_1, feed.getVideos());
            media.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }



}
