package com.cookandroid.muksaegwon.controller;

import android.util.Log;

import com.cookandroid.muksaegwon.model.Category;
import com.cookandroid.muksaegwon.model.Favorite;
import com.cookandroid.muksaegwon.model.Menu;
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

    public void xmlParsingRFM(ArrayList<Review> r) {
        String review = "", storeName = "", date;
        short rating = 0;
        ArrayList<Review> reviews = r;
        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            xpp = factory.newPullParser();
            xpp.setInput(new StringReader(data));
            eventType = xpp.getEventType();
            boolean reviewFlag = false, storeNameFlag = false, ratingFlag = false, dateFlag = false;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("review")) reviewFlag = true;
                    else if (xpp.getName().equals("storeName")) storeNameFlag = true;
                    else if (xpp.getName().equals("rating")) ratingFlag = true;
                    else if (xpp.getName().equals("date")) dateFlag = true;
                } else if (eventType == XmlPullParser.TEXT) {
                    if (reviewFlag) {
                        review = xpp.getText();
                        reviewFlag = false;
                    } else if (storeNameFlag) {
                        storeName = xpp.getText();
                        storeNameFlag = false;
                    } else if (ratingFlag) {
                        rating = Short.parseShort(xpp.getText());
                        ratingFlag = false;
                    } else if (dateFlag) {
                        date = xpp.getText();
                        dateFlag = false;
                        reviews.add(new Review(review, storeName, date, rating));
                    }
                }
                eventType = xpp.next();
            }
        } catch (Exception e) {
            Log.e("ERROR: ", e.toString());
        }
    }

    public void xmlParsingFFM(ArrayList<Favorite> f) {
        String storeName = null;
        ArrayList<Favorite> favorites = f;
        int storeId = 0;


        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            xpp = factory.newPullParser();
            xpp.setInput(new StringReader(data));
            eventType = xpp.getEventType();
            boolean storeIdFlag = false, storeNameFlag = false;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("storeId")) storeIdFlag = true;
                    else if (xpp.getName().equals("storeName")) storeNameFlag = true;
                } else if (eventType == XmlPullParser.TEXT) {
                    if (storeIdFlag) {
                        storeId = Integer.parseInt(xpp.getText());
                        storeIdFlag = false;
                    } else if (storeNameFlag) {
                        storeName = xpp.getText();
                        storeNameFlag = false;
                        favorites.add(new Favorite(storeId, storeName));
                    }
                }
                eventType = xpp.next();
            }
        } catch (Exception e) {

        }
    }

    public void xmlNearByPlaces(ArrayList<Store> s) {
        ArrayList<Store> stores = s;

        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            xpp = factory.newPullParser();
            xpp.setInput(new StringReader(data));
            eventType = xpp.getEventType();

            boolean storeNameFlag = false, latFlag = false, lngFlag = false;

            String storeName = "";
            double lat = 0, lng = 0;
            short isRunning = 0;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("storeName")) storeNameFlag = true;
                    else if (xpp.getName().equals("lat")) latFlag = true;
                    else if (xpp.getName().equals("lng")) lngFlag = true;

                } else if (eventType == XmlPullParser.TEXT) {
                    if (storeNameFlag) {
                        storeName = xpp.getText();
                        storeNameFlag = false;
                    } else if (latFlag) {
                        lat = Double.parseDouble(xpp.getText());
                        latFlag = false;
                    } else if (lngFlag) {
                        lng = Double.parseDouble(xpp.getText());
                        lngFlag = false;
                        stores.add(new Store(storeName, lat, lng));
                    }
                }
                eventType = xpp.next();
            }
        } catch (Exception e) {

        }

    }

    public void xmlParsingSFM(ArrayList<Store> s) {
        ArrayList<Store> stores = s;
        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            xpp = factory.newPullParser();
            xpp.setInput(new StringReader(data));
            eventType = xpp.getEventType();

            boolean storeNameFlag = false, latFlag = false, lngFlag = false, menuFlag = false,
                    payWayFlag = false, isRunningFlag = false, runDayFlag = false,
                    rtFlag = false, otFlag = false, caFlag = false;
            String storeName = "", runDay = "", openTime = "", offTime = "";
            double lat = 0, lng = 0;
            short isRunning = 0;
            JSONObject payWay = null;
            JSONArray menus = null;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("storeName")) storeNameFlag = true;
                    else if (xpp.getName().equals("lat")) latFlag = true;
                    else if (xpp.getName().equals("lng")) lngFlag = true;
                    else if (xpp.getName().equals("menu")) menuFlag = true;
                    else if (xpp.getName().equals("payWay")) payWayFlag = true;
                    else if (xpp.getName().equals("isRunning")) isRunningFlag = true;
                    else if (xpp.getName().equals("runDay")) runDayFlag = true;
                    else if (xpp.getName().equals("openTime")) rtFlag = true;
                    else if (xpp.getName().equals("offTime")) otFlag = true;
                    else if (xpp.getName().equals("category")) caFlag = true;
                } else if (eventType == XmlPullParser.TEXT) {
                    if (storeNameFlag) {
                        storeName = xpp.getText();
                        storeNameFlag = false;
                    } else if (latFlag) {
                        lat = Double.parseDouble(xpp.getText());
                        latFlag = false;
                    } else if (lngFlag) {
                        lng = Double.parseDouble(xpp.getText());
                        lngFlag = false;
                    } else if (menuFlag) {
                        try {
                            menus = new JSONArray(xpp.getText());
                        } catch (Exception e) {
                        }
                    } else if (payWayFlag) {
                        try {
                            payWay = new JSONObject(xpp.getText());
                        } catch (Exception e) {
                        }
                    } else if (isRunningFlag) {
                        isRunning = Short.parseShort(xpp.getText());
                        isRunningFlag = false;
                    } else if (runDayFlag) {
                        runDay = xpp.getText();
                        runDayFlag = false;
                    } else if (rtFlag) {
                        openTime = xpp.getText();
                        rtFlag = false;
                    } else if (otFlag) {
                        offTime = xpp.getText();
                        otFlag = false;
                        stores.add(new Store(storeName, lat, lng, menus, payWay, isRunning, runDay, openTime, offTime));
                    }
                }
                eventType = xpp.next();
            }
        } catch (Exception e) {
        }
    }

    public void jsonParsingMNP(JSONArray MNP) {
        ArrayList<Menu> menus = null;
        try {
            for (int i = 0; i < MNP.length(); i++) {
                JSONObject mObj = MNP.getJSONObject(i);
                String menuName = mObj.getString("name");
                String menuPrice = mObj.getString("price");
                menus.add(new Menu(menuName, menuPrice));
            }
        } catch (Exception e) {
        }
    }

    public void jsonParsingCTG(JSONObject CTG) {
        ArrayList<Category> categories = null;
        try {
            String corn = CTG.getString("corn");
            String fish = CTG.getString("fish");
            String topokki = CTG.getString("topokki");
            String eomuk = CTG.getString("eomuk");
            String sweetpotato = CTG.getString("sweetpotato");
            String toast = CTG.getString("toast");
            String takoyaki = CTG.getString("takoyaki");
            String waffle = CTG.getString("waffle");
            String dakggochi = CTG.getString("dakggochi");
            categories.add(new Category(corn, fish, topokki, eomuk, sweetpotato, toast, takoyaki, waffle, dakggochi));
        } catch (Exception e) {}
    }

}
