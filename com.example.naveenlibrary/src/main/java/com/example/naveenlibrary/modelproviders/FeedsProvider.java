package com.example.naveenlibrary.modelproviders;

import android.util.Log;

import com.example.naveenlibrary.models.Feeds;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 2114 on 26-07-2017.
 */

public class FeedsProvider
{
    private static final String TAG = "FeedsProvider";

    public static List<Feeds> parseJson(JSONArray response)
    {
        List<Feeds> feedsList = new ArrayList<>();
        for (int i=0; i<response.length(); i++)
        {
            try
            {
                JSONObject root = response.getJSONObject(i);
                JSONObject parent_one = root.getJSONObject("address");
                JSONObject parent_two = root.getJSONObject("company");
                JSONObject parent_one_child_one = parent_one.getJSONObject("geo");

                Feeds feeds = new Feeds();
                Feeds.Address address = feeds.new Address();
                Feeds.Company company = feeds.new Company();
                Feeds.Address.Geo geo = address.new Geo();

                //  root items
                feeds.id        = root.getInt("id");
                feeds.name      = root.getString("name");
                feeds.username  = root.getString("username");
                feeds.email     = root.getString("email");

                //  parent_one items
                address.street = parent_one.getString("street");
                address.suite = parent_one.getString("suite");
                address.city = parent_one.getString("city");
                address.zipcode = parent_one.getString("zipcode");
                feeds.address = address;

                //  parent_one_child_one items
                geo.lat = parent_one_child_one.getString("lat");
                geo.lng = parent_one_child_one.getString("lng");
                feeds.geo = geo;

                feeds.phone     = root.getString("phone");
                feeds.website   = root.getString("website");

                //  parent_two items
                company.name = parent_two.getString("name");
                company.catchPhrase = parent_two.getString("catchPhrase");
                company.bs = parent_two.getString("bs");
                feeds.company = company;

                feedsList.add(feeds);
            }
            catch (JSONException e)
            {
                Log.e(TAG,"Error In JSONObject"+e.getMessage());
            }
        }

        /*for (Feeds feeds:feedsList)
        {
            Log.i(TAG,feeds.company.name);
        }*/
        return feedsList;
    }
}
