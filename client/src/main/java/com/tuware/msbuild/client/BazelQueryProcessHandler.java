package com.tuware.msbuild.client;

import com.zaxxer.nuprocess.NuAbstractProcessHandler;

import java.nio.ByteBuffer;

public class BazelQueryProcessHandler extends NuAbstractProcessHandler {

    @Override
    public void onStdout(ByteBuffer buffer, boolean closed) {
        if (!closed) {
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
            System.out.print(new String(bytes));
        }
    }

}
