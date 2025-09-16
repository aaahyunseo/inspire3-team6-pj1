package com.lgcns.inspire3_blog.util;

import java.io.IOException;
import java.net.ServerSocket;

public class PortUtils {

    public static int findAvailableTcpPort() {
        try (ServerSocket socket = new ServerSocket(0)) {
            socket.setReuseAddress(true);
            return socket.getLocalPort(); // 사용 가능한 포트 반환
        } catch (IOException e) {
            throw new IllegalStateException("No available TCP port", e);
        }
    }
}