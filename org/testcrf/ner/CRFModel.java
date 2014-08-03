package org.testcrf.ner;


import org.apache.commons.configuration.ConfigurationUtils;
import org.chasen.crfpp.Tagger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.inject.Singleton;

import java.io.File;
import java.net.URL;

/**
 * CRF模型
 * 加载dll和model
 * Created by pengcheng on 14-7-23.
 */
@Singleton
public class CRFModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(CRFModel.class);
    private Tagger tagger = null;

    static {
        String platform = System.getProperty("os.name").toUpperCase();
        File file = null;
        if (platform.contains("WIN")){
            /*try{
                System.load(ConfigurationUtils.locate("libcrfpp.dll").getFile());
                System.load(ConfigurationUtils.locate("crf.dll").getFile());
            }catch(UnsatisfiedLinkError e){
                LOGGER.error("Cannot load crfmodel dll. {}", e);
                //System.exit(1);
            }*/
            try {
                file = new File(CommonUtils.__CONF_DIR__,"libcrfpp.dll");
                if (!file.exists()) {
                    URL url = ConfigurationUtils.locate("libcrfpp.dll");
                    LOGGER.info("Load crfmodel dll from: " + url);
                    file = new File(url.getFile());
                } else {
                    LOGGER.info("Load crfmodel from: " + file.getAbsolutePath());
                }
                System.load(file.getAbsolutePath());
                System.load(file.getAbsolutePath().replaceAll("libcrfpp.dll", "crf.dll"));
            } catch (Exception e) {
                LOGGER.warn(e.toString(), e);
            }
            LOGGER.info("Load crfmodel dll success from : {}",file.getAbsolutePath());
        } else {
            try{
                file = new File(CommonUtils.__CONF_DIR__,"libCRFPP.so");
                if (!file.exists()) {
                    URL url = ConfigurationUtils.locate("libCRFPP.so");
                    LOGGER.info("Load libCRFPP.so from: " + url);
                    file = new File(url.getFile());
                } else {
                    LOGGER.info("Load libCRFPP.so from: " + file.getAbsolutePath());
                }
                //System.load(ConfigurationUtils.locate("libs" + File.separator + "dll" + File.separator + "libCRFPP.so").getFile());
            }catch (UnsatisfiedLinkError e){
                LOGGER.error("Cannot load libCRFPP.so : {}", e);
                //System.exit(1);
            }
        }
    }

    public CRFModel() {
        loadModel();
    }

    /*public static void loadDll() {
        String platform = System.getProperty("os.name").toUpperCase();
        System.out.println(System.getProperty("sun.arch.data.model"));
        String dllname = "CRFPP";
        if (platform.contains("WINDOWS")) {
            dllname = "crf";
            //dllname = "libs" + File.separator + "dll" + File.separator + "crf";
            //System.out.println(dllname);
        } else if (platform.contains("LINUX")) {
            dllname = "CRFPP";
        }
        try {
            //注意添加环境变量
            //loadLibrary的bug ???
            // 不能用绝对路径，不支持File.separator，但是路径中可以包含斜杠
            System.loadLibrary(dllname);
        } catch (UnsatisfiedLinkError e) {
            System.err.println("Cannot load the example native code.\nMake sure your LD_LIBRARY_PATH contains \'.\'\n" + e);
            System.exit(1);
        }
    }*/

    public void loadModel() {
        if(null != tagger){
            return;
        }
        String param = "";
        File file = null;
        try {
            file = new File(CommonUtils.__CONF_DIR__,"crfmodel.db");
            if (!file.exists()) {
                URL url = ConfigurationUtils.locate("crfmodel.db");
                LOGGER.info("Load crfmodel from: " + url);
                file = new File(url.getFile());
            } else {
                LOGGER.info("Load crfmodel from: " + file.getAbsolutePath());
            }
            param = "-m " + file.getAbsolutePath() + " -v 3 -n2";
            //System.out.println(param);
        } catch (Exception e) {
            LOGGER.warn(e.toString(), e);
        }
        tagger = new Tagger(param);
        LOGGER.info("Load crfmodel success from: " + file.getAbsolutePath());
        tagger.clear();

    }

    public Tagger getTagger() {
        return this.tagger;
    }

    public void setTagger(Tagger tagger) {
        this.tagger = tagger;
    }

}
