package com.yy.server.mina;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

/**
 * Created by yy on 17-6-15.
 * 编码类
 */
public class MyTextLineEncoder implements ProtocolEncoder {
    @Override
    public void encode(IoSession ioSession, Object o, ProtocolEncoderOutput protocolEncoderOutput) throws Exception {
        String s = null;
        if (o instanceof String) {
            s = (String) o;
        }
        if(s != null){
            CharsetEncoder charsetEncoder = (CharsetEncoder) ioSession.getAttribute("encoder");
            if(charsetEncoder == null){ //第一次获取，避免重复new
                charsetEncoder = Charset.defaultCharset().newEncoder();  //调用系统默认编码格式
                ioSession.setAttribute("encoder",charsetEncoder);
            }
            IoBuffer ioBuffer = IoBuffer.allocate(s.length());//开辟内存
            ioBuffer.setAutoExpand(true);//自动扩展内存大小
            ioBuffer.putString(s,charsetEncoder);
            ioBuffer.flip();
            protocolEncoderOutput.write(ioBuffer);
        }
    }

    @Override
    public void dispose(IoSession ioSession) throws Exception {

    }
}
