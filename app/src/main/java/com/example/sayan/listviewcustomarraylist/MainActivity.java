package com.example.sayan.listviewcustomarraylist;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    //steps to follow when creating a custom listview with array list and model objects
    //Prerequisite: get all the resources for the child data in one place
    //step 1: add a listview in your activity layout
    //step 2: find the listview by id in your activity java code
    //step 3: create a child layout without any context
    //step 4: define a custom adapter class
    //step 5: set the custom adapter object in the listview

    //resources for creating child objects
    private String[] textList = {"One", "Two", "Three", "Four", "Five", "Six"};
    private EditText editsearch;
    private CustomAdapter adapter;
    //declare an array list with ChildPojo class type(model object)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<ChildPojo> childPojoList = new ArrayList<>();
        //create each child object with resources and add it to the array list using for loop
        for (int i = 0; i < 6; i++) {
            childPojoList.add(new ChildPojo(R.drawable.image, textList[i]));    //here same image has been used to all child objects
        }
        //step 2
        ListView listView = (ListView) findViewById(R.id.listview_id);
        //step 5
        adapter = new CustomAdapter(this, childPojoList);
        listView.setAdapter(adapter);
        operateOnEditSearch();
    }


    private  void operateOnEditSearch(){
        // Locate the EditText in listview_main.xml
        editsearch = (EditText) findViewById(R.id.search);

        // Capture Text in EditText
        editsearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = editsearch.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filterList(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }
        });
    }
    //step 4
    //for detail explanation refer to previous project ListViewCustom
    private class CustomAdapter extends BaseAdapter {
        private final Context context;
        private List<ChildPojo> childPojoList = null;
        private ArrayList<ChildPojo> arraylist;

        public CustomAdapter(Context context, ArrayList<ChildPojo> childPojoList) {
            this.childPojoList = childPojoList;
            this.context = context;
            this.arraylist = new ArrayList<ChildPojo>();
            this.arraylist.addAll(childPojoList);
        }

        //this will return the number of child items
        @Override
        public int getCount() {
            return childPojoList.size();
        }

        //this will return the child item for a given position
        @Override
        public Object getItem(int i) {
            return childPojoList.get(i);
        }

        //this will return the id of a child item for a given position (here, for now just return the position itself)
        @Override
        public long getItemId(int i) {
            return i;
        }

        //this will return the view of a child item for a given position
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            //the view holder variable for storing the view holder object
            MyViewHolder myViewHolder = null;
            //for newly created item, view will be null
            if (view == null){
                //inflate the layout for getting the view
                LayoutInflater inflater = getLayoutInflater();
                View rootView = inflater.inflate(R.layout.child_layout, null);
                //create the view holder object with the inflated view
                myViewHolder = new MyViewHolder(rootView);
                //store the view holder object permanently in the listview for reusing it
                view = rootView;
                view.setTag(myViewHolder);
            }else {
                //for old child item, reuse using the view holder object
                myViewHolder = (MyViewHolder) view.getTag();
            }
            //childPojoList.get(i) returns a child model object corresponding to the position
            myViewHolder.imageView.setImageDrawable(getDrawable(childPojoList.get(i).getImage()));
            myViewHolder.textView.setText(childPojoList.get(i).getText());
            //return the view
            return myViewHolder.view;
        }

        public void filterList(String charText){
            charText = charText.toLowerCase(Locale.getDefault());
            childPojoList.clear();
            if (charText.length() == 0) {
                childPojoList.addAll(arraylist);
            }
            else
            {
                for (ChildPojo childPojo : arraylist)
                {
                    if (childPojo.getText().toLowerCase(Locale.getDefault()).startsWith(charText))
                    {
                        childPojoList.add(childPojo);
                    }
                }
            }
            notifyDataSetChanged();
        }

    }

    //the view holder class
    private class MyViewHolder{

        private final ImageView imageView;
        private final TextView textView;
        private final View view;

        //constructor of view holder
        MyViewHolder(View rootView){
            //initialize views
            imageView = rootView.findViewById(R.id.imageview_child_id);
            textView = rootView.findViewById(R.id.textview_child_id);
            view = rootView;
        }
    }
}