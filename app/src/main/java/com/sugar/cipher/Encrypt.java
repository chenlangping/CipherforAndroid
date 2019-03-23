package com.sugar.cipher;

public class Encrypt {

    public String Caesar(String plaintext, String key) {
        char[] plainrecord = plaintext.toCharArray();
        int[] record = new int[plainrecord.length];
        for (int i = 0; i < record.length; i++) {
            if (plainrecord[i] >= 'a' && plainrecord[i] <= 'z') {
                record[i] = 0;
            } else if (plainrecord[i] >= 'A' && plainrecord[i] <= 'Z') {
                record[i] = 1;
            } else
                record[i] = -1;
        }
        plaintext = plaintext.toLowerCase();
        key = key.toLowerCase();
        // 记录下传入时候的大小写，并且转化为小写

        String cipher;
        char[] ciphertext = plaintext.toCharArray();
        int a;
        a = plaintext.length();
        for (int i = 0; i < a; i++) {
            if (plaintext.charAt(i) >= 'a' && plaintext.charAt(i) <= 'z') {
                if (plaintext.charAt(i) + key.charAt(0) - 'a' + 1 <= 'z')
                    ciphertext[i] = (char) (ciphertext[i] + key.charAt(0) - 'a' + 1);
                else
                    ciphertext[i] = (char) (ciphertext[i] + key.charAt(0) - 'a' - 25);
            } else if (plaintext.charAt(i) >= 'A' && plaintext.charAt(i) <= 'Z') {
                if (plaintext.charAt(i) + key.charAt(0) - 'a' + 1 <= 'Z')
                    ciphertext[i] = (char) (ciphertext[i] + key.charAt(0) - 'a' + 1);
                else
                    ciphertext[i] = (char) (ciphertext[i] + key.charAt(0) - 'a' - 25);
            }
        }
        cipher = new String(ciphertext);

        char[] cipherrecord = cipher.toCharArray();
        for (int i = 0; i < record.length; i++) {
            if (record[i] == 0) {
            } else if (record[i] == 1) {
                cipherrecord[i] = Character.toUpperCase(cipherrecord[i]);
            }
        }
        cipher = new String(cipherrecord);
        // 最后将要传出去的内容String cipher复原

        return cipher;
    }

    public String Autocipher(String plaintext, String key) {
        char[] plainrecord = plaintext.toCharArray();
        int[] record = new int[plainrecord.length];
        for (int count = 0; count < record.length; count++) {
            if (plainrecord[count] >= 'a' && plainrecord[count] <= 'z') {
                record[count] = 0;
            } else if (plainrecord[count] >= 'A' && plainrecord[count] <= 'Z') {
                record[count] = 1;
            } else
                record[count] = -1;
        }
        plaintext = plaintext.toLowerCase();
        key = key.toLowerCase();
        // 记录下传入时候的大小写，并且转化为小写

        char alphbet[][] = new char[26][26];
        char alpht[] = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
                'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        int i, j, size, sizet, num;
        char text[] = plaintext.toCharArray();
        char keynew[] = key.toCharArray();
        char cipher[] = new char[plaintext.length()];
        for (i = 0; i < 26; i++) {
            for (j = 0; j < 26; j++) {
                alphbet[i][j] = alpht[(j + i) % 26];
            }
        } // 得到表
        size = keynew.length;
        for (i = 0; i < text.length; i++) {
            if (i < size)
                num = keynew[i] - 97;
            else
                num = text[i - size] - 97;
            cipher[i] = alphbet[num][text[i] - 97];
        }

        String ciphertext = new String(cipher);

        char[] cipherrecord = ciphertext.toCharArray();
        for (int count = 0; count < record.length; count++) {
            if (record[count] == 0) {
            } else if (record[count] == 1) {
                cipherrecord[count] = Character.toUpperCase(cipherrecord[count]);
            }
        }
        ciphertext = new String(cipherrecord);
        // 还原

        return ciphertext;
    }

    public String ColumnPermutationcipher(String plaintext, String key) {

        while (plaintext.length() % key.length() != 0) {
            plaintext = plaintext + "x";
        }
        String ciphertext;
        int num[] = new int[100];
        int j;
        int plen = plaintext.length();
        int len = key.length();
        for (int i = 0; i < len; i++) {
            num[i] = 1;
            for (j = 0; j < len; j++) {
                if (key.charAt(j) < key.charAt(i))
                    num[i]++;
            }
            for (j = 0; j < i; j++) {
                if (key.charAt(j) == key.charAt(i))
                    num[i] = num[i] + 1;
            }
        }
        int k = 0;
        char[] cipher = new char[plen];
        for (int i = 0; i < len; i++) {
            for (j = 0; j < plen / len; j++) {
                cipher[k] = plaintext.charAt(j * len + num[i] - 1);
                k++;
            }
        }
        ciphertext = new String(cipher);

        return ciphertext;
    }

