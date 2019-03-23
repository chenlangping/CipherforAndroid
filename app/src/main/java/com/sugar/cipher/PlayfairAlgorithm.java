package com.sugar.cipher;

import java.nio.CharBuffer;

/**
 * Created by Sugar on 2016/9/22.
 */
public class PlayfairAlgorithm {

    /**
     * 金杰加密算法
     **/
    public static char[] encode(char[] plaintext, char[] key, char replace) {
        // 构造解密矩阵
        char[][] matrix = constructMatrix(key);
        // 为缓冲区分配空间
        CharBuffer xplaintext = CharBuffer.allocate(plaintext.length * 2);
        // 重新生成明文
        int len = 0;
        int i = 0;
        for (i = 0; i < plaintext.length - 1; i++) {
            xplaintext.append(plaintext[i]);
            if (i != plaintext.length - 1) {
                if (plaintext[i] == plaintext[i + 1]) {
                    xplaintext.append(replace);
                } else {
                    xplaintext.append(plaintext[i + 1]);
                    i++;
                }
            } else {
                xplaintext.append(replace);
            }
            len += 2;
        }
        // 剩余最后一个字符
        if (i == plaintext.length - 1) {
            xplaintext.append(plaintext[i]);
            xplaintext.append(replace);
            len += 2;
        }
        char[] xxplaintext = new char[len];
        xplaintext.position(0);
        xplaintext.get(xxplaintext);
        System.out.println("整理后的明文: " + new String(xxplaintext));
        char[] ciphertext = getciphertext(xxplaintext, matrix);
        return ciphertext;
    }

    /**
     * 根据密文和密钥解密密文并返回生成的明文
     *
     * @param ciphertext
     * @param key
     * @return
     */
    public static char[] decode(char[] ciphertext, char[] key) {
        // 构造解密矩阵
        char[][] matrix = constructMatrix(key);
        char[] plaintext = getplaintext(ciphertext, matrix);
        return plaintext;
    }

    /**
     * 根据密文和解密矩阵返回明文
     *
     * @param ciphertext
     * @param matrix
     * @return
     */
    private static char[] getplaintext(char[] ciphertext, char[][] matrix) {
        char[] plaintext = new char[ciphertext.length];
        int index = 0;
        for (int i = 0; i < ciphertext.length; i += 2) {
            int row1, row2, col1, col2;
            String[] pos1, pos2;
            pos1 = getPosition(matrix, ciphertext[i]);
            pos2 = getPosition(matrix, ciphertext[i + 1]);
            if (pos1 == null || pos2 == null) {
                throw new RuntimeException("m密文中包含无效字符!!!");
            }
            row1 = Integer.parseInt(pos1[0]);
            col1 = Integer.parseInt(pos1[1]);
            row2 = Integer.parseInt(pos2[0]);
            col2 = Integer.parseInt(pos2[1]);
            if (row1 == row2) {
                // 同一行的情况
                if (col1 == 0) {
                    plaintext[index++] = matrix[row1][matrix[0].length - 1];
                    plaintext[index++] = matrix[row1][(col2 - 1 + 5) % 5];
                } else if (col2 == 0) {
                    plaintext[index++] = matrix[row1][(col1 - 1 + 5) % 5];
                    plaintext[index++] = matrix[row1][matrix[0].length - 1];
                } else {
                    plaintext[index++] = matrix[row1][(col1 - 1 + 5) % 5];
                    plaintext[index++] = matrix[row1][(col2 - 1 + 5) % 5];
                }

            } else if (col1 == col2) {
                // 同一列的情况
                if (row1 == 0) {
                    plaintext[index++] = matrix[matrix.length - 1][col1];
                    plaintext[index++] = matrix[(row2 - 1 + 5) % 5][col1];
                } else if (row2 == 0) {
                    plaintext[index++] = matrix[(row1 - 1 + 5) % 5][col1];
                    plaintext[index++] = matrix[matrix.length - 1][col1];
                } else {
                    plaintext[index++] = matrix[(row1 - 1 + 5) % 5][col1];
                    plaintext[index++] = matrix[(row2 - 1 + 5) % 5][col2];
                }
            } else {
                plaintext[index++] = matrix[row1][col2];
                plaintext[index++] = matrix[row2][col1];
            }
        }
        return plaintext;
    }

