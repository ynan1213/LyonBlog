package com.epichust.config;

import com.epichust.utils.RsaUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.security.PrivateKey;
import java.security.PublicKey;

@Configuration
@ConfigurationProperties(prefix = "rsa.key")
public class RsaKeyProperties
{
    private String pubKeyFile;
    private String priKeyFile;

    private PublicKey publicKey;
    private PrivateKey privateKey;

    @PostConstruct
    public void createRsaKey() throws Exception
    {
        publicKey = RsaUtils.getPublicKey(pubKeyFile);
        privateKey = RsaUtils.getPrivateKey(priKeyFile);
    }

    public String getPubKeyFile()
    {
        return pubKeyFile;
    }

    public void setPubKeyFile(String pubKeyFile)
    {
        this.pubKeyFile = pubKeyFile;
    }

    public String getPriKeyFile()
    {
        return priKeyFile;
    }

    public void setPriKeyFile(String priKeyFile)
    {
        this.priKeyFile = priKeyFile;
    }

    public PublicKey getPublicKey()
    {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey)
    {
        this.publicKey = publicKey;
    }

    public PrivateKey getPrivateKey()
    {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey)
    {
        this.privateKey = privateKey;
    }
}