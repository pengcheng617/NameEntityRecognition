Rest������־��ӡ�淶��
=====================


��־������¼�û�������ϵͳ����״̬�ȣ���һ��ϵͳ����Ҫ��ɲ��֡�Ȼ��������־����ϵͳ���Ĺ��ܣ�ͨ������²������Ŷӵ����ӡ��ڳ���������Ҫͨ����־����λʱ���ŷ�����־�����ںܶ����⡣��־��¼�ĺû�ֱ�ӹ�ϵ��ϵͳ��������ʱ��λ���ٶȣ�ͬʱ����ͨ������־�Ĺ۲�ͷ�������ǰ����ϵͳ���ܵķ��գ����������¹ʵķ�����

----------


��־����
---------

������־����ķ��࣬��StackOverflow����һ�����������й����ۣ�����Ҳο���

**FATAL** �� ��ʾ��Ҫ�����������ϵͳ�����󡣵��ô�����ʱ����ʾ�����Ѿ�������ĳ�̶ֳȵĲ����ã�ϵͳ����Ա��Ҫ�������롣�����������ص���־������˸���־����������ã�������ּ������־�������֣������־Ҳʧȥ�����塣ͨ������£�һ�����̵�����������Ӧ��ֻ��¼һ��FATAL�������־�����ý��������޷��ָ��Ĵ�����˳�ʱ����Ȼ�����ĳ��ϵͳ����ϵͳ�����˲��ɻָ��Ĵ����Ǹ���ϵͳ�ĵ��÷�Ҳ���Լ���FATAL������־���Ա�ͨ����־��������ϵͳ����Ա�޸���
    
**ERROR** �� �ü���Ĵ���Ҳ��Ҫ���ϱ��������ǽ����̶�Ҫ����FATAL���𡣵�ERROR������ʱ���Ѿ�Ӱ�����û����������ʡ��Ӹ���������˵��ʵ����ERROR�����FATAL������û���Ӱ�����൱�ġ�FATAL�൱�ڷ����Ѿ����ˣ���ERROR�൱�ں������������ţ�Ȼ������ȴ�޷��ṩ�����ķ���ֻ�ܲ��ϵش�ӡERROR��־���ر���Ҫע����ǣ�ERROR��FATAL�����ڷ������Լ����쳣������Ҫ���ϵõ��˹����벢����ġ��������û��Լ������������������������ȵȣ��Ǿ��Բ�Ӧ�ü�ΪERROR��־�ģ�
    
**WARN** �� ����־��ʾϵͳ���ܳ������⣬Ҳ����û�У��������������Ĳ����ȡ�������ЩĿǰ�����Ǵ���Ȼ������ʱ����Ҳ���Ϊ����������Ҳ���Լ�ΪWARN��־������һ���洢ϵͳ�Ĵ���ʹ����������ֵ������ϵͳ��ĳ���û��Ĵ洢��������ȵȡ�����WARN�������־����Ȼ����Ҫϵͳ����Ա���ϴ���Ҳ����Ҫ��ʹ�鿴������ġ���˴��ּ������־Ҳ��Ӧ̫�࣬�ܲ���WARN�������־���;�����Ҫ��
    
**INFO** �� ������־��¼ϵͳ����������״̬������ĳ����ϵͳ�ĳ�ʼ����ĳ������ĳɹ�ִ�еȵȡ�ͨ���鿴INFO�������־�����Ժܿ�ض�ϵͳ�г��ֵ�WARN,ERROR,FATAL������ж�λ��INFO��־���˹��࣬ͨ������£�INFO�������־Ӧ�ò�����TRACE��־��10%��
    
