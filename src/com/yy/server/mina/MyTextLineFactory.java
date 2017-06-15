package com.yy.server.mina;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * Created by yy on 17-6-15.
 */
public class MyTextLineFactory implements ProtocolCodecFactory {
    private MyTextLineEncoder mEncoder;
    private MyTextLineDecoder mDecoder;
    private MyTextLineCumulativeDecoder mCumulativeDecoder;

    public MyTextLineFactory(){
        mEncoder = new MyTextLineEncoder();
        mDecoder = new MyTextLineDecoder();
        mCumulativeDecoder = new MyTextLineCumulativeDecoder();
    }
    //加密
    @Override
    public ProtocolEncoder getEncoder(IoSession ioSession) throws Exception {
        return mEncoder;
    }
    //解密
    @Override
    public ProtocolDecoder getDecoder(IoSession ioSession) throws Exception {
        return mCumulativeDecoder;
    }
}
