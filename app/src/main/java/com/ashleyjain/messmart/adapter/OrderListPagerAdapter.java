package com.ashleyjain.messmart.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ashleyjain.messmart.Fragment.List.OrderList;

/**
 * Created by ashleyjain on 24/05/16.
 */
public class OrderListPagerAdapter extends FragmentStatePagerAdapter{

    String upcomingOrderList,pastOrderList;

    int NumOfTabs;
    public OrderListPagerAdapter(FragmentManager fm, int NumOfTabs,String upcomingorderlist,String pastorderlist) {
        super(fm);
        this.NumOfTabs = NumOfTabs;
        upcomingOrderList = upcomingorderlist;
        pastOrderList = pastorderlist;
    }

    @Override
    public Fragment getItem(int position) {

        if(position == 0) {
            OrderList frag = new OrderList(upcomingOrderList);
            return frag;
        }
        if(position == 1){
            OrderList frag1 = new OrderList(upcomingOrderList);
            return frag1;
        }
        return null;
    }

    @Override
    public int getCount() {
        return NumOfTabs;
    }

    @Override
    public int getItemPosition(Object object) {
        //don't return POSITION_NONE, avoid fragment recreation.
        return POSITION_NONE;
    }
}