    public String Vigenere(String plaintext, String key) {
        char[] plainrecord = plaintext.toCharArray();
        int[] record = new int[plainrecord.length];
        for (int count = 0; count < record.length; count++) {
            if (plainrecord[count] >= 'a' && plainrecord[count] <= 'z') {
                record[count] = 0;
            } else if (plainrecord[count] >= 'A' && plainrecord[count] <= 'Z') {
                record[count] = 1;
            } else
                record[count] = -1;
        }
        plaintext = plaintext.toLowerCase();
        key = key.toLowerCase();
        // 记录下传入时候的大小写，并且转化为小写

        String ciphertext;
        char[] cipher = plaintext.toCharArray();
        String a = "abcdefghijklmnopqrstuvwxyz";
        char[] b = a.toCharArray();
        char[][] table = new char[26][26];
        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < 26; j++)
                table[i][j] = (char) (b[(j + i) % 26]);
        }
        int c = key.length();
        int d = plaintext.length();
        for (int i = 0; i < d; i++) {
            cipher[i] = (char) (table[key.charAt(i % c) - 97][plaintext.charAt(i) - 97]);
        }
        ciphertext = new String(cipher);

        char[] cipherrecord = ciphertext.toCharArray();
        for (int count = 0; count < record.length; count++) {
            if (record[count] == 0) {
            } else if (record[count] == 1) {
                cipherrecord[count] = Character.toUpperCase(cipherrecord[count]);
            }
        }
        ciphertext = new String(cipherrecord);
        // 还原

        return ciphertext;
    }

    public String Keyword(String plaintext, String key) {
        char[] plainrecord = plaintext.toCharArray();
        int[] record = new int[plainrecord.length];
        for (int count = 0; count < record.length; count++) {
            if (plainrecord[count] >= 'a' && plainrecord[count] <= 'z') {
                record[count] = 0;
            } else if (plainrecord[count] >= 'A' && plainrecord[count] <= 'Z') {
                record[count] = 1;
            } else
                record[count] = -1;
        }
        plaintext = plaintext.toLowerCase();
        key = key.toLowerCase();
        // 记录下传入时候的大小写，并且转化为小写

        char a[] = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
                'u', 'v', 'w', 'x', 'y', 'z'};
        char newkey[] = key.toCharArray();
        char text[] = plaintext.toCharArray();
        char b[] = new char[26];
        int size, i, k = 1, j, l, m, sizet;
        size = newkey.length;
        for (i = 1; i < size; i++) {
            for (j = 0; j < i; j++) {
                if (newkey[i] == newkey[j])
                    break;
                if (j == i - 1) {
                    newkey[k] = newkey[i];
                    k++;
                }
            }
        } // 删除重复部分密钥
        m = k;
        for (j = 0; j < k; j++)
            b[j] = newkey[j];// 将新字母表首部重置
        for (j = 0; j < 26; j++) {
            for (l = 0; l < k; l++) {
                if (a[j] == newkey[l])
                    break;
                if (l == k - 1) {
                    b[m] = a[j];
                    m++;
                }
            }
        } // 得到该密钥下新字母表
        sizet = text.length;
        for (i = 0; i < sizet; i++) {
            text[i] = b[text[i] - 97];
        }
        String ciphertext = new String(text);

        char[] cipherrecord = ciphertext.toCharArray();
        for (int count = 0; count < record.length; count++) {
            if (record[count] == 0) {
            } else if (record[count] == 1) {
                cipherrecord[count] = Character.toUpperCase(cipherrecord[count]);
            }
        }
        ciphertext = new String(cipherrecord);
        // 还原

        return ciphertext;
    }

    public String Permutationcipher(String plaintext, String key) {
        char keytext[] = key.toCharArray();
        int order[] = new int[keytext.length];
        char plain[] = plaintext.toCharArray();
        double c = Math.ceil((double) (plain.length) / (double) (keytext.length));
        char newplain[][] = new char[(int) c][keytext.length];
        char cipher[][] = new char[(int) c][keytext.length];
        int i = 0, j;
        char newcipher[] = new char[(int) c * keytext.length];
        for (i = keytext.length - 1; i >= 0; i--) {
            for (j = i - 1; j >= 0; j--) {
                if (keytext[i] < keytext[j])
                    order[j]++;
                else
                    order[i]++;
            }
        } // 得到密钥顺序的简便算法。
        for (i = 0; i < (int) c; i++) {
            for (j = 0; j < keytext.length; j++) {
                if (i == ((plain.length) / (keytext.length)) && ((keytext.length) * i + j) >= plain.length)
                    newplain[i][j] = 'x';
                else
                    newplain[i][j] = plain[(keytext.length) * i + j];
            }
        } // 将明文分组存储；
        for (i = 0; i < (int) c; i++) {
            for (j = 0; j < keytext.length; j++) {
                cipher[i][order[j]] = newplain[i][j];
            }
        }
        for (i = 0; i < (int) c; i++) {
            for (j = 0; j < keytext.length; j++) {
                newcipher[keytext.length * i + j] = cipher[i][j];
            }
        }
        String ciphertext = new String(newcipher);
        return ciphertext;
    }

    public String DES(String plaintext, String key) {

        while (plaintext.length() % 8 != 0) {
            plaintext = plaintext + "x";
        }
        // 如果不足8位，则添加x
        System.out.println(plaintext);
        String plaintextcopy = plaintext;
        int plaintextlength = plaintext.length();
        String ciphertextcopy = "";

        for (int cyle = 0; cyle < plaintextlength; cyle = cyle + 8) {
            plaintext = plaintextcopy.substring(cyle, cyle + 8);
            System.out.println(plaintext + plaintext.length());
            // 密钥PC-1块
            final int PC1_Table[] = {57, 49, 41, 33, 25, 17, 9, 1, 58, 50, 42, 34, 26, 18, 10, 2, 59, 51, 43, 35, 27,
                    19, 11, 3, 60, 52, 44, 36, 63, 55, 47, 39, 31, 23, 15, 7, 62, 54, 46, 38, 30, 22, 14, 6, 61, 53, 45,
                    37, 29, 21, 13, 5, 28, 20, 12, 4};

            // 密钥PC-2块
            final int PC2_Table[] = {14, 17, 11, 24, 1, 5, 3, 28, 15, 6, 21, 10, 23, 19, 12, 4, 26, 8, 16, 7, 27, 20,
                    13, 2, 41, 52, 31, 37, 47, 55, 30, 40, 51, 45, 33, 48, 44, 49, 39, 56, 34, 53, 46, 42, 50, 36, 29,
                    32};

            // 密钥每阶段左移位数列表数组
            final int LOOP_Table[] = {1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1};

            // 明文初始换位表 IP
            final int IP_Table[] = {58, 50, 42, 34, 26, 18, 10, 2, 60, 52, 44, 36, 28, 20, 12, 4, 62, 54, 46, 38, 30,
                    22, 14, 6, 64, 56, 48, 40, 32, 24, 16, 8, 57, 49, 41, 33, 25, 17, 9, 1, 59, 51, 43, 35, 27, 19, 11,
                    3, 61, 53, 45, 37, 29, 21, 13, 5, 63, 55, 47, 39, 31, 23, 15, 7};

            // 明文第16次换位表
            final int IPR_Table[] = {40, 8, 48, 16, 56, 24, 64, 32, 39, 7, 47, 15, 55, 23, 63, 31, 38, 6, 46, 14, 54,
                    22, 62, 30, 37, 5, 45, 13, 53, 21, 61, 29, 36, 4, 44, 12, 52, 20, 60, 28, 35, 3, 43, 11, 51, 19, 59,
                    27, 34, 2, 42, 10, 50, 18, 58, 26, 33, 1, 41, 9, 49, 17, 57, 25};

            // E-boxes
            final int E_Table[] = {32, 1, 2, 3, 4, 5, 4, 5, 6, 7, 8, 9, 8, 9, 10, 11, 12, 13, 12, 13, 14, 15, 16, 17,
                    16, 17, 18, 19, 20, 21, 20, 21, 22, 23, 24, 25, 24, 25, 26, 27, 28, 29, 28, 29, 30, 31, 32, 1};

            // P-boxes
            final int PBox[] = {16, 7, 20, 21, 29, 12, 28, 17, 1, 15, 23, 26, 5, 18, 31, 10, 2, 8, 24, 14, 32, 27, 3,
                    9, 19, 13, 30, 6, 22, 11, 4, 25};

            // S-boxes
            final int S_Box[][][] = {
                    // S1
                    {{14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
                            {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
                            {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
                            {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}},
                    // S2
                    {{15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
                            {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
                            {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
                            {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}},
                    // S3
                    {{10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
                            {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
                            {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
                            {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}},
                    // S4
                    {{7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
                            {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
                            {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
                            {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}},
                    // S5
                    {{2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
                            {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
                            {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
                            {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}},
                    // S6
                    {{12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
                            {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
                            {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
                            {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}},
                    // S7
                    {{4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
                            {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
                            {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
                            {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}},
                    // S8
                    {{13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
                            {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
                            {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
                            {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}}};

            int key1[] = new int[56]; // 根据用户输入的关键字得到的56位密钥
            int key2[] = new int[64]; // 添加完奇偶校验位的64位密钥
            int key3[] = new int[56]; // 经过PC-1之后的密钥
            int i, j; // 存放循环变量
            int temp; // 存放中间变量
            int r; // 轮数
            int p[] = new int[64]; // 明文转换成ASCII码之后
            int p2[] = new int[64]; // 明文经过IT变换
            char keyword[] = new char[7]; // 存放用户输入的7位的单词
            char plaintxt[] = new char[8]; // 存放明文
            int lp[] = new int[32];
            int rp[] = new int[32];// 分别对应了左半部分和右半部分
            int erp[] = new int[48]; // 存放经过EBOX之后的
            int srp[] = new int[48]; // 存放进入SBOX之后与子密钥进行异或之后的
            int subkey[][] = new int[16][48];// 二维数组存放16轮子密钥
            int sp[] = new int[32]; // 存放经过SBOX之后的
            int prp[] = new int[32]; // 存放经过PBOS之后的
            int ciphertext[] = new int[64]; // 存放密文
            int last[] = new int[64]; // 存放最后一次操作之前的
            int copyrp[] = new int[32];// 右半部分的备份
            int column, line; // 对应子BOX的行与列

            keyword = key.toCharArray();
            plaintxt = plaintext.toCharArray();
            for (i = 0; i < 7; i++) {
                temp = (int) keyword[i];
                for (j = 8 * i + 7; j >= 8 * i; j--) {

                    key1[j] = temp % 2;
                    temp = temp / 2;
                }
            } // 根据关键字生成密钥

            for (i = 0; i < 64; i++) {
                j = i + i / 7;
                if (j < 64) {

                    key2[i + i / 7] = key1[i];
                }
            }
            for (i = 7; i < 64; i = i + 8) {
                key2[i] = (key2[i - 1] + key2[i - 2] + key2[i - 3] + key2[i - 4] + key2[i - 5] + key2[i - 6]
                        + key2[i - 7] + 1) % 2;
            } // 添加完奇偶校验位

            for (i = 0; i < 56; i++) {
                key3[i] = key2[PC1_Table[i] - 1];
            } // 经过PC-1

            for (r = 0; r < 16; r++) {
                if (r == 0 || r == 1 || r == 8 || r == 15) {
                    temp = key3[0];
                    for (i = 0; i < 27; i++) {
                        key3[i] = key3[i + 1];
                    }
                    key3[27] = temp;

                    temp = key3[28];
                    for (i = 28; i < 55; i++) {
                        key3[i] = key3[i + 1];
                    }
                    key3[55] = temp;
                } // 如果是第1,2,9,16轮，只需要循环左移一次
                else {
                    temp = key3[0];
                    for (i = 0; i < 27; i++) {
                        key3[i] = key3[i + 1];
                    }
                    key3[27] = temp;
                    temp = key3[0];
                    for (i = 0; i < 27; i++) {
                        key3[i] = key3[i + 1];
                    }
                    key3[27] = temp;

                    temp = key3[28];
                    for (i = 28; i < 55; i++) {
                        key3[i] = key3[i + 1];
                    }
                    key3[55] = temp;
                    temp = key3[28];
                    for (i = 28; i < 55; i++) {
                        key3[i] = key3[i + 1];
                    }
                    key3[55] = temp;
                } // 如果是其他的则循环两次

                for (i = 0; i < 48; i++) {
                    subkey[r][i] = key3[PC2_Table[i] - 1];
                }

            } // 子密钥生成完毕

            for (i = 0; i < 8; i++) {
                temp = (int) plaintxt[i];
                for (j = 8 * i + 7; j >= 8 * i; j--) {

                    p[j] = temp % 2;
                    temp = temp / 2;
                }
            } // 把明文转换成64位的ASCII码

            for (i = 0; i < 64; i++) {
                p2[i] = p[IP_Table[i] - 1];
            } // 经过IT操作

            System.out.println("初始化操作之后");
            for (i = 0; i < 64; i++) {
                System.out.print(p2[i] + " ");
                if ((i + 1) % 8 == 0)
                    System.out.println("");
            }

            for (i = 0; i < 32; i++) {
                lp[i] = p2[i];
                rp[i] = p2[i + 32];
                copyrp[i] = p2[i + 32];
            } // 分成左右部分

            // 加密循环开始
            for (r = 0; r < 16; r++) {
                for (i = 0; i < 48; i++) {
                    erp[i] = rp[E_Table[i] - 1];
                } // 经过E-BOX

                System.out.println("EBOX之后");
                for (i = 0; i < 48; i++) {
                    System.out.print(erp[i] + " ");
                    if ((i + 1) % 8 == 0)
                        System.out.println("");
                }
                for (i = 0; i < 48; i++) {
                    if (subkey[r][i] == 1 && erp[i] == 1)
                        srp[i] = 0;
                    else if (subkey[r][i] == 1 && erp[i] == 0)
                        srp[i] = 1;
                    else if (subkey[r][i] == 0 && erp[i] == 1)
                        srp[i] = 1;
                    else if (subkey[r][i] == 0 && erp[i] == 0)
                        srp[i] = 0;
                } // 经过异或操作之后

                for (i = 0; i < 8; i++) {
                    line = srp[6 * i] * 2 + srp[6 * i + 5] * 1; // printf("line
                    // =%d
                    // ",line);
                    column = srp[6 * i + 1] * 8 + srp[6 * i + 2] * 4 + srp[6 * i + 3] * 2 + srp[6 * i + 4] * 1;// printf("column
                    // =%d
                    // ",column);
                    temp = S_Box[i][line][column];// printf("temp =%d ",temp);
                    for (j = i * 4 + 3; j >= i * 4; j--) {
                        sp[j] = temp % 2;
                        temp = temp / 2;
                    }

                } // 对于每一个子BOX分别输出

                for (i = 0; i < 32; i++) {
                    prp[i] = sp[PBox[i] - 1];
                } // 经过PBOX处理

                for (i = 0; i < 32; i++) {
                    if (lp[i] == 1 && prp[i] == 1)
                        rp[i] = 0;
                    else if (lp[i] == 1 && prp[i] == 0)
                        rp[i] = 1;
                    else if (lp[i] == 0 && prp[i] == 1)
                        rp[i] = 1;
                    else if (lp[i] == 0 && prp[i] == 0)
                        rp[i] = 0;
                } // 与lp异或，并赋值给右半部分

                for (i = 0; i < 32; i++) {
                    lp[i] = copyrp[i];
                    copyrp[i] = rp[i];
                }
            } // 加密循环结束

            for (i = 0; i < 32; i++) {
                last[i] = rp[i];
            }
            for (i = 0; i < 32; i++) {
                last[i + 32] = lp[i];
            } // 左右合并，并进行一次交换

            for (i = 0; i < 64; i++) {
                ciphertext[i] = last[IPR_Table[i] - 1];
            }

            System.out.println("最终结果");
            for (i = 0; i < 64; i++) {
                System.out.print(ciphertext[i] + " ");
                if ((i + 1) % 8 == 0)
                    System.out.println("");
            }

            /*
             * 每八位转换成整数 int[] Dec = new int[8]; Dec[0] = ciphertext[0]; for(int
             * i1 = 1 , k = 0; i1 < 64; i1++){ if(i1 % 8 != 0){ Dec[k] = (Dec[k]
             * * 2) + ciphertext[i1]; } else{ k++; Dec[k] = ciphertext[i1]; }
             *
             * }
             *
             *
             * String finalciphertext = "";
             *
             * for(i=0;i<8;i++){ finalciphertext = finalciphertext + (char
             * )Dec[i]; }
             *
             * System.out.println(finalciphertext);
             */

            String str = "";
            for (i = 0; i < ciphertext.length; i++) {
                str = str + ciphertext[i];// 拼接成字符串，最终放在变量str中
            }

            //return str;
            ciphertextcopy = ciphertextcopy + str;
        }
        return ciphertextcopy;

    }

    public String CA(String plaintext, String key, String Cell) {

        CAalgorithm ca = new CAalgorithm(key, Cell);
        String cryptext = ca.CAcrypt(plaintext);
        return cryptext;
    }


}
