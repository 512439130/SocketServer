package com.yy.server.mina;

import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Created by yy on 17-6-14.
 * Mina服务端
 */
public class MinaServer {

    //mina：分离网络管理和消息处理

    public static void main(String[] args){
        try {
            //基础用法
            //1
            NioSocketAcceptor acceptor = new NioSocketAcceptor();
            //2
            acceptor.setHandler(new MyServerHanader());
            //3(拦截器过滤（二进制数据和对象的转换）)
            acceptor.getFilterChain().addLast("codec_server",new ProtocolCodecFilter(new MyTextLineFactory()));

            //4设置空闲状态位(5秒进入空闲状态)
           // acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE,5);  //空闲状态：客户端和服务器多久没有读取和发送数据，单位：秒

            //5(服务器启动，监听9898端口)
            acceptor.bind(new InetSocketAddress(9898));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
