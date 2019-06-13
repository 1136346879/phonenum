package com.hexun.base.phonenum;

import android.text.TextUtils;

/**
 * 判断是否是手机号
 */

public class MobileUtil {
    public static boolean isMobileNO(String phone) {
       /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188、198
    联通：130、131、132、152、155、156、166、185、186
    电信：133、153、180、189、199（1349卫通）
    虚拟：170、171
    总结起来就是第一位必定为1，第二位必定为3356789，其他位置的可以为0-9
    */
        //String telRegex = "[1][356789]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、6、8、9中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        //String telRegex = "^1((3[0-9]|4[57]|5[0-35-9]|6[6]|7[014678]|8[0-9]|9[89])\\d{8}$)";
        String telRegex = "^1[3,5-9]\\d{9}$";
        if (TextUtils.isEmpty(phone)) return false;
        else return phone.matches(telRegex);
    }
}
