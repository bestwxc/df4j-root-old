package net.df.base.utils;

import net.df.base.exception.DfException;
import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.PublicKey;

/*
    RSA加解密工具类
 */
public class RsaUtils {
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private Cipher publicKeyEncodeCipher;
    private Cipher publicKeyDecodeCipher;
    private Cipher privateKeyEncodeCipher;
    private Cipher privateKeyDecodeCipher;
    private int MAX_ENCODE_BYTE = 0;
    private int MAX_DECODE_BYTE = 0;

    public RsaUtils(){

    }

    public RsaUtils(int length){
        this.setKeyByte(length);
    }

    public RsaUtils(PublicKey publicKey, int length){
        this.setKeyByte(length);
        //int keyByte = new X509EncodedKeySpec(publicKey.getEncoded()).
        this.publicKey = publicKey;
    }

    public RsaUtils(PrivateKey privateKey, int length){
        this.setKeyByte(length);
        this.privateKey = privateKey;
    }

    public RsaUtils(PublicKey publicKey, PrivateKey privateKey, int length){
        this.setKeyByte(length);
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public void setKeyByte(int length){
        //int keyByte =  8 * (int) Math.round(key.getEncoded().length/8.0);

        //int keyByte = (int) Math.pow(2, Math.round(Math.log(key.getEncoded().length)/(Math.log(10.0))));
        //int keyByte = Integer.parseInt("");
        this.MAX_DECODE_BYTE = length/8;
        this.MAX_ENCODE_BYTE = length/8 - 11;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public void initPublicKeyEncode(){
        try {
            //this.setKeyByte(publicKey);
            publicKeyEncodeCipher = Cipher.getInstance("RSA");
            publicKeyEncodeCipher.init(Cipher.ENCRYPT_MODE, publicKey);
        } catch (Exception e) {
            publicKeyEncodeCipher = null;
            throw new DfException("RSA初始化错误",e);
        }
    }

    public void initPublicKeyDecode(){
        try {
            //this.setKeyByte(publicKey);
            publicKeyDecodeCipher = Cipher.getInstance("RSA");
            publicKeyDecodeCipher.init(Cipher.DECRYPT_MODE, publicKey);
        } catch (Exception e) {
            publicKeyDecodeCipher = null;
            throw new DfException("RSA初始化错误",e);
        }
    }

    public void initPrivateKeyEncode(){
        try {
            //this.setKeyByte(privateKey);
            privateKeyEncodeCipher = Cipher.getInstance("RSA");
            privateKeyEncodeCipher.init(Cipher.ENCRYPT_MODE, privateKey);
        } catch (Exception e) {
            privateKeyEncodeCipher = null;
            throw new DfException("RSA初始化错误",e);
        }
    }

    public void initPrivateKeyDecode(){
        try {
            //this.setKeyByte(privateKey);
            privateKeyDecodeCipher = Cipher.getInstance("RSA");
            privateKeyDecodeCipher.init(Cipher.DECRYPT_MODE, privateKey);
        } catch (Exception e) {
            privateKeyDecodeCipher = null;
            throw new DfException("RSA初始化错误",e);
        }
    }

    private byte[] convert(byte[] data,Cipher cipher,int max_convert_byte) throws GeneralSecurityException{
        if(cipher != null){
            try {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                int dataLength = data.length;
                int offset = 0;
                int convert_byte = 0;
                byte[] cache;
                while((convert_byte = dataLength - offset) > 0){
                    convert_byte = Math.min(convert_byte, max_convert_byte);
                    cache = cipher.doFinal(data, offset, convert_byte);
                    out.write(cache);
                    offset += convert_byte;
                }
                return out.toByteArray();
            } catch (Exception e) {
                throw new DfException("加/解密错误",e);
            }
        }else{
            throw new DfException("请先初始化cipher");
        }
    }

    public byte[] publicKeyEncode(byte[] data) throws GeneralSecurityException{
        return this.convert(data, publicKeyEncodeCipher, MAX_ENCODE_BYTE);
    }

    public byte[] publicKeyDecode(byte[] data) throws GeneralSecurityException{
        return this.convert(data, publicKeyDecodeCipher, MAX_DECODE_BYTE);
    }

    public byte[] privateKeyEncode(byte[] data) throws GeneralSecurityException{
        return this.convert(data, privateKeyEncodeCipher, MAX_ENCODE_BYTE);
    }

    public byte[] privateKeyDecode(byte[] data) throws GeneralSecurityException{
        return this.convert(data, privateKeyDecodeCipher, MAX_DECODE_BYTE);
    }
}
