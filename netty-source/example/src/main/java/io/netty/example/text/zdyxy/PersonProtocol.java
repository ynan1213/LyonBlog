package io.netty.example.text.zdyxy;

/**
 * @Author: Changwu
 * @Date: 2019/7/21 20:44
 */

public class PersonProtocol {
    private int length;
    private byte[] content;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
