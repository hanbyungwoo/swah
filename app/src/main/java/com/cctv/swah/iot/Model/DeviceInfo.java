package com.cctv.swah.iot.Model;

import java.io.Serializable;

/**
 * Created by byungwoo on 2016-07-26.
 */
public class DeviceInfo implements Serializable {

    private static final long serialVersionUID = 4220461820168818967L;

    private int device;
    private String id;
    private String model;
    private String name;
    private String pw;

    public DeviceInfo(int device, String id, String model, String name, String pw) {
        this.device = device;
        this.id = id;
        this.model = model;
        this.name = name;
        this.pw = pw;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getDevice() {
        return device;
    }

    public String getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public String getName() {
        return name;
    }

    public String getPw() {
        return pw;
    }
}