**DEBUG or TRACE** �� ��������־����Ĺ淶Ӧ������Ŀ���Լ����壬�ü�����־����Ҫ�����Ƕ�ϵͳÿһ��������״̬���о�ȷ�ļ�¼��ͨ��������־�����Բ鿴ĳһ������ÿһ����ִ�й��̣�����׼ȷ��λ�Ǻ��ֲ��������ֲ���������˳������ĳ�ִ���ķ��������Ա�֤�ڲ����ִ��������£�Ҳ����ͨ��DEBUG����TRACE���������־�����������ϡ���Ҫע����ǣ�DEBUG��־Ҳ��Ҫ�淶��־��ʽ��Ӧ�ñ�֤���˼�¼��־�Ŀ�����Ա�Լ��⣬����������ά��������Ա��Ҳ����ͨ��DEBUG����TRACE����־����λ���⣻


----------


Logback������
---------

Logger��Ϊ��־�ļ�¼��������������Ӧ�ö�Ӧ��context�Ϻ���Ҫ���ڴ����־����Ҳ���Զ�����־���͡�����Appender��Ҫ����ָ����־�����Ŀ�ĵأ�Ŀ�ĵؿ����ǿ���̨���ļ���Զ���׽��ַ������� MySQL�� PostreSQL�� Oracle���������ݿ⡢ JMS��Զ��UNIX Syslog�ػ����̵ȡ�
    
Logger���Ա����伶�𡣼��������TRACE��DEBUG��INFO��WARN �� ERROR�������� ch.qos.logback.classic.Level�ࡣ��� loggerû�б����伶����ô�������б����伶����������������̳м���root logger Ĭ�ϼ����� DEBUG��


logger����
---------

**logger**��������ĳһ�������߾����ĳһ�������־��ӡ�����Լ�ָ��appender��logger����һ��name���ԣ�һ����ѡ��level��һ����ѡ��addtivity���ԡ�
    
**name**:����ָ���ܴ�loggerԼ����ĳһ�������߾����ĳһ���ࡣ
**level**:�������ô�ӡ���𣬴�Сд�޹أ�TRACE, DEBUG, INFO, WARN, ERROR, ALL �� OFF������һ������ֵINHERITED����ͬ���NULL������ǿ��ִ���ϼ��ļ������δ���ô����ԣ���ô��ǰloger����̳��ϼ��ļ���
**addtivity**:�Ƿ����ϼ�logger���ݴ�ӡ��Ϣ��Ĭ����true��logger���԰����������appender-refԪ�أ���ʶ���appender������ӵ����logger��

---------

root����
---------

rootҲ��loggerԪ�أ��������Ǹ�logger��ֻ��һ��level���ԣ�������Ϊ"root"��

```<level>```�������ô�ӡ����TRACE, DEBUG, INFO, WARN, ERROR, ALL �� OFF����������ΪINHERITED����ͬ���NULL��Ĭ����DEBUG��root���԰����������appender-refԪ�أ���ʶ���appender������ӵ����loger��

---------

appender����
---------
**appender** ��**configuration**���ӽڵ㣬�Ǹ���д��־���������������Ҫ����name��class��nameָ��appender���ƣ�classָ��appender��ȫ�޶�����
    
**ConsoleAppender**������־��ӵ�����̨
**FileAppender**������־��ӵ��ļ�
**RollingFileAppender**��������¼�ļ����Ƚ���־��¼��ָ���ļ���������ĳ������ʱ������־��¼�������ļ�������ѡ�������԰���TimeBasedRollingPolicy��FixedWindowRollingPolicy��SizeBasedTriggeringPolicy��


filter����
---------------

��������ִ��һ�����������з��ظ�ö��ֵ����DENY��NEUTRAL��ACCEPT����֮һ������DENY����־���������������پ�������������������NEUTRAL�������б�����¸������������Ŵ�����־������ACCEPT����־�ᱻ�����������پ���ʣ�������������������ӵ�Appender�У�ΪAppender���һ�������������󣬿�����������������־���й��ˡ�Appender�ж��������ʱ����������˳��ִ�С�
    
���õĹ�������
    **LevelFilter**�� �����������������־������й��ˣ�
    **ThresholdFilter**�� �ٽ�ֵ�����������˵�����ָ���ٽ�ֵ����־��
    **EvaluatorFilter**�� ��ֵ��������������������־�Ƿ����ָ��������


