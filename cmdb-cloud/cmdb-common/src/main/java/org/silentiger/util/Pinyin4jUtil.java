package org.silentiger.util;

import io.lettuce.core.pubsub.PubSubCommandHandler;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.lang3.StringUtils;

/**
 * Pinyin4Util类
 * 汉字转拼音工具类
 *
 * @Author silentiger@yyh
 * @Date 2023-12-29 17:25:54
 */
public class Pinyin4jUtil {

    /**
     * 获得汉语拼音首字母 大写
     *
     * @param chines 汉字
     * @return
     */
    public static String getAlpha2UpperCase(String chines) {
        StringBuilder pinyinName = new StringBuilder();
        char[] nameChar = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < nameChar.length; i++) {
            if (nameChar[i] > 128) {
                try {
                    pinyinName.append(PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat)[0].charAt(0));
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    System.out.println("获得汉语拼音首字母异常:" + e.getMessage());
                }
            } else {
                pinyinName.append(nameChar[i]);
            }
        }
        return pinyinName.toString();
    }

    /**
     * 将字符串中的中文转化为拼音,英文字符不变
     * 小写
     * @param inputString 汉字
     * @return
     */
    public static String getPingYin(String inputString) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        StringBuilder output = new StringBuilder();
        if (StringUtils.isNotBlank(inputString) && !"null".equals(inputString)) {
            char[] input = inputString.trim().toCharArray();
            try {
                for (int i = 0; i < input.length; i++) {
                    if (Character.toString(input[i]).matches("[\\u4E00-\\u9FA5]+")) {
                        String[] temp = PinyinHelper.toHanyuPinyinStringArray(input[i], format);
                        output.append(temp[0]);
                    } else {
                        output.append(input[i]);
                    }
                }
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                System.out.println("将字符串中的中文转化为拼音,英文字符不变异常 :" + e.getMessage());
            }
        } else {
            return "";
        }
        return output.toString();
    }

    /**
     * 转换成每个字首字母大写的拼音，英文
     * @param chines 可能包含汉字的字符串
     * @return
     */
    public static String convertToFirstUpper(String chines) {
        HanyuPinyinOutputFormat lowerFormat = new HanyuPinyinOutputFormat();
        lowerFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        lowerFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        lowerFormat.setVCharType(HanyuPinyinVCharType.WITH_V);

        HanyuPinyinOutputFormat upperFormat = new HanyuPinyinOutputFormat();
        upperFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        upperFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        StringBuilder output = new StringBuilder();
        if (StringUtils.isNotBlank(chines) && !"null".equals(chines)) {
            char[] input = chines.trim().toCharArray();
            try {
                for (char c : input) {
                    if (c > 128) {  // 对于英文不转
//                    if (Character.toString(c).matches("[\\u4E00-\\u9FA5]+")) {
                        char firstUpper = PinyinHelper.toHanyuPinyinStringArray(c, upperFormat)[0].charAt(0);
                        String temp = PinyinHelper.toHanyuPinyinStringArray(c, lowerFormat)[0].substring(1);
                        output.append(firstUpper).append(temp);
                    } else {
                        output.append(c);
                    }
                }
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                System.out.println("将字符串中的中文转化为拼音,英文字符不变异常 :" + e.getMessage());
            }
        } else {
            return "";
        }
        return output.toString();
    }

    /**
     * 汉字转换为汉语拼音首字母，英文字符不变
     *
     * @param chines 汉字
     * @return 拼音
     */
    public static String converterToFirstSpell(String chines) {
        StringBuilder pinyinName = new StringBuilder();
        char[] nameChar = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        try {
            for (char c : nameChar) {
                if (c > 128) {
                        pinyinName.append(PinyinHelper.toHanyuPinyinStringArray(c, defaultFormat)[0].charAt(0));
                } else {
                    pinyinName.append(c);
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            System.out.println("汉字转换位汉语拼音首字母，英文字符不变异常 :" + e.getMessage());
        }
        return pinyinName.toString();
    }

    /**
     * 汉字转换位汉语拼音首字母，英文字符不变(小写)
     *
     * @param chines 汉字
     * @return 拼音
     */
    public static String converterToFirstSpellSmal(String chines) {
        StringBuilder pinyinName = new StringBuilder();
        char[] nameChar = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        try {
            for (char c : nameChar) {
                if (c > 128) {
                    if (Character.toString(c).matches("[\\u4E00-\\u9FA5]+")) {
                        pinyinName.append(PinyinHelper.toHanyuPinyinStringArray(c, defaultFormat)[0].charAt(0));
                    } else {
                        pinyinName.append(c);
                    }
                } else {
                    pinyinName.append(c);
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            System.out.println("汉字转换位汉语拼音首字母，英文字符不变异常:" + e.getMessage());
        }
        return pinyinName.toString();
    }

    public static void main(String[] args) {
        String str = "哈哈啊";
        System.out.println(convertToFirstUpper(str));
    }
}