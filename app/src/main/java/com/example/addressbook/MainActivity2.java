package com.example.addressbook;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;



import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    AlertDialog dialog ;//退出对话框
    View view1;
    int sex1=0;//用来判断是男还是女
    private EditText edit_name2,edit_tel,edit_home;//姓名，电话，家庭地址的输入框
    private TextView tv_name2,tv_tel,tv_home;//layout中的姓名，电话，家庭地址的显示文本
    private TextView tv_name22,tv_tel2,tv_home2,tv_sex2;//item中的姓名，电话，家庭地址的显示文本，用于listView中
    private Button btn_add,btn_del,btn_modify,btn_check,btn_exit;//增删改查四个按钮
    private RadioGroup radioGroup11;//男女的单选按钮组
    private RadioButton radio_man,radio_woman;//男女的单选按钮
    String name,tel,home,sex_man,sex_woman;//用于存放用户输入的数据
    long id;
    private ListView lv1;//用于显示数据库的后台数据
    List<InfoBean> infoBeanList= new ArrayList<>();//获取数据库的后台数据显示listView中

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        edit_name2=(EditText)findViewById(R.id.edit_name2);
        edit_tel=(EditText)findViewById(R.id.edit_tel);
        edit_home=(EditText)findViewById(R.id.edit_home);
        btn_add=(Button)findViewById(R.id.btn_add);
        btn_del=(Button)findViewById(R.id.btn_del);
        btn_modify=(Button)findViewById(R.id.btn_modify);
        btn_check=(Button)findViewById(R.id.btn_check);
        btn_exit=(Button)findViewById(R.id.btn_exit);
        radioGroup11=(RadioGroup)findViewById(R.id.radioGroup11);
        radio_man=(RadioButton)findViewById(R.id.radio_man);
        radio_woman=(RadioButton)findViewById(R.id.radio_woman);
        lv1=(ListView)findViewById(R.id.lv1);


        radioGroup11.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==R.id.radio_man)//判断用户点击的是哪个单选按钮
                {
                    sex1=0;
                }
                else{
                    sex1=1;
                }
            }
        });


    }

    public void click_exit(View view) {//退出按钮
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("退出对话框");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("是否退出通讯录：");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
                MainActivity2.this.finish();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
            }
        });
        dialog=builder.create();
        dialog.show();
    }

    public void click_add(View view) {//增加信息
        name=edit_name2.getText().toString();
        tel=edit_tel.getText().toString();
        home=edit_home.getText().toString();
        sex_man=radio_man.getText().toString();
        sex_woman=radio_woman.getText().toString();
        MyHelper helper=new MyHelper(MainActivity2.this);
        SQLiteDatabase db=helper.getWritableDatabase();//获取可读写SQLiteDatabase对象
        ContentValues values=new ContentValues();//创建ContentValues对象
        //将用户输入的数据添加到ContentValues对象
        values.put("name",name);
        values.put("tel",tel);
        values.put("home",home);
        if(sex1==0)
        {
            values.put("sex",sex_man);
        }
        else
        {
            values.put("sex",sex_woman);
        }
         id=db.insert("information",null,values);//将一条数据插入到数据库中
        Toast.makeText(this,"添加成功！",Toast.LENGTH_LONG).show();
        db.close();//关闭数据库
    }

    public void click_del(View view) {//删除信息


        MyHelper helper=new MyHelper(MainActivity2.this);
        SQLiteDatabase db=helper.getWritableDatabase();//获取可读写SQLiteDatabase对象
        //按姓名删除信号
        db.delete("information","name=?",new String[]{edit_name2.getText().toString().trim()});
        Toast.makeText(this,"删除成功！",Toast.LENGTH_LONG).show();
        db.close();//关闭数据库

    }

    public void click_modify(View view) {//修改信息
        name=edit_name2.getText().toString();
        tel=edit_tel.getText().toString();
        home=edit_home.getText().toString();
        sex_man=radio_man.getText().toString();
        sex_woman=radio_woman.getText().toString();
        MyHelper helper=new MyHelper(MainActivity2.this);
        SQLiteDatabase db=helper.getWritableDatabase();//获取可读写SQLiteDatabase对象
        ContentValues values=new ContentValues();//创建ContentValues对象
        //将用户输入的数据添加到ContentValues对象
        values.put("name",name);
        values.put("tel",tel);
        values.put("home",home);
        if(sex1==0)
        {
            values.put("sex",sex_man);
        }
        else
        {
            values.put("sex",sex_woman);
        }
        //按姓名修改数据
        db.update("information",values,"name=?",new String[]{edit_name2.getText().toString().trim()});
        Toast.makeText(this,"修改成功！",Toast.LENGTH_LONG).show();
        db.close();//关闭数据库

    }


    public void click_check(View view) {//查找信息
        infoBeanList.clear();//每点击一次查询，先清空listView中之前的数据
        MyHelper helper=new MyHelper(MainActivity2.this);
        SQLiteDatabase db=helper.getWritableDatabase();//获取可读写SQLiteDatabase对象
        //查询数据库中所有数据，将它们显示到listView中
        Cursor cursor=db.query("information",null,null,null,null,null,null);
        if(cursor.getCount()!=0)//判断数据库中是否为空
        {
            while (cursor.moveToNext())//显示数据库中所有数据
            {
                String name = cursor.getString(1);
                String tel = cursor.getString(2);
                String home = cursor.getString(3);
                String sex = cursor.getString(4);
                InfoBean infoBean=new InfoBean(name,tel,home,sex);
                infoBeanList.add(infoBean);
            }
        }

        cursor.close();
        db.close();
        MyBaseAdapter myBaseAdapter=new MyBaseAdapter();
        lv1.setAdapter(myBaseAdapter);
    }
    class MyBaseAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return infoBeanList.size();
        }//获得item的总数以及返回总数

        @Override
        public Object getItem(int i) {
            return infoBeanList.get(i);
        }//返回Item的数据对象

        @Override
        public long getItemId(int i) {
            return i;
        }//返回Item的id

        //得到View视图
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            //加载item.xml布局文件
            view1=View.inflate(MainActivity2.this,R.layout.item,null);
            tv_name22=(TextView)view1.findViewById(R.id.tv_name22);
            tv_tel2=(TextView)view1.findViewById(R.id.tv_tel2);
            tv_home2=(TextView)view1.findViewById(R.id.tv_home2);
            tv_sex2=(TextView)view1.findViewById(R.id.tv_sex2);
            tv_name22.setText(infoBeanList.get(i).getName());
            tv_tel2.setText(infoBeanList.get(i).getTel());
            tv_home2.setText(infoBeanList.get(i).getHome());
            tv_sex2.setText(infoBeanList.get(i).getSex());
            return view1;
        }
    }
}
