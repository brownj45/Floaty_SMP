package com.example.brownj45.floatySmp.parser;

import android.util.Log;
import android.util.Xml;

import com.example.brownj45.floatySmp.feed.VideoFeed;
import com.example.brownj45.floatySmp.feed.VideoFeedItem;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class BBCNewsVideoParser {
	
	public BBCNewsVideoParser() {
		Log.d("BBCNewsVideoParser ","In BBCLocationParser constructor");
	}
	

	public VideoFeed parse(String feedURL) {
		
		VideoFeed videos = new VideoFeed();
		
		URL url = null;
		try {
			url = new URL(feedURL);
		}
		catch(MalformedURLException malformedURLException) {
			Log.e("Location feed error: ","Exception",malformedURLException);
		}		
				
		HttpURLConnection conn = null;
		InputStream inputStream = null;
		try {
			conn = (HttpURLConnection)url.openConnection();
			conn.setReadTimeout(10*1000);
			conn.setConnectTimeout(20*1000);
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			conn.connect();
			inputStream = conn.getInputStream();
		}
		catch(IOException ioException) {
			Log.e("Connection error: ","Exception",ioException);
		}
		
		XmlPullParser parser = Xml.newPullParser();
		try {
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, Boolean.TRUE);
			parser.setInput(inputStream, null);
			parser.nextTag();
			while(parser.next()!=XmlPullParser.END_DOCUMENT) {
				int eventType = parser.getEventType();
				
		        	if(eventType==XmlPullParser.START_TAG && parser.getName().equals("title")) {
		        		parser.next();
		        		videos.setTitle(parser.getText());
		        		Log.e("title = ", "" + parser.getText());
		        		continue;
		        	}
		        	if(eventType==XmlPullParser.START_TAG && parser.getName().equals("lastUpdated")) {
		        		parser.next();
		        		videos.setLastUpdated(parser.getText());
		        		Log.e("lastUpdated = ", "" + parser.getText());
		        		continue;
		        	}
				if(eventType==XmlPullParser.START_TAG && parser.getName().equals("item")) {
					Log.e("going into item = ", "");

					videos.addVideoItem(parseItem(parser));
				}
			}
		
		}
		catch(XmlPullParserException xmlPullParserException) {
			Log.e("XML Parser Error: ","Exception",xmlPullParserException);
		}
		catch(IOException ioException) {
			Log.e("I/O Error: ","Exception",ioException);
		}				
		return videos;
	}

	private VideoFeedItem parseItem(XmlPullParser parser) throws XmlPullParserException, IOException {
		VideoFeedItem videoItem = new VideoFeedItem();

		while(parser.next()!=XmlPullParser.END_TAG) {
			int eventType = parser.getEventType();
			Log.e("parser.getName(): ", ""+parser.getName());
			Log.e("parser. line nymber: ", ""+parser.getLineNumber());
			if(eventType==XmlPullParser.START_TAG && parser.getName().equals("id")) {
				parser.next();
				videoItem.setId(parser.getText());
        			Log.e("id = ", "" + parser.getText());
				parser.next();
			}
			if(eventType==XmlPullParser.START_TAG && parser.getName().equals("sectionName")) {
				parser.next();
				videoItem.setSection(parser.getText());
        			Log.e("section = ", "" + parser.getText());
				parser.next();
			}
			if(eventType==XmlPullParser.START_TAG && parser.getName().equals("sectionId")) {
				parser.next();
				Log.e("sectionId = ", "" + parser.getText());
				parser.next();
			}
			if(eventType==XmlPullParser.START_TAG && parser.getName().equals("suitableForAdvertising")) {
				parser.next();
				Log.e("suitableForAdvertising = ", "" + parser.getText());
				parser.next();
			}
			if(eventType==XmlPullParser.START_TAG && parser.getName().equals("lastPublishedDate")) {
				parser.next();
				Log.e("lastPublishedDate = ", "" + parser.getText());
				parser.next();
			}
			if(eventType==XmlPullParser.START_TAG && (parser.getName().equals("guidance")) ) {
				parser.next();
				Log.e("guidance = ", "" + parser.getText());
			}
			if(eventType==XmlPullParser.START_TAG && (parser.getName().equals("caption")) ) {
				parser.next();
				Log.e("caption = ", "" + parser.getText());
				parser.next();
			}
			if(eventType==XmlPullParser.START_TAG && (parser.getName().equals("duration")) ) {
				parser.next();
				Log.e("guidance = ", "" + parser.getText());
				parser.next();
			}
			if(eventType==XmlPullParser.START_TAG && (parser.getName().equals("pid")) ) {
				parser.next();
				Log.e("pid = ", "" + parser.getText());
				parser.next();
			}
			if(eventType==XmlPullParser.START_TAG && (parser.getName().equals("mpsId")) ) {
				parser.next();
				Log.e("guidance = ", "" + parser.getText());
			}
			if(eventType==XmlPullParser.START_TAG && (parser.getName().equals("isLive")) ) {
				parser.next();
				Log.e("isLive = ", "" + parser.getText());
				parser.next();
			}
			if(eventType==XmlPullParser.START_TAG && parser.getName().equals("title")) {
				parser.next();
				videoItem.setTitle(parser.getText());
        			Log.e("title2 = ", "" + parser.getText());
				parser.next();
			}
			if(eventType==XmlPullParser.START_TAG && parser.getName().equals("description")) {
				parser.next();
				Log.e("desc = ", "" + parser.getText());
				videoItem.setDescription(parser.getText());
				parser.next();
			}
			if(eventType==XmlPullParser.START_TAG && parser.getName().equals("indexImage")) {
				parser.next();
				Log.e("image = ", "" + parser.getText());
				videoItem.setIndexImage(parser.getText());
				parser.next();
			}
			if(eventType==XmlPullParser.START_TAG && parser.getName().equals("playlistUrl")) {
				parser.next();
				videoItem.setPlaylistUrl(parser.getText());
				Log.e("PLAYLIST = ", "" + parser.getText());
				parser.next();
			}
		}
		return videoItem;
	}

}
