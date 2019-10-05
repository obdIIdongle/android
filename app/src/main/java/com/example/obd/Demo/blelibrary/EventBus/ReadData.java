package com.example.obd.Demo.blelibrary.EventBus;

public class ReadData {
    byte[] reback=new byte[0];
    public ReadData(byte[] reback){
        this.reback=reback;
    }

    public byte[] getReback() {
        return reback;
    }
}
