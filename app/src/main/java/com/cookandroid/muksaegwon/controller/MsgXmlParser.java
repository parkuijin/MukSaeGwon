package com.cookandroid.muksaegwon.controller;

import android.util.Log;

import com.cookandroid.muksaegwon.model.Favorite;
import com.cookandroid.muksaegwon.model.Menu;
import com.cookandroid.muksaegwon.model.PayWay;
import com.cookandroid.muksaegwon.model.Review;
import com.cookandroid.muksaegwon.model.Store;
import com.cookandroid.muksaegwon.model.StoreReview;

import org.json.JSONArray;
import org.json.JSONException;
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

    public void xmlParsingSRFM(ArrayList<StoreReview> sr) {
        String review = "", date;
        short rating = 0;
        ArrayList<StoreReview> storeReviews = sr;
        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            xpp = factory.newPullParser();
            xpp.setInput(new StringReader(data));
            eventType = xpp.getEventType();
            boolean reviewFlag = false, ratingFlag = false, dateFlag = false;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("review")) reviewFlag = true;
                    else if (xpp.getName().equals("rating")) ratingFlag = true;
                    else if (xpp.getName().equals("date")) dateFlag = true;
                } else if (eventType == XmlPullParser.TEXT) {
                    if (reviewFlag) {
                        review = xpp.getText();
                        reviewFlag = false;
                    } else if (ratingFlag) {
                        rating = Short.parseShort(xpp.getText());
                        ratingFlag = false;
                    } else if (dateFlag) {
                        date = xpp.getText();
                        dateFlag = false;
                        storeReviews.add(new StoreReview(review, date, rating));
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

            boolean storeIdFlag=false, storeNameFlag = false, latFlag = false, lngFlag = false;

            String storeName = "", storeId ="";
            double lat = 0, lng = 0;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("storeId")) storeIdFlag = true;
                    else if (xpp.getName().equals("storeName")) storeNameFlag = true;
                    else if (xpp.getName().equals("lat")) latFlag = true;
                    else if (xpp.getName().equals("lng")) lngFlag = true;
                } else if (eventType == XmlPullParser.TEXT) {
                    if (storeIdFlag) {
                        storeId = xpp.getText();
                        storeIdFlag = false;
                    } else if (storeNameFlag) {
                        storeName = xpp.getText();
                        storeNameFlag = false;
                    } else if (latFlag) {
                        lat = Double.parseDouble(xpp.getText());
                        latFlag = false;
                    } else if (lngFlag) {
                        lng = Double.parseDouble(xpp.getText());
                        lngFlag = false;
                        stores.add(new Store(storeId, storeName, lat, lng));
                    }
                }
                eventType = xpp.next();
            }
        } catch (Exception e) {

        }

    }

    public void xmlParsingForStore(Store s) {
         Store store = s;
        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            xpp = factory.newPullParser();
            xpp.setInput(new StringReader(data));
            eventType = xpp.getEventType();

            boolean storeIdFlag=false, storeNameFlag = false, storeLocationFlag=false, latFlag = false, lngFlag = false, menuFlag = false,
                    payWayFlag = false, isRunningFlag = false, runDayFlag = false,
                    rtFlag = false, otFlag = false, caFlag = false;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("storeId")) storeIdFlag = true;
                    else if (xpp.getName().equals("storeName")) storeNameFlag = true;
                    else if (xpp.getName().equals("storeLocation")) storeLocationFlag = true;
                    else if (xpp.getName().equals("lat")) latFlag = true;
                    else if (xpp.getName().equals("lng")) lngFlag = true;
                    else if (xpp.getName().equals("category")) caFlag = true;
                    else if (xpp.getName().equals("menu")) menuFlag = true;
                    else if (xpp.getName().equals("payWay")) payWayFlag = true;
                    else if (xpp.getName().equals("isRunning")) isRunningFlag = true;
                    else if (xpp.getName().equals("runDay")) runDayFlag = true;
                    else if (xpp.getName().equals("openTime")) rtFlag = true;
                    else if (xpp.getName().equals("offTime")) otFlag = true;
                } else if (eventType == XmlPullParser.TEXT) {
                    if (storeIdFlag) {
                        store.setStoreId(xpp.getText());
                        storeIdFlag = false;
                    } else if (storeNameFlag) {
                        store.setStoreName(xpp.getText());
                        storeNameFlag = false;
                    } else if (storeLocationFlag) {
                        store.setStoreLocation(xpp.getText());
                        storeLocationFlag = false;
                    } else if (latFlag) {
                        store.setLat(Double.parseDouble(xpp.getText()));
                        latFlag = false;
                    } else if (lngFlag) {
                        store.setLng(Double.parseDouble(xpp.getText()));
                        lngFlag = false;
                    } else if (menuFlag) {
                        try {
                            store.setMenus(new JSONArray(xpp.getText()));
                            menuFlag = false;
                        } catch (Exception e) {
                        }
                    } else if (payWayFlag) {
                        try {
                            store.setPayWay(new JSONObject(xpp.getText()));
                            payWayFlag = false;
                        } catch (Exception e) {
                        }
                    } else if (isRunningFlag) {
                        store.setIsRunning(Short.parseShort(xpp.getText()));
                        isRunningFlag = false;
                    } else if (runDayFlag) {
                        store.setRunDay(new JSONObject(xpp.getText()));
                        runDayFlag = false;
                    } else if (rtFlag) {
                        store.setOpenTime(xpp.getText());
                        rtFlag = false;
                    } else if (otFlag) {
                        store.setOffTime(xpp.getText());
                        otFlag = false;
                    } else if (caFlag) {
                        try {
                            store.setCategory(new JSONObject(xpp.getText()));
                            caFlag = false;
                        } catch (Exception e) {}
                    }
                }
                eventType = xpp.next();
            }
        } catch (Exception e) {}
    }

    public void jsonParsingMNP(JSONArray MNP, ArrayList<Menu> m) {
        ArrayList<Menu> menus = m;
        try {
            for (int i = 0; i < MNP.length(); i++) {
                JSONObject mObj = MNP.getJSONObject(i);
                String menuName = mObj.getString("name");
                String menuPrice = mObj.getString("price");
                menus.add(new Menu(menuName, menuPrice));
            }
        } catch (Exception e) {}
    }

    public void jsonParsingCTG(JSONObject CTG, ArrayList<Boolean> c) {
        try {
            Boolean corn = Boolean.parseBoolean(CTG.getString("corn"));
            Boolean fish = Boolean.parseBoolean(CTG.getString("fish"));
            Boolean topokki = Boolean.parseBoolean(CTG.getString("topokki"));
            Boolean eomuk = Boolean.parseBoolean(CTG.getString("eomuk"));
            Boolean sweetpotato = Boolean.parseBoolean(CTG.getString("sweetpotato"));
            Boolean toast = Boolean.parseBoolean(CTG.getString("toast"));
            Boolean takoyaki = Boolean.parseBoolean(CTG.getString("takoyaki"));
            Boolean waffle = Boolean.parseBoolean(CTG.getString("waffle"));
            Boolean dakggochi = Boolean.parseBoolean(CTG.getString("dakggochi"));
            c.add(corn);
            c.add(fish);
            c.add(topokki);
            c.add(eomuk);
            c.add(sweetpotato);
            c.add(toast);
            c.add(takoyaki);
            c.add(waffle);
            c.add(dakggochi);
        } catch (Exception e) {}
    }

    public void jsonParsingPW(JSONObject PW, ArrayList<PayWay> p) {
        ArrayList<PayWay> payWays = p;
        try {
            String cash = PW.getString("cash");
            String card = PW.getString("card");
            String account = PW.getString("account");
            payWays.add(new PayWay(cash, card, account));
        } catch (Exception e) {}
    }

    public void payWayInfo(JSONObject payWay, ArrayList<Boolean> b){
        try{
            Boolean cash = Boolean.parseBoolean(payWay.getString("cash"));
            Boolean card = Boolean.parseBoolean(payWay.getString("card"));
            Boolean account = Boolean.parseBoolean(payWay.getString("account"));
            b.add(card);
            b.add(cash);
            b.add(account);
        } catch (Exception e){}
    }
    public void daysInfo(JSONObject days, ArrayList<Boolean> b){
        try{
            Boolean mon = Boolean.parseBoolean(days.getString("mon"));
            Boolean tue = Boolean.parseBoolean(days.getString("tue"));
            Boolean wed = Boolean.parseBoolean(days.getString("wed"));
            Boolean thu = Boolean.parseBoolean(days.getString("thu"));
            Boolean fri = Boolean.parseBoolean(days.getString("fri"));
            Boolean sat = Boolean.parseBoolean(days.getString("sat"));
            Boolean sun = Boolean.parseBoolean(days.getString("sun"));

            b.add(mon);
            b.add(tue);
            b.add(wed);
            b.add(wed);
            b.add(fri);
            b.add(sat);
            b.add(sun);
        } catch (Exception e){}
    }

    public void menuInfo(JSONArray menus, ArrayList<Menu> m) {
        JSONObject menu;
        String name;
        String price;
        try {
            for (int i = 0; i < menus.length(); i++) {
                menu = menus.getJSONObject(i);
                name = menu.getString("name");
                price = menu.getString("price");
                m.add(new Menu(name,price));
            }
        } catch (JSONException e){
            Log.e("JSONException: ", e.toString());
        }
    }
}
