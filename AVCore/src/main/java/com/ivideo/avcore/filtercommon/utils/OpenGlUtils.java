package com.ivideo.avcore.filtercommon.utils;

import android.content.res.Resources;

import org.jash.common.BaseApplication;

import java.io.InputStream;

/**
 * @author hahajing 企鹅：444511958
 * @version 1.0.0
 * @createDate 2022/6/16 15:08
 * @description
 * @updateUser hahajing
 * @updateDate 2022/6/16 15:08
 * @updateRemark
 */
public class OpenGlUtils {
    //通过资源路径加载shader脚本文件
    public static String uRes(String path) {
        Resources resources = BaseApplication.Companion.getAppContext().getResources();
        StringBuilder result = new StringBuilder();
        try {
            InputStream is = resources.getAssets().open(path);
            int ch;
            byte[] buffer = new byte[1024];
            while (-1 != (ch = is.read(buffer))) {
                result.append(new String(buffer, 0, ch));
            }
        } catch (Exception e) {
            return null;
        }
        return result.toString().replaceAll("\\r\\n", "\n");
    }
}
