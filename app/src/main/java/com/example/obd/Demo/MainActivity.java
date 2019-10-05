package com.example.obd.Demo;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;
import com.example.obd.Demo.Adapter.MsAdapter;
import com.example.obd.Demo.blelibrary.EventBus.ConnectState;
import com.example.obd.Demo.blelibrary.EventBus.Messages;
import com.example.obd.Demo.blelibrary.EventBus.ReadData;
import com.example.obd.Demo.blelibrary.EventBus.RebackData;
import com.example.obd.Demo.blelibrary.EventBus.SerchDevice;
import com.example.obd.Demo.blelibrary.EventBus.WriteData;
import com.example.obd.Demo.blelibrary.Server.BleServiceControl;
import com.example.obd.Demo.blelibrary.Server.ScanDevice;
import com.example.obd.Demo.blelibrary.tool.FormatConvert;
import com.orange.obd.R;
import com.orange_electronic.languagesetting.LanguageUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.ArrayList;

import static com.example.obd.Demo.Command.getDateTime;
import static com.example.obd.Demo.Command.setTireId;

public class MainActivity extends AppCompatActivity {
    public static ScanDevice scan=new ScanDevice();
    public static ArrayList<String> message=new ArrayList<>();
    public static MsAdapter adapter=new MsAdapter(message);
    EditText edit_id1,edit_id2,edit_id3,edit_id4,edit_id5;
    public static Activity activity;
    Button write_btn;
    public static RecyclerView re;
    Toolbar toolbar;
    public static String blename="";
    public static BleServiceControl bleServiceControl=new BleServiceControl();
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scan.setmBluetoothAdapter(this);
        activity=this;
        toolbar=findViewById(R.id.toolbar2);
        edit_id1=findViewById(R.id.edit_id1);
        edit_id2=findViewById(R.id.edit_id2);
        edit_id3=findViewById(R.id.edit_id3);
        edit_id4=findViewById(R.id.edit_id4);
        edit_id5=findViewById(R.id.edit_id);
        write_btn=findViewById(R.id.write_btn);
        setActionBar(toolbar);
        setTitle("");
        re=findViewById(R.id.re);
        re.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true));
        re.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        setui(false);
        LanguageUtil.updateLocale(this, LanguageUtil.LOCALE_CHINESE);
    }
    public void setui(boolean enable){
        edit_id1.setEnabled(enable);edit_id2.setEnabled(enable);edit_id3.setEnabled(enable);edit_id4.setEnabled(enable);write_btn.setEnabled(enable);
        edit_id5.setEnabled(enable);
    }
        @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if(grantResults[0]!=0){
                Toast.makeText(this, "必須打開定位才能啟用此服務", Toast.LENGTH_LONG).show();
            }
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(SerchDevice a){
//        try{
//            if(a.getDevic().getAddress().equals(blename)){
//                Toast.makeText(this, "找到新裝置"+a.getDevic().getName(), Toast.LENGTH_LONG).show();
//                scan.scanLeDevice(false);
//                bleServiceControl.connect(a.getDevic().getAddress(),this);
//                message.add("找到新裝置"+a.getDevic().getName()+","+getDateTime());
//                adapter.notifyDataSetChanged();
//            }
//        }catch (Exception e){Log.w("error",e.getMessage());}
    }
    //----------------------------------------Connect state----------------------------------------
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(ConnectState a){
if(a.getReback()){   message.add(0,"Connect::"+blename+","+getDateTime());}else{   message.add("Disconnect::"+blename+","+getDateTime());}
if(a.getReback()){setui(true);}else{setui(false);}
        adapter.notifyDataSetChanged();
re.scrollToPosition(0);
    }
//----------------------------------------Use bleServiceControl.WriteCmd(HexString,uuid).to write Data ----------------------------------------
@Subscribe(threadMode = ThreadMode.MAIN)
  public void Event(RebackData a){
      try{
          Log.w("WriteReback","Data:"+ FormatConvert.bytesToHex(a.getReback())+","+getDateTime());
          message.add(0,"RX:"+FormatConvert.bytesToHex(a.getReback())+","+getDateTime());
          Command.RXDATA=a.getReback();
          adapter.notifyDataSetChanged();
          re.scrollToPosition(0);
      }catch (Exception e){Log.w("error",e.getMessage());}
  }
  //----------------------------------------Use bleServiceControl.ReadCmd(uuid).to read Data----------------------------------------
  @Subscribe(threadMode = ThreadMode.MAIN)
  public void Event(ReadData a){
      try{
          Log.w("ReadReback","Data:"+ FormatConvert.bytesToHex(a.getReback())+","+getDateTime());
          message.add(0,"RX:"+FormatConvert.bytesToHex(a.getReback())+","+getDateTime());
          adapter.notifyDataSetChanged();
          re.scrollToPosition(0);
      }catch (Exception e){Log.w("error",e.getMessage());}
  }
  //----------------------------------------Use bleServiceControl.WriteCmd will callbcak this method to confirm is writed susscessfully----------------------------------------
  @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(WriteData a){
        try{
            Log.w("Write","Data:"+ FormatConvert.bytesToHex(a.data()));
            message.add(0,"TX:"+FormatConvert.bytesToHex(a.data())+","+getDateTime());
            adapter.notifyDataSetChanged();
            re.scrollToPosition(0);
        }catch (Exception e){Log.w("error",e.getMessage());}
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(Messages a){
        try{
            message.add(0,a.a());
            adapter.notifyDataSetChanged();
            re.scrollToPosition(0);
        }catch (Exception e){Log.w("error",e.getMessage());}
    }
public void onClick(View view){
switch (view.getId()){
    case R.id.write_btn:
        ArrayList<String> write=new ArrayList<>();
        if(edit_id1.getText().length()<6||edit_id1.getText().length()>8){ return;}
        if(edit_id2.getText().length()<6||edit_id2.getText().length()>8){return;}
         if(edit_id3.getText().length()<6||edit_id3.getText().length()>8){return;}
        if(edit_id4.getText().length()<6||edit_id4.getText().length()>8){return;}

        write.add(edit_id1.getText().toString());
        write.add(edit_id2.getText().toString());
        write.add(edit_id3.getText().toString());
        write.add(edit_id4.getText().toString());
        if(edit_id5.getText().length()>=6&&edit_id5.getText().length()<=8){write.add(edit_id5.getText().toString());}

        setTireId(write);
        break;
    case R.id.select_ble:
        scan.scanLeDevice(true);
        Intent intent=new Intent(this, ScanBle.class);
        startActivity(intent);
        break;
    case R.id.select_mmy:
      
        break;
    case R.id.action:
        Intent in=new Intent(this, SelectAction.class);
        startActivity(in);
        break;
} }}
