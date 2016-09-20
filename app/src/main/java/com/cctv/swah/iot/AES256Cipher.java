package com.cctv.swah.iot;

import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by byungwoo on 2016-08-02.
 */
public class AES256Cipher {
    public static byte[] ivBytes = new byte[16];

    public AES256Cipher() {
    }

    public static String AES_Encode(String str, String key) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        byte[] textBytes = str.getBytes("UTF-8");
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        SecretKeySpec newKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
        Cipher cipher = null;
        cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(1, newKey, ivSpec);
        Log.e("---", textBytes+"");
        return Base64.encodeToString(cipher.doFinal(textBytes), 0);
    }

    public static String AES_Decode(String str, String key) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        byte[] textBytes = Base64.decode(str, 0);
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        SecretKeySpec newKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
//        cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
        cipher.init(2, newKey, ivSpec);
        return new String(cipher.doFinal(textBytes), "UTF-8");
    }
}
