package com.toy.chanygram.utils;

public class ScriptWriter {

    public static String alertWithBack(String msg) {
        StringBuffer sb = new StringBuffer();
        sb.append("<script>");
        sb.append("alert('" + msg + "');");
        sb.append("history.back();");
        sb.append("</script>");

        return sb.toString();
    }
}
