package com.sugar.cipher;

import java.math.BigInteger;

class DHexchange {
    public BigInteger g;    //原根
    public BigInteger p;    //素数
    public BigInteger A;
    public BigInteger B;
    protected BigInteger K;
    protected int sn = 0;

    public BigInteger getK() {
        return K;
    }
}

class SDH extends DHexchange {


    public SDH(String p, String g, String secretnum) {        //服务器公开A
        Integer temp = new Integer(secretnum.trim());
        this.p = new BigInteger(p);
        this.g = new BigInteger(g);
        sn = temp.intValue();
        A = this.g.pow(sn);
    }

    public void setK(String clientB) {
        B = new BigInteger(clientB);
        K = this.B.pow(sn);
        K = K.mod(p);
    }
}

class CDH extends DHexchange {
    public CDH(String p, String g, String clientA, String secretnum) {        //客户端获取p, g, A, 公开B，生成K
        Integer temp = new Integer(secretnum.trim());
        this.g = new BigInteger(g);
        this.p = new BigInteger(p);
        sn = temp.intValue();
        this.B = this.g.pow(sn);

        A = new BigInteger(clientA.trim());
        K = this.A.pow(sn);
        K = K.mod(this.p);
    }
}
