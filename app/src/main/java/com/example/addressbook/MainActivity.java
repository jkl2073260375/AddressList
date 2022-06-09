package com.example.addressbook;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{
    AlertDialog dialog ;//退出对话框
    private Button btn_enter,btn_cancel;//确认和取消按钮
    private EditText edit_name1,edit_password;//姓名和密码框
    Boolean aBoolean= false;//判断是否选中多选复选框
    CheckBox check1;//多选框

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       btn_enter =(Button)findViewById(R.id.btn_enter);
       btn_cancel=(Button) findViewById(R.id.btn_cancel);
       edit_name1=(EditText)findViewById(R.id.edit_name1);
       edit_password=(EditText)findViewById(R.id.edit_password);
       check1 = (CheckBox) findViewById(R.id.check1);//监听
       check1.setOnCheckedChangeListener(this);

    }
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if(b)//如果选中多选框将aBoolean=true;
        {
            aBoolean=true;
        }
    }

    public void click_enter(View view) {//登入按钮

        Intent intent=new Intent();//用于界面跳转
        intent.setClass(MainActivity.this,MainActivity2.class);//从MainActivity跳转到MainActivity2
        String name=edit_name1.getText().toString();//获取用户输入的姓名
        String password=edit_password.getText().toString();//获取用户输入的密码
        SharedPreferences sp=getSharedPreferences("addressBook",MODE_PRIVATE);//将用户输入数据用SharedPreferences保存

        if(aBoolean){//如果勾选单选框则进行数据保存
            SharedPreferences.Editor editor=sp.edit();
            editor.putString("name",name);
            editor.putString("password",password);
           editor.commit();
        }
        startActivity(intent);//跳转页面
    }

    public void click_cancel(View view) {//退出按钮

        AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("退出对话框");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("是否退出登入：");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
                MainActivity.this.finish();
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


}