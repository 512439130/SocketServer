package com.yy.server.mina;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

/**
 * Created by yy on 17-6-16.
 */
public class MyTextLineCumulativeDecoder extends CumulativeProtocolDecoder {
    @Override
    protected boolean doDecode(IoSession ioSession, IoBuffer ioBuffer, ProtocolDecoderOutput protocolDecoderOutput) throws Exception {

        //记录一开始读取的位置
        int startPosition = ioBuffer.position();
        while(ioBuffer.hasRemaining()){
            byte b = ioBuffer.get();
            if(b == '\n'){  //一行读取结束
                int currentPosition = ioBuffer.position();//记录当前位置
                int limit = ioBuffer.limit();  //记录当前总长度
                ioBuffer.position(startPosition);  //起点
                ioBuffer.limit(currentPosition);  //终点
                IoBuffer buf = ioBuffer.slice();  //截取数据

                byte[] dest = new byte[buf.limit()];
                buf.get(dest);  //把IoBuffer的字节赋值到dest数组里
                String str = new String(dest);  //把数据赋值到字符串中
                protocolDecoderOutput.write(str);

                //位置还原
                ioBuffer.position(currentPosition);
                ioBuffer.limit(limit);
                return true;
            }

        }
        //重定向到开始的位置
        ioBuffer.position(startPosition);

        return false;
    }
}
