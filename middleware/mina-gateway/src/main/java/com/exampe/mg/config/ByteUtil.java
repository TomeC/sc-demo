package com.exampe.mg.config;

import lombok.NonNull;

import java.io.UnsupportedEncodingException;

/**
 * 协议帧的byte数据转换工具
 * Created by yinbo on 2017/7/20.
 */

public class ByteUtil {

    /**
     * 将byte转化为无符号的int
     */
    public static int byteToInt(byte input) {
        return input & 0xff;
    }

    /**
     * 将short转化为int
     */
    public static int shortToInt(short input) {
        return input & 0xffff;
    }

    /**将int转化为long*/
    public static long intToLong(int input) {
        return input & 0xffffffffL;
    }

    /**
     * 将byte数组转化为int，如果input为空，则会抛出非法参数异常
     * @param input byte数组
     * @return int值
     */
    public static int bytesToInt(@NonNull byte[] input){
        if(input.length == 0) {
            throw new IllegalArgumentException("input can't be empty");
        }
        input = inverseArray(input);
        int intVar = 0;
        int length = input.length;
        if(length > 4) {
            length = 4;
        }
        for (int i = 0; i < length; i++) {
            intVar |= ((input[i] << i * 8) & getIndexMaxByte(i));
        }
        return intVar;
    }

//    /**
//     * 将byte数组转化为long，如果input为空，则会抛出非法参数异常
//     * @param input byte数组
//     * @return long值
//     */
//    public static long bytesToLong(@NonNull byte[] input){
//        if(input.length == 0) {
//            throw new IllegalArgumentException("input can't be empty");
//        }
//        input = inverseArray(input);
//        long intVar = 0;
//        int length = input.length;
//        if(length > 8) {
//            length = 8;
//        }
//        for (int i = 0; i < length; i++) {
//            intVar |= ((input[i] << i * 8) & getIndexMaxByte(i));
//        }
//        return intVar;
//    }

    public static byte[] longToBytes(long num) {
        byte[] byteNum = new byte[8];
        for (int ix = 0; ix < 8; ++ix) {
            int offset = 64 - (ix + 1) * 8;
            byteNum[ix] = (byte) ((num >> offset) & 0xff);
        }
        return byteNum;
    }

    public static long bytesToLong(byte[] buffer) {
        long  values = 0;
        for (int i = 0; i < 8; i++) {
            values <<= 8; values|= (buffer[i] & 0xff);
        }
        return values;
    }

    public static byte[] BCDToByte(String str)
    {
        //数位为奇数时前面补0
        if (str.length() %2 == 1) {
            str = "0" + str;
        }
        byte[] aryTemp = new byte[str.length() / 2];
        for (int i = 0; i < (str.length() / 2); i++)
        {
            aryTemp[i] = (byte)(((str.charAt(i * 2) - '0') << 4) | (str.charAt(i * 2 + 1) - '0'));
        }
        return aryTemp;//高位在前
    }


    private static long getIndexMaxByte(int i) {
        long max = 0;
        switch (i) {
            case 0:
                max = 0xFF;
                break;
            case 1:
                max = 0xFF00;
                break;
            case 2:
                max = 0xFF0000;
                break;
            case 3:
                max = 0xFF000000;
                break;
            case 4:
                max = 0xFF00000000L;
                break;
            case 5:
                max = 0xFF0000000000L;
                break;
            case 6:
                max = 0xFF000000000000L;
                break;
            case 7:
                max = 0xFF00000000000000L;
                break;
        }
        return max;
    }

    /**
     * 将long转为byte数组，指定数组长度，当int值大于数组长度能提供的精度时，此转化方法将丢失精度，<br>
     * 请谨慎指定数组的长度。
     * @param input long值
     * @param length 数组长度，不超过8
     * @return 转化的数组
     */
    private static byte[] inputToBytes(long input, int length) {
        if(length > 8) {
            length = 8;
        }
        byte[] dataArr = new byte[length];
        for (int i = 0; i < length; i++) {
            dataArr[i] = (byte)((input & getIndexMaxByte(i)) >>> i * 8);
        }
        return inverseArray(dataArr);
    }

