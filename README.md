NameEntityRecognition
=====================
Extract address and organization
��
exlink.txt : RuleFilter�л�ƴ��һЩ����ַ���ôʵ����ڷ�ֹ�ǵ�ַ���ﱻƴ�ӳɵ�ַ
exanly.txt : �ôʵ������ų��ִʴ����Ĵ���Ʃ�磬"֮����������"���������ʱ��ᱻֱ�Ӵ��Ա�עΪ��ַ����

Address extract 
=====================


Nerrecongnition ������ȡ�����еĵ�ַ��ʱ�䡢����
����CRFģ�ͳ�ȡ��ַ�ͻ������ƣ���ԴCRF++ѵ��ģ�͡���ǵ�ַ
��������ƥ��ʱ�������
����enable.nerΪtrue��Ĭ��Ϊfalse


Nerrecognition.getNer���ص�ַ��ʱ�䡢����
Nerrecognition.getNerIndex���ص�ַ��ʱ�䡢�����ڶ����е���ʼλ�úͳ���

---------

��Դ��˵��
---------
libcrfpp.dll : crfģ��API��װ��C++
crf.dll ��ͨ��JNI����,������libcrfpp.dll,JNI��ͨ��Swig�������ɣ�dllͨ��Visual stdio��������
libCRFPP.so ��Linuxϵͳ��ʹ�õ�ģ��
----------
��������
---------
CRFģ�ͽ���[1]
CRFģ��ѵ����ʹ��˵��[2]
CRF++ JNI����[3]
CRF++ ����DLL[3]

---------

[1]: http://crfpp.googlecode.com/svn/trunk/doc/index.html
[2]: http://www.cnblogs.com/pangxiaodong/archive/2011/11/21/2256264.html
[3]: http://www.nilday.com/category/%E5%91%BD%E5%90%8D%E5%AE%9E%E4%BD%93%E8%AF%86%E5%88%AB/


��װʹ��˵��
---------
����antispam����
��libcrfdll��crf.dll��crfmodel.db����resourcesĿ¼�¼���
�޸�spam.properties

Classes
---------

**Nerrecognition** : ʶ���ַ��ʱ�䡢���ڣ�
**AddreeRecognition**��ͨ��CRFģ��ʶ���ַ����
**CRFModel**: ����CRF model��dll��so����
**RuleFilter**: ͨ�����һЩ����ʶ�𳣵�ַ����ͨ��CRFģ��ʶ������ĵ�ַ�ֶν϶̣�1��ʶ���ַ���桰**�ֵ�������******·**������******�š�֮��ĳ���ַ��
**Analysis**: ANSJ�ִʣ���Ӵ��Ա�ע


----------


ִ������
---------
����-->�ִ�-->���Ա�ע-->CRFģ��-->�̵�ַ��-->����ƥ�䳤��ַ��-->���


���
