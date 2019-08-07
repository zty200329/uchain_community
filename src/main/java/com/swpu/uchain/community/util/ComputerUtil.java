package com.swpu.uchain.community.util;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;

/**
 * @ClassName ComputerUtil
 * @Description TODO
 * @Author LZH
 */
public class ComputerUtil {

    /**
     * 通过ip获取本地mac地址
     * @param ia
     * @return
     * @throws SocketException
     */
    private static String getLocalMac(InetAddress ia) throws SocketException {
        //获取网卡，获取地址
        byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
        System.out.println("mac数组长度："+mac.length);
        StringBuffer sb = new StringBuffer("");
        for(int i=0; i<mac.length; i++) {
            if(i!=0) {
                sb.append("-");
            }
            //字节转换为整数
            int temp = mac[i]&0xff;
            String str = Integer.toHexString(temp);
            if(str.length()==1) {
                sb.append("0"+str);
            }else {
                sb.append(str);
            }
        }
        String macString = sb.toString().toUpperCase();
        return macString;
    }

    /**
     * 得到当前电脑桌面路径
     * @return
     */
    public static String getDesktopPath(){
        File desktopDir = FileSystemView.getFileSystemView().getHomeDirectory();
        String desktop = desktopDir.getAbsolutePath();
        return desktop;
    }

    /**
     * 获取本地mac地址
     * @return
     */
    public static String getMacAddressByIp(){
        String macAddress = "";
        try {
            InetAddress ia = InetAddress.getLocalHost();
            System.out.println(ia);
            macAddress = getLocalMac(ia);
        }catch (Exception e){
            e.printStackTrace();
        }
        return macAddress;
    }

    

}
