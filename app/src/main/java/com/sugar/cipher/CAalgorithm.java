package com.sugar.cipher;

/**
 * Created by Sugar on 2016/9/21.
 */
public class CAalgorithm {
    public int RuleTable[];
    private int CELLSIZE;
    private int RULE;
    private String Cell;
    private String Key;
    private char[] key;

    public CAalgorithm(String Rule, String cell) {
        CELLSIZE = cell.length();        //设定CELL长度
        Cell = cell;
        RULE = Integer.parseInt(Rule);                //自动机确定规则
        RuleTable = new int[CELLSIZE];        //创建规则映射表

        //System.out.print("ruletable is: ");
        for (int i = CELLSIZE - 1; i >= 0; i--) {        //把规则数转换为规则（二进制）， 并且存入规则表中
            RuleTable[i] = RULE % 2;
            RULE = RULE / 2;
            //System.out.print(RuleTable[i]+",");
        }        //注：数组高低位与原二进制数的高低位刚好相反

    }


    public String CAcrypt(String text) {        //加解密
        char[] txt = text.toCharArray();
        CreateKey(text.length());        //生成秘钥

        for (int i = 0; i < text.length(); i++) {            //异或操作
            if (txt[i] == key[i]) txt[i] = '0';
            else if (txt[i] != key[i]) txt[i] = '1';
        }

        String finaltext = new String(txt);
        return finaltext;
    }

    /*
     * 以下是工具方法
     */
    public int GetNeighborNum(int i, String txt) {
        char[] s = txt.toCharArray();
        int mapnum = 0;
        if ((i % CELLSIZE) >= 0 && (i % CELLSIZE) <= CELLSIZE - 3)
            mapnum = 4 * (s[i] - '0') + 2 * (s[i + 1] - '0') + s[i + 2] - '0';
        else {
            if (i % CELLSIZE == CELLSIZE - 2)
                mapnum = 4 * (s[i] - '0') + 2 * (s[(i + 1)] - '0') + s[(i + 2) - CELLSIZE] - '0';
            else if (i % CELLSIZE == CELLSIZE - 1)
                mapnum = 4 * (s[i] - '0') + 2 * (s[(i + 1) - CELLSIZE] - '0') + s[(i + 2) - CELLSIZE] - '0';
        }
        return mapnum;
    }

    public int GetNeighborNum(int i, char[] s) {        //重载GetNeighborNum(int, String)
        int mapnum = 0;
        if ((i % CELLSIZE) >= 0 && (i % CELLSIZE) <= CELLSIZE - 3)
            mapnum = 4 * (s[i] - '0') + 2 * (s[i + 1] - '0') + s[i + 2] - '0';
        else {
            if (i % CELLSIZE == CELLSIZE - 2)
                mapnum = 4 * (s[i] - '0') + 2 * (s[(i + 1)] - '0') + s[(i + 2) - CELLSIZE] - '0';
            else if (i % CELLSIZE == CELLSIZE - 1)
                mapnum = 4 * (s[i] - '0') + 2 * (s[(i + 1) - CELLSIZE] - '0') + s[(i + 2) - CELLSIZE] - '0';
        }
        return mapnum;
    }

    public String CreateKey(int len) {
        key = new char[len];
        System.out.print("\nkey is: ");
        //System.out.println(RuleTable[GetNeighborNum(1, Cell)]);

        for (int i = 0; i < CELLSIZE; i++) {
            key[i] = (char) (RuleTable[GetNeighborNum(i, Cell)] + '0');
            //System.out.print(key[i]);
        }

        for (int i = CELLSIZE; i < len; i++)
            key[i] = (char) (RuleTable[GetNeighborNum(i - CELLSIZE, key)] + '0');

        Key = new String(key);
        return Key;
    }
}