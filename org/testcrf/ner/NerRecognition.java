package org.testcrf.ner;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.configuration.ConfigurationUtils;
import org.chasen.crfpp.Tagger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.inject.Singleton;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 命名实体识别
 * 返回地址、日期、电话号码等
 * Created by pengcheng on 14-7-23.
 */
@Singleton
public class NerRecognition {

    private static final Logger LOGGER = LoggerFactory.getLogger(NerRecognition.class);

    private CRFModel crfModel;
    private RuleFilter ruleFilter;
    private Analysis analysis;
    private AddressRecognition addressRecognition;
    private Tagger tagger;
    private ArrayList<String> address = null;
    private ArrayList<String> organization = null;
    private ArrayList<String> date = null;
    private ArrayList<String> phone = null;
    private ArrayList<String> addressindex = null;
    private ArrayList<String> dateindex = null;
    private ArrayList<String> phoneindex = null;


    public NerRecognition() {
        String exlinkpath = "";
        try {
            File file = new File(CommonUtils.__CONF_DIR__,"exlink.txt");
            if (!file.exists()) {
                URL url = ConfigurationUtils.locate("exlink.txt");
                LOGGER.info("Load exlink from: " + url);
                file = new File(url.getFile());
            } else {
                LOGGER.info("Load exlink from: " + file.getAbsolutePath());
            }
            exlinkpath = file.getAbsolutePath();
        } catch (Exception e) {
            LOGGER.warn(e.toString(), e);
        }
        String exanlypath = "";
        try {
            File file = new File(CommonUtils.__CONF_DIR__,"exanly.txt");
            if (!file.exists()) {
                URL url = ConfigurationUtils.locate("exanly.txt");
                LOGGER.info("Load exanly from: " + url);
                file = new File(url.getFile());
            } else {
                LOGGER.info("Load exanly from: " + file.getAbsolutePath());
            }
            exanlypath = file.getAbsolutePath();
        } catch (Exception e) {
            LOGGER.warn(e.toString(), e);
        }
        //exlinkpath = ConfigurationUtils.locate(exlinkpath).getFile();
        //exanlypath = ConfigurationUtils.locate(exanlypath).getFile();
        this.ruleFilter = new RuleFilter(exlinkpath, exanlypath);
        this.analysis = new Analysis();
        this.addressRecognition = new AddressRecognition();
        this.crfModel = new CRFModel();
        this.tagger = this.crfModel.getTagger();
    }

    /*public void init() {
        String platform = System.getProperty("os.name").toUpperCase();
        String dllname = "CRFPP";
        if (platform.contains("WINDOWS")) {
            dllname = "crf";
        } else if (platform.contains("LINUX")) {
            dllname = "CRFPP";
        }
        try {
            //注意添加环境变量
            System.loadLibrary(dllname);
        } catch (UnsatisfiedLinkError e) {
            System.err.println("Cannot load the example native code.\nMake sure your LD_LIBRARY_PATH contains \'.\'\n" + e);
            System.exit(1);
        }
    }*/

    public ArrayList<String> getAddress(String body) {

        ArrayList<String> nature = new ArrayList<String>();
        //分词+词性标注
        ArrayList<String> message = analysis.natureAnalysis(body, 0, nature);
        //CRF模型标记识别
        ArrayList<String> ns = new ArrayList<String>();
        ArrayList<String> org = new ArrayList<String>();
        ArrayList<Integer> nsseqnum = new ArrayList<Integer>();
        ArrayList<Integer> seqnum = new ArrayList<Integer>();
        ArrayList<Integer> orgseqnum = new ArrayList<Integer>();
        addressRecognition.BIO(this.tagger, message, ns, org, nsseqnum, seqnum, orgseqnum);
        //连接长地址
        //System.out.println(message);
        //System.out.println(ns);
        ruleFilter.NSLink(message, ns, nsseqnum, nature, seqnum);
        this.address = ruleFilter.getAddress(ns, seqnum);
        return this.address;
    }

    public ArrayList<String> getOrganization(String body) {

        ArrayList<String> nature = new ArrayList<String>();
        //分词+词性标注
        ArrayList<String> message = analysis.natureAnalysis(body, 0, nature);
        //CRF模型标记识别
        ArrayList<String> ns = new ArrayList<String>();
        ArrayList<String> org = new ArrayList<String>();
        ArrayList<Integer> nsseqnum = new ArrayList<Integer>();
        ArrayList<Integer> seqnum = new ArrayList<Integer>();
        ArrayList<Integer> orgseqnum = new ArrayList<Integer>();
        addressRecognition.BIO(this.tagger, message, ns, org, nsseqnum, seqnum, orgseqnum);
        this.organization = ruleFilter.getOrganization(org, orgseqnum);
        return organization;


    }

