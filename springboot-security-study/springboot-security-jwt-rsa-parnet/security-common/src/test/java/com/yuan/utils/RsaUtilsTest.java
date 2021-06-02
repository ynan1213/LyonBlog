package com.epichust.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RsaUtilsTest
{
    private static String privateFilePath = "E:\\GitProjects\\Java-Study\\springboot-security-study\\auth_key\\id_key_rsa";
    private static String publicFilePath = "E:\\GitProjects\\Java-Study\\springboot-security-study\\auth_key\\id_key_rsa.pub";

    public static void main(String[] args) throws Exception
    {
        RsaUtils.generateKey(publicFilePath, privateFilePath, "itheima", 2048);
    }

    @Test
    public void generateKey() throws Exception
    {
        RsaUtils.generateKey(publicFilePath, privateFilePath, "itheima", 2048);
    }

    @Test
    public void getPublicKey() throws Exception
    {
        System.out.println(RsaUtils.getPublicKey(publicFilePath));
    }

    @Test
    public void getPrivateKey() throws Exception
    {
        System.out.println(RsaUtils.getPrivateKey(privateFilePath));
    }
}