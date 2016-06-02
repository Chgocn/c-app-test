package io.chgocn.plug.network;

import java.io.Serializable;

/**
 * Response Json
 *
 * @author chgocn
 */
public class MobResponseJson implements Serializable {

    private String retCode;
    private String result;
    private String msg;
    private ResponsePage pages;

    public MobResponseJson() {
    }

    public MobResponseJson(String retCode, String result, String msg, ResponsePage pages) {
        this.retCode = retCode;
        this.result = result;
        this.msg = msg;
        this.pages = pages;
    }

    public boolean isSuccessful() {
        return Integer.valueOf(retCode) >= 200 && Integer.valueOf(retCode) < 300;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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
                "retCode=" + retCode +
                ", result='" + result + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