    public ArrayList<String> getAddressIndex(String body) {
        ArrayList<String> address = this.getAddress(body);
        //this.addressindex = ruleFilter.getAddressIndex(body, address);
        this.addressindex = ruleFilter.getAddressIndex2(body, address);
        return this.addressindex;
    }

    public ArrayList<String> getPhoneNumberIndex(String body) {

        Pattern phone = Pattern.compile("[^\\d](((((\\+)?86)?(-)?((13[0-9])|147|(15[^4,\\D])|(18[0,5-9])))(-)?\\d{4}(-)?\\d{4})|(((010)|(02[0-57-9])|0[\\d]{3})(-)?\\d{7,8}))[^\\d]");
        Matcher m = phone.matcher("[" + body + "]");
        ArrayList<String> phoneindex = new ArrayList<String>();
        while (m.find()) {
            phoneindex.add(String.valueOf(m.start()) + '-' + String.valueOf(m.end() - m.start() - 2));
        }
        this.phoneindex = phoneindex;
        return this.phoneindex;
    }

    public ArrayList<String> getPhoneNumber(String body) {

        Pattern phone = Pattern.compile("[^\\d](((((\\+)?86)?(-)?((13[0-9])|147|(15[^4,\\D])|(18[0,5-9])))(-)?\\d{4}(-)?\\d{4})|(((010)|(02[0-57-9])|0[\\d]{3})(-)?\\d{7,8}))[^\\d]");
        Matcher m = phone.matcher("[" + body + "]");
        ArrayList<String> phonenumber = new ArrayList<String>();
        while (m.find()) {
            phonenumber.add(m.group(1));
        }
        this.phone = phonenumber;
        return this.phone;
    }

    public ArrayList<String> getDate(String body) {
        //Pattern datepattern = Pattern.compile("((((19|20)\\\\d{2})(-|.)(0?(1|[3-9])|1[012])(-|.)(0?[1-9]|[12]\\\\d|30))|(((19|20)\\\\d{2})(-|.)(0?[13578]|1[02])(-|.)31)|(((19|20)\\\\d{2})(-|.)0?2(-|.)(0?[1-9]|1\\\\d|2[0-8]))|((((19|20)([13579][26]|[2468][048]|0[48]))|(2000))(-|.)0?2(-|.)29))[\\d]");
        //Pattern datepattern = Pattern.compile("((((19|20)\\d{2})(-|.)(0?(1|[3-9])|1[012])(-|.)(0?[1-9]|[12]\\d|30))|(((19|20)\\d{2})(-|.)(0?[13578]|1[02])(-|.)31)|(((19|20)\\d{2})(-|.)0?2(-|.)(0?[1-9]|1\\d|2[0-8]))|((((19|20)([13579][26]|[2468][048]|0[48]))|(2000))(-|.)0?2(-|.)29))[\\d]{1}");
        //Pattern datepattern = Pattern.compile("\\d{4}[-|.|_|年]\\d{1,2}[-|.|_|月]\\d{1,2}([日]{0,})");
        //Pattern datepattern = Pattern.compile("[^\\d]((19|20)\\d{2}([-|.|_|年])?\\d{1,2}([-|.|_|月])?\\d{1,2}([日]{0,}))[^\\d]");
        Pattern datepattern = Pattern.compile("[^\\d]((19|20)\\d{2}([-|.|_|年])([1-9]|(([0|1][\\d]))|([1][0-2]))([-|.|_|月])([1-9]|([0-3][\\d]))([日]?))[^\\d]");
        Matcher m = datepattern.matcher("[" + body + "]");
        ArrayList<String> date = new ArrayList<String>();
        while (m.find()) {
            date.add(m.group(1));
        }
        this.date = date;
        return this.date;
    }

