package com.neu.common.utils;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

/**
 * <p>
 * 封装同RSA非对称加密算法有关的方法，可用于数字签名，RSA加密解密
 * </p>
 *
 * @Copyright:WDSsoft
 */

public class RSATool {

    public RSATool() {
    }

    /**
     * 用RSA公钥解密
     *
     * @param pubKeyInByte
     *            公钥打包成byte[]形式
     * @param data
     *            要解密的数据
     * @return 解密数据
     */
    public static byte[] decryptByRSA1(byte[] pubKeyInByte, byte[] data) {
        try {
            KeyFactory mykeyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec pub_spec = new X509EncodedKeySpec(pubKeyInByte);
            PublicKey pubKey = mykeyFactory.generatePublic(pub_spec);
            Cipher cipher = Cipher.getInstance(mykeyFactory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, pubKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            return null;
        }
    }

    private static class DogConfig{
        private String expiredTime;
        private boolean enableDog;

        public String getExpiredTime() {
            return expiredTime;
        }
        public void setExpiredTime(String expiredTime) {
            this.expiredTime = expiredTime;
        }
        public boolean isEnableDog() {
            return enableDog;
        }
        public void setEnableDog(boolean enableDog) {
            this.enableDog = enableDog;
        }
        @Override
        public String toString() {
            return "DogConfig [expiredTime=" + expiredTime + ", enableDog=" + enableDog + "]";
        }
    }
}
