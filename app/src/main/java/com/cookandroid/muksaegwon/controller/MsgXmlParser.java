package com.cookandroid.muksaegwon.controller;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

import com.cookandroid.muksaegwon.model.Favorite;
import com.cookandroid.muksaegwon.model.Review;

public class MsgXmlParser {
    String data;
    private XmlPullParserFactory factory;
    private XmlPullParser xpp;
    int eventType;

    public MsgXmlParser(String data) {
        this.data = data;
    }

    public void xmlParsingRFM(ArrayList<Review> r){
        String review="", storeName="", date;
        short rating = 0;
        ArrayList<Review> reviews = r;
        try{
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            xpp = factory.newPullParser();
            xpp.setInput(new StringReader(data));
            eventType = xpp.getEventType();
            boolean reviewFlag=false, storeNameFlag=false, ratingFlag=false, dateFlag=false;

            while(eventType != XmlPullParser.END_DOCUMENT){
                if(eventType == XmlPullParser.START_TAG){
                    if (xpp.getName().equals("review")) reviewFlag = true;
                    else if (xpp.getName().equals("storeName")) storeNameFlag = true;
                    else if (xpp.getName().equals("rating")) ratingFlag = true;
                    else if (xpp.getName().equals("date")) dateFlag = true;
                } else if (eventType == XmlPullParser.TEXT){
                    if(reviewFlag){
                        review = xpp.getText();
                        reviewFlag = false;
                    } else if (storeNameFlag){
                        storeName = xpp.getText();
                        storeNameFlag = false;
                    } else if (ratingFlag){
                        rating = Short.parseShort(xpp.getText());
                        ratingFlag = false;
                    } else if (dateFlag){
                        date = xpp.getText();
                        dateFlag = false;
                        reviews.add(new Review(review,storeName,date,rating));
                    }
                }
                eventType = xpp.next();
            }
        }catch(Exception e) {
            Log.e("ERROR: ",e.toString());
        }
    }
    public void xmlParsingFFM(ArrayList<Favorite> f){
        ArrayList<Favorite> favorites = f;
        int storeId=0;
        String storeName="";

        try{
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            xpp = factory.newPullParser();
            xpp.setInput(new StringReader(data));
            eventType = xpp.getEventType();
            boolean storeIdFlag=false, storeNameFlag=false;

            while(eventType != XmlPullParser.END_DOCUMENT){
                if(eventType == XmlPullParser.START_TAG){
                    if (xpp.getName().equals("storeId")) storeIdFlag = true;
                    else if (xpp.getName().equals("storeName")) storeNameFlag = true;
                } else if (eventType == XmlPullParser.TEXT){
                    if(storeIdFlag){
                        storeId = Integer.parseInt(xpp.getText());
                    } else if (storeNameFlag){
                        storeName = xpp.getText();
                        favorites.add(new Favorite(storeId,storeName));
                    }
                    eventType = xpp.next();
                }
            }
        }catch(Exception e) {

        }
    }
}
