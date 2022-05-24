package com.cookandroid.muksaegwon.controller;

import android.util.Log;

import com.cookandroid.muksaegwon.model.Favorite;
import com.cookandroid.muksaegwon.model.Review;
import com.cookandroid.muksaegwon.model.Store;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

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

    public void xmlNearByPlaces(ArrayList<Store> s){
        ArrayList<Store> stores = s;
        try{
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            xpp = factory.newPullParser();
            xpp.setInput(new StringReader(data));
            eventType = xpp.getEventType();

            boolean storeNameFlag=false, latFlag=false, lngFlag=false, menuFlag=false,
                    payWayFlag=false, isRunningFlag=false, runDayFlag=false,
                    rtFlag=false, otFlag=false;
            String storeName="", runDay="", openTime="", offTime="";
            double lat=0, lng=0;
            short isRunning=0;
            JSONObject payWay = new JSONObject();
            JSONArray menu = new JSONArray();

            while(eventType != XmlPullParser.END_DOCUMENT){
                if(eventType == XmlPullParser.START_TAG){
                    if (xpp.getName().equals("storeName")) storeNameFlag = true;
                    else if (xpp.getName().equals("lat")) latFlag = true;
                    else if (xpp.getName().equals("lng")) lngFlag = true;
                    else if (xpp.getName().equals("menu")) menuFlag = true;
                    else if (xpp.getName().equals("payWay")) payWayFlag = true;
                    else if (xpp.getName().equals("isRunning")) isRunningFlag = true;
                    else if (xpp.getName().equals("runDay")) runDayFlag = true;
                    else if (xpp.getName().equals("openTime")) rtFlag = true;
                    else if (xpp.getName().equals("offTime")) otFlag = true;
                } else if (eventType == XmlPullParser.TEXT){
                    if(storeNameFlag){
                        storeName = xpp.getText();
                    } else if (latFlag){
                        lat = Double.parseDouble(xpp.getText());
                    } else if (lngFlag){
                        lng = Double.parseDouble(xpp.getText());
                    } else if (menuFlag){
                        // JSON PARSING
                    } else if (payWayFlag){
                        // JSON PARSING
                    } else if (isRunningFlag){
                        isRunning = Short.parseShort(xpp.getText());
                    } else if (runDayFlag){
                        runDay = xpp.getText();
                    } else if (rtFlag){
                        openTime = xpp.getText();
                    } else if (otFlag){
                        offTime = xpp.getText();
                        s.add(new Store(storeName,lat,lng,menu,payWay,isRunning,runDay,openTime,offTime));
                    }
                }
                eventType = xpp.next();
            }
        } catch(Exception e) {

        }
    }
}
