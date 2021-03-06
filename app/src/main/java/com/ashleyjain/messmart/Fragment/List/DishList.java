package com.ashleyjain.messmart.Fragment.List;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ashleyjain.messmart.Object.DishObject;
import com.ashleyjain.messmart.R;
import com.ashleyjain.messmart.adapter.dishObjectAdapter;
import com.ashleyjain.messmart.function.ExpandableHeightListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DishList extends ListFragment {

    String[] days = {"sun","fre","ce","csdc","vssz"};
    JSONArray menulistarray;

    private int[] messDishId,messId,t_avai,m_avai;
    private String[] messTitle,messLord;
    private String[] messDescription,messProfPic;
    private int[] messPrice,messBook;
    private boolean[] messIsVeg;
    private String[] messPic;
    private String[] messName,messAddress,messTime,messDatetime,messDatetext;

    private List<DishObject> messObjectList;
    dishObjectAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String menulist = getArguments().getString("menulist");
        try {
            menulistarray = new JSONArray(menulist);
            Integer len = menulistarray.length();
            messDishId=new int[len];
            messLord=new String[len];
            messId = new int[len];
            messTitle = new String[len];
            messDescription = new String[len];
            messPrice = new int[len];
            messIsVeg = new boolean[len];
            messPic = new String[len];
            messName = new String[len];
            messProfPic = new String[len];
            messAddress = new String[len];
            messTime = new String[len];
            messDatetime = new String[len];
            messBook = new int[len];
            messDatetext = new String[len];
            t_avai = new int[len];
            m_avai = new int[len];
            for(int i=0;i<len;i++){
                JSONObject fooditem = menulistarray.getJSONObject(i);
                messDishId[i]=fooditem.getInt("dishid");
                messLord[i]=fooditem.getString("lord");
                messId[i] = fooditem.getInt("mid");
                messTitle[i] = fooditem.getString("title");
                messProfPic[i] = fooditem.getString("profilepic_small");
                messDescription[i] = fooditem.getString("descp");
                messPrice[i] = fooditem.getInt("price");
                messIsVeg[i] = fooditem.getString("isveg")=="n"?false:true;
                messPic[i] = fooditem.getString("pic");
                messName[i] = fooditem.getString("name");
                messAddress[i] = fooditem.getString("address");
                messTime[i] = fooditem.getString("timing");
                messDatetime[i]=fooditem.getString("datetime");
                messBook[i]=fooditem.getInt("isbooked");
                messDatetext[i] = fooditem.getString("datetext");
                t_avai[i]=fooditem.getInt("t_avai");
                m_avai[i]=fooditem.getInt("m_avai");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        messObjectList = new ArrayList<DishObject>();
        for(int i = 0;i<messId.length;i++){
            DishObject items = new DishObject(messDishId[i],messLord[i],messId[i],messTitle[i],messDescription[i],messPrice[i],messIsVeg[i],messPic[i],messName[i],messAddress[i],messTime[i],messDatetime[i],messBook[i],messDatetext[i],messProfPic[i],t_avai[i],m_avai[i]);
            messObjectList.add(items);
        }
        System.out.println(messObjectList.size());
        adapter = new dishObjectAdapter(getActivity(), messObjectList);
        setListAdapter(adapter);
        return inflater.inflate(R.layout.activity_dish_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //ListView
        ExpandableHeightListView listView = (ExpandableHeightListView) view.findViewById(android.R.id.list);
        listView.setExpanded(true);
    }
}
