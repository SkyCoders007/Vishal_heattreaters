package com.mxi.demotablelayout.Activity;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mxi.demotablelayout.Db.SQLDatabase;
import com.mxi.demotablelayout.Modelclass.Mymodel;
import com.mxi.demotablelayout.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText a,b,c,d,e,f,g,h;
    SQLDatabase dbHelper;
    ImageView i;
    ArrayList<Mymodel> data;
    RecyclerView rv_recyclerview;
    RecyclerView.Adapter mAdapter;
    private DataAdapater adapter;

    Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        data = new ArrayList<Mymodel>();

        rv_recyclerview = (RecyclerView) findViewById(R.id.rv_recyclerview);
        dbHelper = new SQLDatabase(this);
        cursor = dbHelper.GetAllData();

        a = (EditText) findViewById(R.id.a);
        b = (EditText) findViewById(R.id.b);
        c = (EditText) findViewById(R.id.c);
        d = (EditText) findViewById(R.id.d);
        e = (EditText) findViewById(R.id.e);
        f = (EditText) findViewById(R.id.f);
        g = (EditText) findViewById(R.id.g);
        h = (EditText) findViewById(R.id.h);

        i = (ImageView) findViewById(R.id.i);

        i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dbHelper.InsertWishlistData(a.getText().toString().trim(), b.getText().toString().trim() + "", c.getText().toString().trim() + ""
                        , d.getText().toString().trim() + "", e.getText().toString().trim() + "", f.getText().toString().trim() + "", g.getText().toString().trim() + "", h.getText().toString().trim() + "");

                finish();
                startActivity(getIntent());

            }
        });

        getdata();



    }



    private void getdata() {
        try {
            Cursor c = dbHelper.GetAllData();

            if (c.getCount() != 0 && c != null) {
                c.moveToFirst();
                do {

                    Mymodel rt = new Mymodel();
                    rt.setToken(String.valueOf(c.getInt(0)));
                    rt.setPrticular(c.getString(1));
                    rt.setMat(c.getString(2));
                    rt.setProcess(c.getString(3));
                    rt.setHardness(c.getString(4));
                    rt.setHardness1(c.getString(5));
                    rt.setNo(c.getString(6));
                    rt.setWt(c.getString(7));

                    data.add(rt);


                } while (c.moveToNext());


            }

            if(data.size() > 0){






                rv_recyclerview.setVisibility(View.VISIBLE);
                rv_recyclerview.setHasFixedSize(true);
                rv_recyclerview.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
                adapter = new DataAdapater(MainActivity.this, data);
                rv_recyclerview.setAdapter(adapter);

            }else {
                rv_recyclerview.setVisibility(View.GONE);
                Toast.makeText(this, "There is no data available", Toast.LENGTH_LONG).show();
            }


        }catch (Exception e) {
            e.printStackTrace();
        }

        Log.e("Size", String.valueOf(data.size()));


    }

    public class DataAdapater extends RecyclerView.Adapter<DataAdapater.CustomViewHolder> {
        private Context context;
        ArrayList<Mymodel> data;
        private LayoutInflater mInflater;
        Dialog dialog;

        public DataAdapater(Context context, ArrayList<Mymodel> heartlist) {
            this.context = context;
            this.data = heartlist;
        }




        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

            View view = LayoutInflater.
                    from(viewGroup.getContext()).
                    inflate(R.layout.my_listing_data, viewGroup, false);

            CustomViewHolder viewHolder = new CustomViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final CustomViewHolder holder, final int i) {

            final Mymodel list = data.get(i);

            holder.et1.setText(list.getToken());
            holder.et2.setText(list.getPrticular());
            holder.et3.setText(list.getMat());
            holder.et4.setText(list.getHardness());
            holder.et5.setText(list.getNo());
            holder.et6.setText(list.getMat());
            holder.et7.setText(list.getWt());
            holder.et_hardness1.setText(list.getHardness1());



            holder.delete_data.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Log.e("click","yes");

                    String name = holder.et2.getText().toString().trim();
                    String id = dbHelper.getIdFromName(name);
                    String particular_name = holder.et3.getText().toString().trim();


                    String playerId=dbHelper.getinvoiceIdFromtokenname(particular_name,id);

                    Log.e("playerid_Adapter",playerId);
                    Log.e("name_Adapter",name);
                    Log.e("id_Adapter",id);
                    Log.e("token_no_Adapter",particular_name);


                    dbHelper.deleteTable(playerId);
//                    adapter.notifyDataSetChanged();


                    finish();
                    context.startActivity(getIntent());

                }
            });


        }

        private void showForgotPasswordPopup() {
            dialog = new Dialog(MainActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog_delete_password);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            DisplayMetrics metrics = getResources().getDisplayMetrics();
            int width = metrics.widthPixels;
            dialog.getWindow().setLayout((6 * width) / 7, ActionBar.LayoutParams.WRAP_CONTENT);

//            et_forgot_email = (EditText) dialog.findViewById(R.id.et_email_id);
//
//            TextView tv_close = (TextView) dialog.findViewById(R.id.tv_cancel);
//            TextView tv_apply = (TextView) dialog.findViewById(R.id.tv_apply);
//
//            tv_close.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialog.dismiss();
//                }
//            });
//            tv_apply.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    if (!cc.isConnectingToInternet()) {
//                        cc.showToast(getString(R.string.no_internet));
//
//                    } else {
//                        if (isValidateForgot()) {
//
//                            forgotPasswordWS();
//
//                        }
//                    }
//
//
//                }
//            });
            dialog.show();
        }

        @Override
        public int getItemCount() {
            return (null != data ? data.size() : 0);
        }

        public class CustomViewHolder extends RecyclerView.ViewHolder {

            EditText et1, et2,et3,et4,et5,et6,et7,et_hardness1;
            ImageView delete_data;

            public CustomViewHolder(View convertView) {
                super(convertView);

                et1 = (EditText) convertView.findViewById(R.id.et_1);
                et2 = (EditText) convertView.findViewById(R.id.et_2);
                et3 = (EditText) convertView.findViewById(R.id.et_3);
                et4 = (EditText) convertView.findViewById(R.id.et_4);
                et5 = (EditText) convertView.findViewById(R.id.et_5);
                et6 = (EditText) convertView.findViewById(R.id.et_6);
                et7 = (EditText) convertView.findViewById(R.id.et_7);
                et_hardness1 = (EditText) convertView.findViewById(R.id.et_hardness1);

                delete_data=(ImageView)convertView.findViewById(R.id.delete_data);

            }
        }


    }



}
