Address extract 
=====================


    Nerrecongnition 用来抽取短信中的地址、时间、日期
    
    采用CRF模型抽取地址和机构名称；开源CRF++训练模型、标记地址
    
    采用正则匹配时间和日期

----------


Classes
---------

    **Nerrecognition** : 识别地址、时间、日期；
    
    **AddreeRecognition**：通过CRF模型识别地址串；
    
    **CRFModel**: 加载CRF model，dll，so文件等
    
    **RuleFilter**: 通过添加规则，识别常地址串，通过CRF模型识别出来的地址字段较短，无法识别地址后面“******街道”、“******路**”、“******号”之类的常地址串
    
    **Analysis**: ANSJ分词，添加词性标注


----------


执行流程
---------
    短信-->分词-->词性标注-->CRF模型-->短地址串-->规则匹配长地址串-->输出


相关文档和链接
---------

	[CRF模型介绍][1]

	[CRF模型训练和使用说明][2]

	[CRF++ JNI调用][3]

	[CRF++ 编译DLL][3]

---------

安装使用说明
---------

    将libcrfdll、crf.dll、crfmodel.db、libCRFPP.so放在resources目录下即可
    修改spam.properties文件中enable.ner为true，默认为false
    其他项目使用时，直接引入antispam包即可



    Nerrecognition.getNer返回地址、时间、日期
    Nerrecognition.getNerIndex返回地址、时间、日期在短信中的起始位置和长度
    Nerrecognition.getOrganization返回 ArrayList<String> 类型的机构名称

---------

资源文件说明
---------
引用文件说明

    libcrfpp.dll : crf模型API封装，C++
    crf.dll ：通过JNI调用,依赖于libcrfpp.dll,
             JNI文件通过Swig工具生成，dll通过Visual stdio编译生成
    libCRFPP.so ：Linux系统中使用的模型文件
    crfmodel.db ：训练后的crf模型

修正字典：exlink.txt,exanly.txt

    exlink.txt : RuleFilter中会拼接一些长地址，该词典用于防止非地址词语被拼接成地址
    exanly.txt : 该词典用于排除分词带来的错误，譬如，"之日起五日内"这个词语有时候会被直接词性标注为地址名称

	
	[1]: http://crfpp.googlecode.com/svn/trunk/doc/index.html
	
	[2]: http://www.cnblogs.com/pangxiaodong/archive/2011/11/21/2256264.html
	
	[3]: http://www.nilday.com/category/%E5%91%BD%E5%90%8D%E5%AE%9E%E4%BD%93%E8%AF%86%E5%88%AB/