    /**
     * 根据明文和加密矩阵得到密文
     *
     * @param plaintext
     * @param matrix
     * @return
     */
    private static char[] getciphertext(char[] plaintext, char[][] matrix) {
        char[] ciphertext = new char[plaintext.length];
        int index = 0;
        for (int i = 0; i < plaintext.length - 1; i += 2) {
            int row1, row2, col1, col2;
            String[] pos1, pos2;
            pos1 = getPosition(matrix, plaintext[i]);
            pos2 = getPosition(matrix, plaintext[i + 1]);
            if (pos1 == null || pos2 == null) {
                throw new RuntimeException("明文中包含无效字符!!!");
            }
            row1 = Integer.parseInt(pos1[0]);
            col1 = Integer.parseInt(pos1[1]);
            row2 = Integer.parseInt(pos2[0]);
            col2 = Integer.parseInt(pos2[1]);
            if (row1 == row2) {
                // 同一行的情况
                if (col1 == matrix[0].length - 1) {
                    ciphertext[index++] = matrix[row1][(col1 + 1) % 5];
                    ciphertext[index++] = matrix[row1][(col2 + 1) % 5];
                } else if (col2 == matrix[0].length - 1) {
                    ciphertext[index++] = matrix[row1][(col1 + 1) % 5];
                    ciphertext[index++] = matrix[row1][(col2 + 1) % 5];
                } else {
                    ciphertext[index++] = matrix[row1][(col1 + 1) % 5];
                    ciphertext[index++] = matrix[row1][(col2 + 1) % 5];
                }
            } else if (col1 == col2) {
                //同一列的情况
                if (row1 == matrix.length - 1) {
                    ciphertext[index++] = matrix[row1 + 1][col1];
                    ciphertext[index++] = matrix[(row2 + 1) % 5][col1];
                } else if (col2 == matrix.length - 1) {
                    ciphertext[index++] = matrix[(row1 + 1) % 5][col1];
                    ciphertext[index++] = matrix[row2 + 1][col1];
                } else {
                    ciphertext[index++] = matrix[(row1 + 1) % 5][col1];
                    ciphertext[index++] = matrix[(row2 + 1) % 5][col2];
                }
            } else {
                ciphertext[index++] = matrix[row1][col2];
                ciphertext[index++] = matrix[row2][col1];
            }
        }
        return ciphertext;
    }

    /**
     * 返回字符在矩阵中的位置
     *
     * @param matrix
     * @param ch
     * @return
     */
    private static String[] getPosition(char[][] matrix, char ch) {
        String[] pos = new String[]{null, null};
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if ((matrix[i][j] == ch) || (matrix[i][j] == 'j' && ch == 'i') || (matrix[i][j] == 'i' && ch == 'j')) {
                    pos[0] = i + "";
                    pos[1] = j + "";
                    return pos;
                }
            }
        }
        return null;
    }

    /**
     * 用密钥构造矩阵
     *
     * @param cyber
     * @param key
     * @return
     */
    private static char[][] constructMatrix(char[] key) {
        char[][] matrix = new char[5][5];
        CharBuffer buf = CharBuffer.allocate(25);
        buf.append(key[0]);
        // 移除密钥中重复的字符
        for (int i = 1; i < key.length; i++) {
            if (!contains(buf.array(), key[i])) {
                buf.append(key[i]);
            }
        }
        // 将字母表中剩余的字符加入
        for (int i = 0; i < 26; i++) {
            char ch = (char) ('a' + i);
            if (!contains(buf.array(), ch)) {
                buf.append(ch);
            }
        }
        int index = 0;
        buf.position(0);
        System.out.println("开始构造矩阵...");
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (index != buf.length()) {
                    matrix[i][j] = buf.get(index++);
                    System.out.print(matrix[i][j] + "\t");
                }
            }
            System.out.println();
        }
        buf.clear();
        return matrix;
    }

    /**
     * 判断是否包含字符（这里将i和j视为同一个字符）
     *
     * @param buf
     * @param c
     * @return
     */
    private static boolean contains(char[] buf, char c) {
        for (int i = 0; i < buf.length; i++) {
            if (buf[i] == c || (c == 'j' && buf[i] == 'i') || (c == 'i' && buf[i] == 'j'))
                return true;
        }
        return false;
    }

}
