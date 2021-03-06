package com.example.madchocho.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.madchocho.myapplication.ContactUtil;
import com.example.madchocho.myapplication.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class
TestAddressBook extends Activity
{
    private ListView lv;
    private boolean goon=false;

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS=10;



    @Override
    public void onCreate(Bundle savedInstanceState)
    { //System.out.println("here");

        goon=false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView)findViewById(R.id.list);

        ArrayList<Person> m_orders = new ArrayList<Person>();


        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);

        if(permissionCheck== PackageManager.PERMISSION_DENIED){


                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.READ_CONTACTS},
                            MY_PERMISSIONS_REQUEST_READ_CONTACTS);


            //permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);

                //onCreate(savedInstanceState);




                } else {
            // 폰 주소록
            Map<String, String> phone_address = ContactUtil.getAddressBook(this);

            @SuppressWarnings("rawtypes")
            Iterator ite = phone_address.keySet().iterator();
            while(ite.hasNext())
            {
                String phone = ite.next().toString();
                String name = phone_address.get(phone).toString();
                m_orders.add(new Person(name, phone));
            }

            PersonAdapter m_adapter = new PersonAdapter(this, R.layout.view_friend_list, m_orders);
            lv.setAdapter(m_adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                public void onItemClick(AdapterView<?> parent, View view, int position, long rowID)
                {
                    doSelectFriend((Person)parent.getItemAtPosition(position));
                }});

                    // 필요한 권한과 요청 코드를 넣어서 권한허가요청에 대한 결과를 받아야 합니다

                }





    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if(requestCode==MY_PERMISSIONS_REQUEST_READ_CONTACTS&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
            lv = (ListView)findViewById(R.id.list);
            ArrayList<Person> m_orders = new ArrayList<Person>();
            Map<String, String> phone_address = ContactUtil.getAddressBook(this);

            @SuppressWarnings("rawtypes")
            Iterator ite = phone_address.keySet().iterator();
            while(ite.hasNext())
            {
                String phone = ite.next().toString();
                String name = phone_address.get(phone).toString();
                m_orders.add(new Person(name, phone));
            }

            PersonAdapter m_adapter = new PersonAdapter(this, R.layout.view_friend_list, m_orders);
            lv.setAdapter(m_adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                public void onItemClick(AdapterView<?> parent, View view, int position, long rowID)
                {
                    doSelectFriend((Person)parent.getItemAtPosition(position));
                }});
            //goon=true;
            //onCreate(savedInstanceState);
            Toast.makeText(this,"주소록 접근이 승인되었습니다.",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"주소록 접근이 거절되었습니다. 추가 승인이 필요합니다.",Toast.LENGTH_SHORT).show();

        }


    }
    // 한명 선택했을 때
    public void doSelectFriend(Person p)
    {
        Log.e("####", p.getName() + ", " + p.getNumber());
    }

    public class PersonAdapter extends ArrayAdapter<Person>
    {
        private ArrayList<Person> items;

        public PersonAdapter(Context context, int textViewResourceId, ArrayList<Person> items)
        {
            super(context, textViewResourceId, items);
            this.items = items;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View v = convertView;
            if (v == null)
            {
                LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.view_friend_list, null);
            }
            Person p = items.get(position);
            if (p != null)
            {
                TextView tt = (TextView) v.findViewById(R.id.name);
                TextView bt = (TextView) v.findViewById(R.id.msg);
                if (tt != null)
                {
                    tt.setText(p.getName());
                }
                if(bt != null)
                {
                    bt.setText("전화번호: "+ p.getNumber());
                }
            }
            return v;
        }
    }

    class Person
    {
        private String Name;
        private String Number;

        public Person(String _Name, String _Number)
        {
            this.Name = _Name;
            this.Number = _Number;
        }

        public String getName()
        {
            return Name;
        }

        public String getNumber()
        {
            return Number;
        }
    }
}



