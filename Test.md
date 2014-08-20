Rest服务日志打印规范：
=====================


日志用来记录用户操作、系统运行状态等，是一个系统的重要组成部分。然而由于日志并非系统核心功能，通常情况下并不受团队的重视。在出现问题需要通过日志来定位时，才发现日志还存在很多问题。日志记录的好坏直接关系到系统出现问题时定位的速度，同时可以通过对日志的观察和分析，提前发现系统可能的风险，避免线上事故的发生。

----------


日志级别
---------

关于日志级别的分类，在StackOverflow上有一个讨论贴进行过讨论，供大家参考：

**FATAL** — 表示需要立即被处理的系统级错误。当该错误发生时，表示服务已经出现了某种程度的不可用，系统管理员需要立即介入。这属于最严重的日志级别，因此该日志级别必须慎用，如果这种级别的日志经常出现，则该日志也失去了意义。通常情况下，一个进程的生命周期中应该只记录一次FATAL级别的日志，即该进程遇到无法恢复的错误而退出时。当然，如果某个系统的子系统遇到了不可恢复的错误，那该子系统的调用方也可以记入FATAL级别日志，以便通过日志报警提醒系统管理员修复；
    
**ERROR** — 该级别的错误也需要马上被处理，但是紧急程度要低于FATAL级别。当ERROR错误发生时，已经影响了用户的正常访问。从该意义上来说，实际上ERROR错误和FATAL错误对用户的影响是相当的。FATAL相当于服务已经挂了，而ERROR相当于好死不如赖活着，然而活着却无法提供正常的服务，只能不断地打印ERROR日志。特别需要注意的是，ERROR和FATAL都属于服务器自己的异常，是需要马上得到人工介入并处理的。而对于用户自己操作不当，如请求参数错误等等，是绝对不应该记为ERROR日志的；
    
**WARN** — 该日志表示系统可能出现问题，也可能没有，这种情况如网络的波动等。对于那些目前还不是错误，然而不及时处理也会变为错误的情况，也可以记为WARN日志，例如一个存储系统的磁盘使用量超过阀值，或者系统中某个用户的存储配额快用完等等。对于WARN级别的日志，虽然不需要系统管理员马上处理，也是需要即使查看并处理的。因此此种级别的日志也不应太多，能不打WARN级别的日志，就尽量不要打；
    
**INFO** — 该种日志记录系统的正常运行状态，例如某个子系统的初始化，某个请求的成功执行等等。通过查看INFO级别的日志，可以很快地对系统中出现的WARN,ERROR,FATAL错误进行定位。INFO日志不宜过多，通常情况下，INFO级别的日志应该不大于TRACE日志的10%；
    
**DEBUG or TRACE** — 这两种日志具体的规范应该由项目组自己定义，该级别日志的主要作用是对系统每一步的运行状态进行精确的记录。通过该种日志，可以查看某一个操作每一步的执行过程，可以准确定位是何种操作，何种参数，何种顺序导致了某种错误的发生。可以保证在不重现错误的情况下，也可以通过DEBUG（或TRACE）级别的日志对问题进行诊断。需要注意的是，DEBUG日志也需要规范日志格式，应该保证除了记录日志的开发人员自己外，其他的如运维，测试人员等也可以通过DEBUG（或TRACE）日志来定位问题；


----------


Logback的配置
---------

Logger作为日志的记录器，把它关联到应用对应的context上后，主要用于存放日志对象，也可以定义日志类型、级别。Appender主要用于指定日志输出的目的地，目的地可以是控制台、文件、远程套接字服务器、 MySQL、 PostreSQL、 Oracle和其他数据库、 JMS和远程UNIX Syslog守护进程等。
    
Logger可以被分配级别。级别包括：TRACE、DEBUG、INFO、WARN 和 ERROR，定义于 ch.qos.logback.classic.Level类。如果 logger没有被分配级别，那么它将从有被分配级别的最近的祖先那里继承级别。root logger 默认级别是 DEBUG。


logger配置
---------

**logger**用来设置某一个包或者具体的某一个类的日志打印级别、以及指定appender。logger仅有一个name属性，一个可选的level和一个可选的addtivity属性。
    
**name**:用来指定受此logger约束的某一个包或者具体的某一个类。
**level**:用来设置打印级别，大小写无关：TRACE, DEBUG, INFO, WARN, ERROR, ALL 和 OFF，还有一个特俗值INHERITED或者同义词NULL，代表强制执行上级的级别。如果未设置此属性，那么当前loger将会继承上级的级别。
**addtivity**:是否向上级logger传递打印信息。默认是true。logger可以包含零个或多个appender-ref元素，标识这个appender将会添加到这个logger。

---------

root配置
---------

root也是logger元素，但是它是根logger。只有一个level属性，被命名为"root"。

```<level>```用来设置打印级别：TRACE, DEBUG, INFO, WARN, ERROR, ALL 和 OFF，不能设置为INHERITED或者同义词NULL。默认是DEBUG。root可以包含零个或多个appender-ref元素，标识这个appender将会添加到这个loger。

---------

appender配置
---------
**appender** 是**configuration**的子节点，是负责写日志的组件，有两个必要属性name和class。name指定appender名称，class指定appender的全限定名。
    
**ConsoleAppender**：把日志添加到控制台
**FileAppender**：把日志添加到文件
**RollingFileAppender**：滚动记录文件，先将日志记录到指定文件，当符合某个条件时，将日志记录到其他文件。（可选滚动策略包括TimeBasedRollingPolicy、FixedWindowRollingPolicy、SizeBasedTriggeringPolicy）


filter配置
---------------

过滤器，执行一个过滤器会有返回个枚举值，即DENY，NEUTRAL，ACCEPT其中之一。返回DENY，日志将立即被抛弃不再经过其他过滤器；返回NEUTRAL，有序列表里的下个过滤器过接着处理日志；返回ACCEPT，日志会被立即处理，不再经过剩余过滤器。过滤器被添加到Appender中，为Appender添加一个或多个过滤器后，可以用任意条件对日志进行过滤。Appender有多个过滤器时，按照配置顺序执行。
    
常用的过滤器：
    **LevelFilter**， 级别过滤器，根据日志级别进行过滤；
    **ThresholdFilter**， 临界值过滤器，过滤掉低于指定临界值的日志；
    **EvaluatorFilter**， 求值过滤器，评估、鉴别日志是否符合指定条件。


Rest服务日志打印规范
-----------

1. 日志需要在fileNamePattern中启用gzip压缩


2. 日志均采用滚动记录文件（RollingFileAppender），按照时间滚动策略（TimeBasedRollingPolicy）

3. 新上线服务需要配置相应的logger,独立打印logger，避免日志被淹没,同时注意设置additivity="false"，避免日志被重复打印

4. 参考示例：Antispam logger格式

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

参考资料
----------
1. http://stackoverflow.com/questions/2031163/when-to-use-log-level-warn-vs-error
2. http://blog.csdn.net/haidage/article/details/6794509