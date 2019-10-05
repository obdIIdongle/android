package com.example.obd.Demo;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.example.obd.Demo.blelibrary.EventBus.Messages;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.example.obd.Demo.MainActivity.bleServiceControl;
import static com.example.obd.Demo.blelibrary.tool.FormatConvert.StringHexToByte;
public  class Command {
    public static String RX="nodata";
    public  static byte[] RXDATA=new byte[0];
    public static String WRITE_SUCCESS="F502000300F40A";
    public static String Program_Flash_Fail="F502000302F60A";
    public static String VERIFY_FAIL="F502000303F70A";
    //自動設定checkbyte
    public static String addcheckbyte(String com){
        byte a[]=StringHexToByte(com);
        byte checkbyte= a[0];
        for(int i=1;i<a.length-2;i++){
            checkbyte ^= a[i];
        }
        a[a.length-2]=checkbyte;
        return bytesToHex(a);
    }
    //握手
    public static void HandShake(){
        String a="0A0000030000F5";
        bleServiceControl.WriteCmd(addcheckbyte(a),7);
    }
    //Reboot
    public static void Reboot(){
        String a="0A0D00030000F5";
        bleServiceControl.WriteCmd(addcheckbyte(a),7);
    }
   private static Handler handler=new Handler() ;
// 燒寫&amp;驗證Flash
    public static void WriteFlash(final Context context,final String FileName,final int Ind){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    InputStreamReader fr = new InputStreamReader(context.getResources().getAssets().open(FileName));
                    BufferedReader br = new BufferedReader(fr);
                    StringBuilder sb = new StringBuilder();
                    while (br.ready()) {
                        String s=br.readLine();
                        sb.append(s);
                    }
                    int Long=0;
                    if(sb.length()%Ind == 0){Long=sb.length()/Ind;
                    }else{Long=sb.length()/Ind+1;}
                    for(int i=0;i<Long;i++){
int b=i;
if(b>=255){b=b-255;}
                        StringBuffer result = new StringBuffer(Integer.toHexString(b));
                        while (result.length() < 2) {
                            result.insert(0, "0");
                        }
                        String cont=result.toString().toUpperCase();
                        if(i==Long-1){
                            String data=bytesToHex(sb.substring(i*Ind, sb.length()).getBytes());
                            int length=sb.substring(i*Ind, sb.length()).getBytes().length+3;
                            if(!check(Convvvert(data,Integer.toHexString(length),cont))){
                                return;
                            }
                        }else{
                            String data=bytesToHex(sb.substring(i*Ind, i*Ind+Ind).getBytes());
                            Log.d("s",data);
                            int length=sb.substring(i*Ind, i*Ind+Ind).getBytes().length+3;
                            if(!check(Convvvert(data,Integer.toHexString(length),cont))){
                                return;
                            }
                        }
                        if(i==Long-1){
                            EventBus.getDefault().post(new Messages("燒錄成功"));
                        }
                    }
                    fr.close();
                }catch(Exception e){e.printStackTrace();}
            }
        }).start();
    }
//設定tireid
public static void setTireId(final ArrayList<String> Id) {
    ArrayList<String> tmpsend=new ArrayList<>();
    tmpsend.add("60A200FFFFFFFFC20A");
    int i=1;
    for(String id:Id){
        id=AddEmpty(id);
        if(id != null){
            tmpsend.add(addcheckbyte("60A20XidFF0A".replace("id",id).replace("X",""+i)));
        }
        i++;
    }
    tmpsend.add("60A2FFFFFFFFFF3D0A");
    bleServiceControl.WriteArray(tmpsend,9);
}
    public static String AddEmpty(String a){
        switch (a.length()){
            case 6:
                return "00"+a;
            case 7:
                return "0"+a;
            case 8:
                return a;
        }
        return null;
    }
    private static String bytesToHex(byte[] hashInBytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : hashInBytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }
    public static String getDateTime(){

        SimpleDateFormat sdFormat = new SimpleDateFormat("HH:mm:ss:SSS");

        Date date = new Date();

        String strDate = sdFormat.format(date);

//System.out.println(strDate);

        return strDate;

    }
    public static String Convvvert(String data,String length,String line){
        String command="0A02LHX00F5";
        switch(length.length()){
            case 1:
                length="000"+length;
                break;
            case 2:
                length="00"+length;
                break;
            case 3:
                length="0"+length;
                break;
            case 4:
                length=length;
                break;
        }
        if(line.equals("F5")){line="00";}
        if(line.length()>2){line="00";}
        command= addcheckbyte(command.replace("L",length).replace("X", data).replace("H",line));
        return command;
    }
    public  static boolean check(String data){
        RXDATA=new byte[0];
        bleServiceControl.WriteCmd(addcheckbyte(data),8);
        try{
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
            Date past=sdf.parse(sdf.format(new Date()));
            int fal=0;
            while(fal<5){
                Date now=sdf.parse(sdf.format(new Date()));
                double time=getDatePoor(now,past);
                if(time>0.5){
                    past=sdf.parse(sdf.format(new Date()));
                    RXDATA=new byte[0];
                    bleServiceControl.WriteCmd(addcheckbyte(data),8);
                    fal++;
                }else{
                    if(RXDATA.length==8){
                        if(RXDATA[1]==(byte) 0x02&&RXDATA[5]==(byte) 0x00){     RXDATA=new byte[0];
                            return true;}else{
                            RXDATA=new byte[0];
                            bleServiceControl.WriteCmd(addcheckbyte(data),8);
                            fal++;
                        }
                    }
                }

            }
            return false;
        }catch (Exception e){e.printStackTrace();return false;}
    }
    public static void uploaderror(){
        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }).start();
    }
    public static double getDatePoor(Date endDate, Date nowDate) {
        long diff = endDate.getTime() - nowDate.getTime();
        long sec = diff/1000;
        return sec;
    }
    public static void OneShot(final Context context,final String FileName){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    InputStreamReader fr = new InputStreamReader(context.getResources().getAssets().open(FileName));
                    BufferedReader br = new BufferedReader(fr);
                    StringBuilder sb = new StringBuilder();
                    while (br.ready()) {
                        String s=br.readLine();
                        sb.append(s);
                    }
                    StringBuffer result = new StringBuffer(Integer.toHexString(0));
                    while (result.length() < 2) {
                        result.insert(0, "0");
                    }
                    String cont=result.toString().toUpperCase();
                            String data=bytesToHex(sb.toString().getBytes());
                            int length=sb.toString().getBytes().length+3;
                            if(!check(Convvvert(data,Integer.toHexString(length),cont))){
                                return;
                            }
                    fr.close();
                }catch(Exception e){e.printStackTrace();}
            }
        }).start();
    }
}
