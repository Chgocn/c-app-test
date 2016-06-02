package io.chgocn.plug.network;

import java.io.Serializable;

/**
 * Response Json
 *
 * @author chgocn
 */
public class ResponseJson implements Serializable {

    private int ret;
    private String data;
    private String msg;
    private ResponsePage pages;

    public ResponseJson() {
    }

    public ResponseJson(int ret, String data, String msg, ResponsePage pages) {
        this.ret = ret;
        this.data = data;
        this.msg = msg;
        this.pages = pages;
    }

    public boolean isSuccessful() {
        return ret >= 200 && ret < 300;
    }

    public boolean invalidToken(){
        return ret == 401;
    }

    public boolean multiLoginTip(){
        return ret == 402;
    }

    public boolean invalidRequest(){
        return ret == 403;
    }

    public boolean invalidAccount(){
        return ret == 404;
    }

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResponsePage getPages() {
        return pages;
    }

    public void setPages(ResponsePage pages) {
        this.pages = pages;
    }

    @Override
    public String toString() {
        return "ResponseJson{" +
                "ret=" + ret +
                ", data='" + data + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
