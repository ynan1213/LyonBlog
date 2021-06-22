package com.epichust.main7.拆包;

public class MessageProtocol
{
    private int len;
    private byte[] content;

    public byte[] getContent()
    {
        return content;
    }

    public void setContent(byte[] content)
    {
        this.content = content;
    }

    public int getLen()
    {
        return len;
    }

    public void setLen(int len)
    {
        this.len = len;
    }
}
