package com.example.administrator.edittextutils;

import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.EditText;

import java.util.Arrays;
import java.util.List;

/**
 * 限制输入内容
 *
 * 在初始化EditText时，调用InputTxtFilter的inputFilter方法，传入输入长度限制、输入内容的类型限制等即可，eg：

 InputTxtFilter.inputFilter(this, mInputEditTxt, InputTxtFilter.INPUT_TYPE_EN, 5);
 *
 * Created by Administrator on 2016-08-03.
 */
public class InputTxtFilter {

    public static final int INPUT_TYPE_EN = 0x01;

    public static final int INPUT_TYPE_CH = 0x02;

    private static final String[] SPELL = new String[]{
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
            "ā", "á", "ǎ", "à", "ō", "ó", "ǒ", "ò", "ē", "é", "ě", "è", "ī", "í", "ǐ", "ì", "ū", "ú", "ǔ", "ù", "ǖ", "ǘ", "ǚ", "ǜ", "ü"
    };

    private static char[] chineseParam = new char[]{
            '」', '，', '。', '？', '…', '：', '～', '【', '＃', '、', '％', '＊', '＆', '＄', '（', '‘', '’', '“', '”', '『', '〔', '｛', '【'
            , '￥', '￡', '‖', '〖', '《', '「', '》', '〗', '】', '｝', '〕', '』', '”', '）', '！', '；', '—'
    };

    public InputTxtFilter() {
    }

    public static void inputFilter(final Context context, final EditText editText, final int type, final int inputLimit) {
        InputFilter[] filters = new InputFilter[0];
        filters[0] = new InputFilter.LengthFilter(inputLimit) {
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                boolean isRightCharater = false;

                if (type == INPUT_TYPE_EN) {
                    isRightCharater = isLetter(source.toString());
                } else if (type == INPUT_TYPE_CH) {
                    isRightCharater = isChineseWord(source.toString());
                }

                if (!isRightCharater || dest.toString().length() >= inputLimit) {
                    return "";
                }
                return source;
            }
        };
        editText.setFilters(filters);
    }

    /**
     * 检查String是否全是中文
     */
    public static boolean isChineseWord(String name) {
        boolean res = true;

        char[] cTemp = name.toCharArray();

        for (int i = 0; i < name.length(); i++) {
            if (!isChinese(cTemp[i])) {
                res = false;
                break;
            }
        }

        return res;
    }

    /**
     * 是否为英文字母
     */
    public static boolean isLetter(String inputStr) {
        char[] inputArray = inputStr.toCharArray();
        List<String> spellList = Arrays.asList(SPELL);

        for (char input : inputArray) {
            if (!spellList.contains(input + "")) {
                return false;
            }
        }
        return true;
    }


    /**
     * 判读输入汉字
     */

    public static boolean isChinese(char c) {
        for (char param : chineseParam) {
            if (param == c) {
                return false;
            }
        }

        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);

        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }
}