    public ArrayList<String> getDateIndex(String body) {
        //Pattern datepattern = Pattern.compile("((((19|20)\\\\d{2})(-|.)(0?(1|[3-9])|1[012])(-|.)(0?[1-9]|[12]\\\\d|30))|(((19|20)\\\\d{2})(-|.)(0?[13578]|1[02])(-|.)31)|(((19|20)\\\\d{2})(-|.)0?2(-|.)(0?[1-9]|1\\\\d|2[0-8]))|((((19|20)([13579][26]|[2468][048]|0[48]))|(2000))(-|.)0?2(-|.)29))[\\d]");
        //Pattern datepattern = Pattern.compile("((((19|20)\\d{2})(-|.)(0?(1|[3-9])|1[012])(-|.)(0?[1-9]|[12]\\d|30))|(((19|20)\\d{2})(-|.)(0?[13578]|1[02])(-|.)31)|(((19|20)\\d{2})(-|.)0?2(-|.)(0?[1-9]|1\\d|2[0-8]))|((((19|20)([13579][26]|[2468][048]|0[48]))|(2000))(-|.)0?2(-|.)29))[\\d]{1}");
        //Pattern datepattern = Pattern.compile("\\d{4}[-|.|_|年]\\d{1,2}[-|.|_|月]\\d{1,2}([日]{0,})");
        Pattern datepattern = Pattern.compile("[^\\d]((19|20)\\d{2}([-|.|_|年])([1-9]|(([0|1][\\d]))|([1][0-2]))([-|.|_|月])([1-9]|([0-3][\\d]))([日]?))[^\\d]");
        Matcher m = datepattern.matcher("[" + body + "]");
        ArrayList<String> dateindex = new ArrayList<String>();
        while (m.find()) {
            dateindex.add(String.valueOf(m.start()) + '-' + String.valueOf(m.end() - m.start() - 2));
        }
        this.dateindex = dateindex;
        return this.dateindex;

    }


    public String getJsonNer(String body) {
        String json = "";
        if(null == body){
            return json;
        }
        ArrayList<String> address = this.getAddress(body);
        ArrayList<String> date = this.getDate(body);
        ArrayList<String> phone = this.getPhoneNumber(body);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("addr", address);
        result.put("date", date);
        result.put("phone", phone);
        Map<String, Object> ner = new HashMap<String, Object>();
        ner.put("ner", result);

        try {
            json = CommonUtils.JACKSON_OBJECT_MAPPER.writeValueAsString(ner);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    public String getJsonNerIndex(String body) {
        String json = "";
        if(null == body){
            return json;
        }
        ArrayList<String> addressindex = getAddressIndex(body);
        ArrayList<String> dateindex = getDateIndex(body);
        ArrayList<String> phoneindex = getPhoneNumberIndex(body);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("addrindex", addressindex);
        result.put("dateindex", dateindex);
        result.put("phoneindex", phoneindex);
        Map<String, Object> ner = new HashMap<String, Object>();
        ner.put("ner", result);
        try {
            json = CommonUtils.JACKSON_OBJECT_MAPPER.writeValueAsString(ner);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    public Map<String, Object> getNer(String body) {
        Map<String, Object> ner = new HashMap<String, Object>();
        if(null == body){
            Map<String, Object> result = new HashMap<String, Object>();
            ArrayList<String> temp = new ArrayList<String>();
            result.put("addr", temp);
            result.put("date", temp);
            result.put("phone", temp);
            ner.put("ner", result);
            return ner;
        }
        ArrayList<String> address = this.getAddress(body);
        ArrayList<String> date = this.getDate(body);
        ArrayList<String> phone = this.getPhoneNumber(body);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("addr", address);
        result.put("date", date);
        result.put("phone", phone);
        ner.put("ner", result);
        return ner;
    }

    public Map<String, Object> getNerIndex(String body) {
        Map<String, Object> nerindex = new HashMap<String, Object>();
        if(null == body){
            Map<String, Object> result = new HashMap<String, Object>();
            ArrayList<String> temp = new ArrayList<String>();
            result.put("addrindex", temp);
            result.put("dateindex", temp);
            result.put("phoneindex", temp);
            nerindex.put("ner",result);
            return nerindex;
        }
        ArrayList<String> addressindex = getAddressIndex(body);
        ArrayList<String> dateindex = getDateIndex(body);
        ArrayList<String> phoneindex = getPhoneNumberIndex(body);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("addrindex", addressindex);
        result.put("dateindex", dateindex);
        result.put("phoneindex", phoneindex);
        nerindex.put("ner", result);
        return nerindex;
    }
}