    /**
     * 将int转为长度为4的byte数组。
     */
    @NonNull
    public static byte[] intToBytes(int input) {
        return inputToBytes(intToLong(input), 4);
    }

//    /**
//     * 将long转为长度为8的byte数组
//     */
//    @NonNull
//    public static byte[] longToBytes(long input) {
//        return inputToBytes(input, 8);
//    }

    /**
     * 将short转为长度为2的byte数组
     */
    @NonNull
    public static byte[] shortToBytes(short input) {
        return inputToBytes(shortToInt(input), 2);
    }

    /**
     * 以字节为单位反转数组顺序，小端模式转大端模式，或者大端模式转小端模式
     * @param input byte数组 如果input为null，则会报空指针异常
     * @return 反转的数组
     */
    @NonNull
    public static byte[] inverseArray(@NonNull byte[] input) {
        if(input.length == 0) {
            return input;
        }
        int length = input.length;
        byte[] dataArr = new byte[length];
        for (int i = 0; i < length; i++) {
            dataArr[i] = input[length - 1 - i];
        }
        return dataArr;
    }

    /**
     * 将byte数组转化为16进制字符串
     * @param input byte数组
     */
    @NonNull
    public static String bytesToHexString(@NonNull byte[] input) {
        StringBuilder sb = new StringBuilder();
        for (byte aByte : input) {
            String item = Integer.toHexString(byteToInt(aByte));
            if (item.length() < 2) {
                //补0
                sb.append("0");
            }
            sb.append(item);
        }
        return sb.toString();
    }

    /**
     * 将byte数组转化为10进制字符串，如果byte值小于10，则前面补0，保证1byte最少2位，
     * 但是可能出现3位的情况，需要使用者自己处理
     */
    @NonNull
    public static String bytesToDecString(@NonNull byte[] input) {
        StringBuilder sb = new StringBuilder();
        for (byte aByte : input) {
            String item = String.valueOf(byteToInt(aByte));
            if (item.length() < 2) {
                //补0
                sb.append("0");
            }
            sb.append(item);
        }
        return sb.toString();
    }

    public static String bytesToString(@NonNull byte[] input) throws UnsupportedEncodingException {
        String ret = new String(input, "utf-8");
        return ret;
    }


    /**
     * 将16进制字符串转化为byte数组
     * @param input 16进制字符串，每2位代表一个字节
     * @return byte数组
     * @throws NumberFormatException 如果输入不是16进制的字符串，则会报转换异常
     */
    @NonNull
    public static byte[] hexStringToBytes(@NonNull String input) {
        input = input.trim();
        byte[] bytes = new byte[input.length() / 2];
        int lastIndex = 0;
        for (int i = 0; i < input.length(); i++) {
            if ((i + 1) % 2 == 0) {
                String subString = input.substring(lastIndex, i + 1);
                int var = Integer.parseInt(subString, 16);
                bytes[(i - 1) / 2] = (byte) var;
            }
            lastIndex = i;
        }
        return bytes;
    }


    /**
     * 将10进制字符串转化为byte数组
     * @param input 10进制字符串，每2位代表一个字节
     * @return byte数组
     * @throws NumberFormatException 如果输入不是数字，则会报转换异常
     */
    @NonNull
    public static byte[] decStringToBytes(@NonNull String input) {
        input = input.trim();
        byte[] bytes = new byte[input.length() / 2];
        int lastIndex = 0;
        for (int i = 0; i < input.length(); i++) {
            if ((i + 1) % 2 == 0) {
                String subString = input.substring(lastIndex, i + 1);
                int var = Integer.parseInt(subString);
                bytes[(i - 1) / 2] = (byte) var;
            }
            lastIndex = i;
        }
        return bytes;
    }

    /**
     * 截取byte数组
     */
    public static byte[] subBytes(byte[] src, int begin, int length) {
        byte[] bs = new byte[length];
        System.arraycopy(src, begin, bs, 0, length);
        return bs;
    }

    /**
     * 截取byte数据
     */
    public static byte[] subBytes(byte[] src, int begin) {
        int length = src.length - begin;
        return subBytes(src, begin, length);
    }

    /**
     * byte数组求和
     */
    public static long sumOfBytes(byte[] datas)
    {
        long sum = 0;
        for (byte v : datas)
        {
            sum = sum + v;
        }
        return sum;
    }
}