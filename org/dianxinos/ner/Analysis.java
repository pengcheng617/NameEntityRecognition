package org.dianxinos.ner;

import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.BaseAnalysis;
import org.ansj.splitWord.analysis.NlpAnalysis;
import org.ansj.splitWord.analysis.ToAnalysis;

import java.util.ArrayList;
import java.util.List;


/**
 * 封装Ansj分词
 *
 * @author : pengcheng
 */

public class Analysis {

    /**
     * 短信分词
     *
     * @param message:                   短信
     * @param type:2-nlp分词；0-精准分词；1-基本分词
     * @return 标记词性的词语列表
     */
    public ArrayList<String> messageAnalysis(String message, int type) {
        ArrayList<String> token = new ArrayList<String>();
        List<Term> terms;
        if (type == 0) {
            terms = messageToAnalysis(message);
        } else if (type == 1) {
            terms = messageBaseAnalysis(message);
        } else if (type == 2) {
            terms = messageNlpAnalysis(message);
        } else {
            terms = messageToAnalysis(message);
        }
        for (Term term : terms) {
            token.add(term.getName() + " " + term.getNatrue().natureStr);
            //token.add(term.getName() + " " + term.getNatureStr());
        }
        return token;
    }

    /**
     * 精准分词
     *
     * @param message:短信
     * @return 分词列表
     */
    private List<Term> messageToAnalysis(String message) {
        return ToAnalysis.parse(message);
    }

    /**
     * 基本分词
     */
    private List<Term> messageBaseAnalysis(String message) {
        return BaseAnalysis.parse(message);
    }

    /**
     * NLP分词--识别出未登陆词，语法实体抽取
     */
    private List<Term> messageNlpAnalysis(String message) {
        return NlpAnalysis.parse(message);
    }

    /**
     * 短信分词后的词性列表
     *
     * @param message:短信
     * @param type:分词类型
     * @return 词性列表
     */
    public ArrayList<String> natureAnalysis(String message, int type,
                                            ArrayList<String> nature) {
        ArrayList<String> segmessage = messageAnalysis(message, type);
        for (String item : segmessage) {
            nature.add(item.split(" ")[1]);
        }
        return segmessage;
    }
}
