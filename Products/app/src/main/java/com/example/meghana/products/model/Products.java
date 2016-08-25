package com.example.meghana.products.model;

/**
 * Created by meghana on 10/8/16.
 */
public class Products {


    private String pid;
    private String pname;
    private String pcost;

    public Products(String pid, String pname, String pcost) {
        this.pid = pid;
        this.pname = pname;
        this.pcost = pcost;
    }



    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPcost() {
        return pcost;
    }

    public void setPcost(String pcost) {
        this.pcost = pcost;
    }






}