Rest������־��ӡ�淶
-----------

1. ��־��Ҫ��fileNamePattern������gzipѹ��


2. ��־�����ù�����¼�ļ���RollingFileAppender��������ʱ��������ԣ�TimeBasedRollingPolicy��

3. �����߷�����Ҫ������Ӧ��logger,������ӡlogger��������־����û,ͬʱע������additivity="false"��������־���ظ���ӡ

4. �ο�ʾ����Antispam logger��ʽ

```
    <?xml version="1.0" encoding="UTF-8"?>
    <configuration debug="true" scan="true">
     <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
            <Target>System.out</Target>
            <encoder>
                <Pattern>
                    %d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger [%file:%line] [%X{logid}]- %msg%n
                </Pattern>
            </encoder>
    </appender>
    
    
    <appender name="ACCESS" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>logs/access.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!-- daily rollover -->
            <fileNamePattern>
                logs/access.log.%d{yyyy-MM-dd}.gz
            </fileNamePattern>
            <maxHistory>2</maxHistory>
        </rollingPolicy>
        <encoder>
            <Pattern>
                [%X{logid}]- %msg%n
            </Pattern>
        </encoder>
    </appender>

    <appender name="ERR" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>logs/spam.err.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!-- daily rollover -->
            <fileNamePattern>
                logs/spam.err.log.%d{yyyy-MM-dd}.gz
            </fileNamePattern>
            <maxHistory>2</maxHistory>
        </rollingPolicy>
        <filter class="ch.qos.logback.classic.filter.ThresholdFilter">
            <level>WARN</level>
        </filter>
        <encoder>
            <Pattern>
                %d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger [%file:%line] [%X{logid}]- %msg%n
            </Pattern>
        </encoder>
    </appender>
    <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>logs/spam.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!-- daily rollover -->
            <fileNamePattern>
                logs/spam.log.%d{yyyy-MM-dd}.gz
            </fileNamePattern>
            <maxHistory>2</maxHistory>
        </rollingPolicy>
        <encoder>
            <Pattern>
                %d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger [%file:%line] [%X{logid}]- %msg%n
            </Pattern>
        </encoder>
    </appender>

    <appender name="ANTISPAM" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>logs/antispam.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!-- daily rollover -->
            <fileNamePattern>
                logs/antispam.log.%d{yyyy-MM-dd}.gz
            </fileNamePattern>
            <maxHistory>120</maxHistory>
        </rollingPolicy>
        <filter class="ch.qos.logback.classic.filter.ThresholdFilter">
            <level>INFO</level>
        </filter>
        <encoder>
            <Pattern>
                %d{yyyy-MM-dd HH:mm:ss} %msg%n
            </Pattern>
        </encoder>
    </appender>

    <logger name="org.restlet">
        <level value="WARN"/>
    </logger>
    <logger name="org.eclipse.jetty">
        <level value="WARN"/>
    </logger>

    <logger name="LogService" additivity="false">
        <level value="INFO"/>
        <appender-ref ref="ACCESS"/>
    </logger>
    <logger name="org.apache.ibatis">
        <level value="INFO"/>
    </logger>
    <logger name="java.sql">
        <level value="INFO"/>
    </logger>
    <logger name="java.sql.Connection">
        <level value="INFO"/>
    </logger>
    <logger name="spam.ibatis.mapper">
        <level value="INFO"/>
    </logger>

    <logger name="com.dianxinos.antispam.log" additivity="false">
        <appender-ref ref="ANTISPAM"/>
        <level value="INFO"/>
    </logger>
    
    <root>
        <level value="${log.level}"/>
        <appender-ref ref="FILE"/>
        <appender-ref ref="ERR"/>
        <appender-ref ref="STDOUT"/>
    </root>
</configuration>
```

�ο�����
----------
1. http://stackoverflow.com/questions/2031163/when-to-use-log-level-warn-vs-error
2. http://blog.csdn.net/haidage/article/details/6794509