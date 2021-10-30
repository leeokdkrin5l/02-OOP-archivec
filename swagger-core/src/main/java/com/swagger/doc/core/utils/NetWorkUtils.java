package com.swagger.doc.core.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * Created by IntelliJ IDEA.
 *
 * @author wk
 * Date: 2019-08-05 14:44
 */
public class NetWorkUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(NetWorkUtils.class);

    public static String getNetWorkIp() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            NetworkInterface networkInterface;
            Enumeration<InetAddress> inetAddresses;
            InetAddress inetAddress;
            String ip;
            while (networkInterfaces.hasMoreElements()) {
                networkInterface = networkInterfaces.nextElement();
                inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    inetAddress = inetAddresses.nextElement();
                    if (inetAddress != null && inetAddress instanceof Inet4Address && StringUtils.equals("127.0.0.1", inetAddress.getHostAddress())) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return null;
    }
}
