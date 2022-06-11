package com.cookandroid.muksaegwon.controller;

import android.util.Log;

import com.cookandroid.muksaegwon.model.Favorite;
import com.cookandroid.muksaegwon.model.Menu;
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
    public MsgXmlParser() {}

    public void xmlParsingRFM(ArrayList<Review> r) {
        String review = "", storeName = "", date;
        float rating = 0;
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
                        rating = Float.parseFloat(xpp.getText());
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
        String userBy="", review = "", date;
        float rating = 0;
        ArrayList<StoreReview> storeReviews = sr;
        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            xpp = factory.newPullParser();
            xpp.setInput(new StringReader(data));
            eventType = xpp.getEventType();
            boolean userByFlag = false, reviewFlag = false, ratingFlag = false, dateFlag = false;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("userBy")) userByFlag = true;
                    else if (xpp.getName().equals("review")) reviewFlag = true;
                    else if (xpp.getName().equals("rating")) ratingFlag = true;
                    else if (xpp.getName().equals("date")) dateFlag = true;
                } else if (eventType == XmlPullParser.TEXT) {
                    if (userByFlag) {
                        userBy = xpp.getText();
                        userByFlag = false;
                    } else if (reviewFlag) {
                        review = xpp.getText();
                        reviewFlag = false;
                    } else if (ratingFlag) {
                        rating = Float.parseFloat(xpp.getText());
                        ratingFlag = false;
                    } else if (dateFlag) {
                        date = xpp.getText();
                        dateFlag = false;
                        storeReviews.add(new StoreReview(userBy, review, date, rating));
                    }
                }
                eventType = xpp.next();
            }
        } catch (Exception e) {
            Log.e("ERROR: ", e.toString());
        }
    }

    public void xmlParsingFFM(ArrayList<Favorite> f) {
        String storeName = null, storeLocation = null;
        ArrayList<Favorite> favorites = f;
        int storeId = 0;

        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            xpp = factory.newPullParser();
            xpp.setInput(new StringReader(data));
            eventType = xpp.getEventType();
            boolean storeIdFlag = false, storeNameFlag = false, storeLocationFlag = false;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("storeId")) storeIdFlag = true;
                    else if (xpp.getName().equals("storeName")) storeNameFlag = true;
                    else if (xpp.getName().equals("storeLocation")) storeLocationFlag = true;
                } else if (eventType == XmlPullParser.TEXT) {
                    if (storeIdFlag) {
                        storeId = Integer.parseInt(xpp.getText());
                        storeIdFlag = false;
                    } else if (storeNameFlag) {
                        storeName = xpp.getText();
                        storeNameFlag = false;
                    } else if (storeLocationFlag) {
                        storeLocation = xpp.getText();
                        storeLocationFlag = false;
                        favorites.add(new Favorite(storeId, storeName, storeLocation));
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

            boolean storeIdFlag = false, storeNameFlag = false, latFlag = false, lngFlag = false;

            String storeName = "", storeId = "";
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

    public void testParsing(){
        Store store = new Store();
        String data = "<StoreInfo><storeId>2019</storeId><storeName>normal</storeName><storeLocation></storeLocation><lat>37.58125385605374</lat><lng>126.92555107176305</lng><category>{\"corn\": false, \"fish\": true, \"eomuk\": false, \"toast\": false, \"waffle\": false, \"topokki\": false, \"takoyaki\": false, \"dakggochi\": false, \"sweetpotato\": false}</category><menu>[{\"name\": \"teset\", \"price\": \"1000\"}]</menu><payWay>{\"card\": false, \"cash\": true, \"account\": false}</payWay><isRunning>0</isRunning><runDay>{\"fri\": false, \"mon\": true, \"sat\": false, \"sun\": false, \"thu\": false, \"tue\": true, \"wed\": true}</runDay><openTime>1</openTime><offTime>2</offTime></StoreInfo>";
        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            xpp = factory.newPullParser();

            xpp.setInput(new StringReader(data));
            eventType = xpp.getEventType();

            boolean storeIdFlag = false, storeNameFlag = false, storeLocationFlag = false, latFlag = false, lngFlag = false, menuFlag = false,
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
                    } else if (caFlag) {
                        try {
                            store.setCategory(new JSONObject(xpp.getText()));
                            caFlag = false;
                        } catch (Exception e) {
                        }
                    }   else if (menuFlag) {
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
                    }
                }
                eventType = xpp.next();
            }
        } catch (Exception e) {
            Log.e("ERROR PARSING:", e.toString());
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

            boolean storeIdFlag = false, storeNameFlag = false, storeLocationFlag = false, latFlag = false, lngFlag = false, menuFlag = false,
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
                    } else if (caFlag) {
                        try {
                            store.setCategory(new JSONObject(xpp.getText()));
                            caFlag = false;
                        } catch (Exception e) {
                        }
                    }   else if (menuFlag) {
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
                    }
                }
                eventType = xpp.next();
            }
        } catch (Exception e) {
            Log.e("ERROR PARSING:", e.toString());
        }
    }

    public void payWayInfo(JSONObject payWay, ArrayList<Boolean> b) {
        try {
            Boolean cash = Boolean.parseBoolean(payWay.getString("cash"));
            Boolean card = Boolean.parseBoolean(payWay.getString("card"));
            Boolean account = Boolean.parseBoolean(payWay.getString("account"));
            b.add(cash);
            b.add(card);
            b.add(account);

        } catch (Exception e) {
        }
    }

    public void daysInfo(JSONObject days, ArrayList<Boolean> b) {
        try {
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
            b.add(thu);
            b.add(fri);
            b.add(sat);
            b.add(sun);
        } catch (Exception e) {
        }
    }

    public void categoryInfo(JSONObject category, ArrayList<Boolean> b) {
        try {
            Boolean corn = Boolean.parseBoolean(category.getString("corn"));
            Boolean fish = Boolean.parseBoolean(category.getString("fish"));
            Boolean topokki = Boolean.parseBoolean(category.getString("topokki"));
            Boolean eomuk = Boolean.parseBoolean(category.getString("eomuk"));
            Boolean sweetpotato = Boolean.parseBoolean(category.getString("sweetpotato"));
            Boolean toast = Boolean.parseBoolean(category.getString("toast"));
            Boolean takoyaki = Boolean.parseBoolean(category.getString("takoyaki"));
            Boolean waffle = Boolean.parseBoolean(category.getString("waffle"));
            Boolean dakggochi = Boolean.parseBoolean(category.getString("dakggochi"));

            b.add(corn);
            b.add(fish);
            b.add(topokki);
            b.add(eomuk);
            b.add(sweetpotato);
            b.add(toast);
            b.add(takoyaki);
            b.add(waffle);
            b.add(dakggochi);
        } catch (Exception e) {
        }
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
                m.add(new Menu(name, price));
            }
        } catch (JSONException e) {
            Log.e("JSONException: ", e.toString());
        }
    }
}