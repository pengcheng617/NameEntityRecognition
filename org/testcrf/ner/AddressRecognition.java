package org.dianxinos.ner;

import org.chasen.crfpp.Tagger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * 识别地址标记、专有结构标记
 *
 * @author : pengcheng
 */

public class AddressRecognition {

    private final Set<String> set = new HashSet<String>(); //词性集合

    {
        this.set.add("NS");
        this.set.add("NT");
        this.set.add("B-NS");
        this.set.add("I-NS");
        this.set.add("O-NS");
        this.set.add("B-NT");
        this.set.add("I-NT");
        this.set.add("O-NT");
    }

    /**
     * Constructor
     */
    public AddressRecognition() {
    }

    /**
     * 返回标记串
     *
     * @param tagger:    crf模型
     * @param message：短信
     * @return 标记串
     */
    public ArrayList<String> bio2Address(Tagger tagger, ArrayList<String> message) {

        tagger.clear();
        for (String temp : message) {
            tagger.add(temp);
        }
        if (!tagger.parse())
            return null;
        ArrayList<String> NS = new ArrayList<String>();
        for (int i = 0; i < tagger.size(); ++i) {
            if (this.set.contains(tagger.y2(i))) {
                //NS.add(tagger.x(i,0)+tagger.y2(i));
                NS.add(tagger.x(i, 0));
            }
        }
        return NS;
    }

    /**
     * 返回NS标记的单词位置
     *
     * @param tagger:    crf模型
     * @param message:短信
     * @return 标记NS的单词位置列表
     */
    public ArrayList<Integer> bio2NSSequence(Tagger tagger, ArrayList<String> message) {

        tagger.clear();
        for (String temp : message) {
            tagger.add(temp);
        }
        if (!tagger.parse()) return null;
        ArrayList<Integer> nssequence = new ArrayList<Integer>();
        for (int i = 0; i < tagger.size(); ++i) {
            if (tagger.y2(i).equals("NS")) {
                nssequence.add(i);
            }
        }
        return nssequence;
    }

    /**
     * 返回地址标记、专有结构标记的单词位置
     *
     * @param tagger:crf模型
     * @param message:短信
     * @return 地址标记、专有结构标记的单词位置序列
     */
    public ArrayList<Integer> bio2Sequence(Tagger tagger, ArrayList<String> message) {

        tagger.clear();
        for (String temp : message) {
            tagger.add(temp);
        }
        if (!tagger.parse())
            return null;
        ArrayList<Integer> sequence = new ArrayList<Integer>();
        for (int i = 0; i < tagger.size(); ++i) {
            if (this.set.contains(tagger.y2(i)))
                sequence.add(i);
        }
        return sequence;
    }

    /**
     * @param tagger:crf模型
     * @param message:短信
     * @param ns:地址和机构标记串
     * @param org：机构标记串
     * @param nssequence：地址标记位置
     * @param sequence：地址和机构标记位置
     * @param orgsequence：机构标记位置
     */
    public void BIO(Tagger tagger, ArrayList<String> message, ArrayList<String> ns, ArrayList<String> org,
                    ArrayList<Integer> nssequence, ArrayList<Integer> sequence, ArrayList<Integer> orgsequence) {
        tagger.clear();
        for (String temp : message) {
            tagger.add(temp);
        }
        if (!tagger.parse())
            return;
        for (int i = 0; i < tagger.size(); ++i) {
            if (this.set.contains(tagger.y2(i))) {
                sequence.add(i);
            }
            if (tagger.y2(i).equals("NS")) {
                nssequence.add(i);
            }
            if (tagger.y2(i).contains("NT")) {
                orgsequence.add(i);
                org.add(tagger.x(i, 0));
            }
            if (this.set.contains(tagger.y2(i))) {
                //ns.add(tagger.x(i, 0) + tagger.y2(i));
                ns.add(tagger.x(i, 0));
            }
        }
        //return sequence;
    }
}
