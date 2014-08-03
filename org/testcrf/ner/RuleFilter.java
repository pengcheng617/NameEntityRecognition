package org.dianxinos.ner;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 规则过滤短信
 *
 * @author pengcheng
 */
public class RuleFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RuleFilter.class);
    //如：中国 银行 信用卡 "微信"
    private final ArrayList<String> exlinkdic = new ArrayList<String>();//排除连接错误的词典
    //如：惠州市气象台21日22时28
    private final ArrayList<String> exanalyizedic = new ArrayList<String>();//排除长串分词错误的词典

    private final Set<String> determinerset = new HashSet<String>();  //可识别为NS词性集合

    {
        this.determinerset.add("n");
        this.determinerset.add("a");
        this.determinerset.add("ng");
        this.determinerset.add("m");
        this.determinerset.add("nz");
        this.determinerset.add("f");
        this.determinerset.add("nw");
        this.determinerset.add("j");
        this.determinerset.add("nr");
        this.determinerset.add("q");
        this.determinerset.add("s");
        this.determinerset.add("mq");
        //determinerset.add("t");
        //determinerset.add("v");
        //determinerset.add("c");
        //determinerset.add("ag");
    }

    /**
     * Constructor
     */
    public RuleFilter(ArrayList<String> linkdic, ArrayList<String> anlydic) {
        for (String str : linkdic) {
            this.exlinkdic.add(str);
        }
        for (String str : anlydic) {
            this.exanalyizedic.add(str);
        }

    }

    /**
     * 文件中读入排除词，初始化
     *
     * @param exanlyfilename:排除分词错误的词典
     * @param exlinkfilename:排除拼接错误的词典
     */
    public RuleFilter(String exlinkfilename, String exanlyfilename) {

        File file1 = new File(exlinkfilename);
        File file2 = new File(exanlyfilename);
        try {
            BufferedReader reader1 = new BufferedReader(new InputStreamReader(new FileInputStream(file1), "utf-8"));
            String exlinkwords;
            while ((exlinkwords = reader1.readLine()) != null) {
                if (exlinkwords.length() > 0 && !exlinkwords.substring(0, 1).equals("#")) {
                    this.exlinkdic.add(exlinkwords);
                }
            }
            LOGGER.info("Load exlink.dic success.");
            BufferedReader reader2 = new BufferedReader(new InputStreamReader(new FileInputStream(file2), "utf-8"));
            String exanlywords;
            while ((exanlywords = reader2.readLine()) != null) {
                if (exanlywords.length() > 0 && !exanlywords.substring(0, 1).equals("#")) {
                    this.exanalyizedic.add(exanlywords);
                }
            }
            LOGGER.info("Load exanly.dic success.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 分词后非NS（determinerset）词性，识别成NS
     *
     * @param message:分词后的短信
     * @param ns:标记为地址和机构名称命名实体的单词列表
     * @param nsseqnum:标记为地址的单词位置
     * @param nature：message分词后词性列表
     * @param seqnum:识别为NS,NT,B-NS,O-NS,T-NS,etc.词性位置，包含地址和机构名称
     */
    public void NSLink(ArrayList<String> message, ArrayList<String> ns,
                       ArrayList<Integer> nsseqnum, ArrayList<String> nature, ArrayList<Integer> seqnum) {
        for (int i = 0; i < nsseqnum.size(); i++) {
            int j = nsseqnum.get(i) + 1;
            String str = message.get(j - 1).split(" ")[0];
            int num = 0;
            Set<String> naturetype = new HashSet();
            boolean exflag = false;
            while (j < nature.size() - 1 && determinerset.contains(nature.get(j))) {
                if (this.exlinkdic.contains(message.get(j).split(" ")[0])) {
                    exflag = true;
                    //System.out.println(message.get(j).split(" ")[0]);
                    break;
                }
                str += message.get(j).split(" ")[0];
                naturetype.add(nature.get(j));
                j++;
                num++;
            }
            if (exflag) {
                break;
            }
            int index = seqnum.indexOf(nsseqnum.get(i));
            if (i + 1 < nsseqnum.size() && j == nsseqnum.get(i + 1))
                str += "|";
            //NS标记后带有nr标记的词，标记为地址串
            if (naturetype.contains("nr")) {
                ns.set(index, str);
            }
            //Ns标记后带有两种以上词性&&词长度超过2时标记为地址串
            else if (naturetype.size() > 1 && num > 2) {
                ns.set(index, str);
            }

        }
    }

    /**
     * 分词后的相邻地址连接操作
     *
     * @param address：分词词语列表
     * @param sequence：词语位置列表
     * @return 长串地址短语
     */
    public ArrayList<String> linkAddress(ArrayList<String> address, ArrayList<Integer> sequence) {
        ArrayList<String> linkaddr = new ArrayList<String>();
        //连接地址列表中相邻的词
        String str = address.get(0);
        for (int start = 0, end = 1; end < sequence.size(); ) {
            if (sequence.get(end) == sequence.get(start) + 1) {
                str += address.get(end);
                start++;
                end++;
            } else {
                linkaddr.add(str);
                start = end;
                end = start + 1;
                str = address.get(start);
            }
        }
        linkaddr.add(str);
        return linkaddr;
    }

    /**
     * 输出标记地址串
     *
     * @param ns:地址串
     * @param seqnum:短信中已标记的单词位置（NS、NT、etc）
     * @return 返回地址
     */
    public ArrayList<String> getAddress(ArrayList<String> ns, ArrayList<Integer> seqnum) {
        //输出地址
        ArrayList<String> address = new ArrayList<String>();
        if (ns.size() < 1)
            return address;
        ArrayList<String> linkaddr = linkAddress(ns, seqnum);
        String temp = "";
        boolean exflag = false;
        for (String additem : linkaddr) {
            for (String words : this.exanalyizedic) {
                boolean var = Pattern.compile(words).matcher(additem).find();
                if (var) {
                    exflag = true;
                    break;
                }
            }
            if (exflag) {
                continue;
            }
            if (!additem.substring(additem.length() - 1).equals("|")) {
                temp += additem;
                //address.add(temp);
                if (temp.length() > 4) {
                    address.add(temp);
                }
                temp = "";
            } else {
                temp += additem.substring(0, additem.length() - 1);
            }
            exflag = false;
        }
        return address;
    }

    /**
     * @param org:地址和机构标记串
     * @param orgsequence：机构标记位置
     * @return: 机构名称
     */
    public ArrayList<String> getOrganization(ArrayList<String> org, ArrayList<Integer> orgsequence) {

        //返回机构名称
        ArrayList<String> address = new ArrayList<String>();
        if (org.size() < 1 || orgsequence.size() < 1)
            return address;
        //连接机构列表中相邻的词
        ArrayList<String> linkorg = linkAddress(org, orgsequence);
        String temp = "";
        boolean exflag = false;
        for (String additem : linkorg) {
            for (String words : this.exanalyizedic) {
                boolean var = Pattern.compile(words).matcher(additem).find();
                if (var) {
                    exflag = true;
                    break;
                }
            }
            if (exflag) {
                continue;
            }
            temp += additem;
            if (temp.length() > 4) {
                address.add(temp);
            }
            temp = "";
            exflag = false;
        }
        return address;
    }

    /**
     * @param message:原始短信
     * @param address：地址串
     * @return: 地址串位置列表
     */
    public ArrayList<String> getAddressIndex(String message, ArrayList<String> address) {
        ArrayList<String> addressindex = new ArrayList<String>();
        if (address.size() < 1) {
            return addressindex;
        }
        int start = 0;
        for (String words : address) {
            int index = message.indexOf(words, start);
            start = index + words.length();
            addressindex.add(String.valueOf(index) + "-" + String.valueOf(words.length()));
        }
        return addressindex;
    }

    public ArrayList<String> getAddressIndex2(String message, ArrayList<String> address) {
        //Map<String, Object> test = new HashMap<String, Object>();

        ArrayList<String> addressindex = new ArrayList<String>();
        if (address.size() < 1) {
            return addressindex;
        }
        int start = 0;
        int index = 0, len = 0;
        for (String words : address) {
            int temp = message.indexOf(words, start);
            start = temp + words.length();
            if (index + len == temp) {
                addressindex.remove(String.valueOf(index) + "-" + String.valueOf(len));
                len += words.length();
                addressindex.add(String.valueOf(index) + "-" + String.valueOf(len));

            } else {
                index = temp;
                len = words.length();
                addressindex.add(String.valueOf(index) + "-" + String.valueOf(len));
            }
        }
        return addressindex;
    }
}
