package com.distributed.common.uuid.config;

import com.baidu.fsg.uid.impl.CachedUidGenerator;
import com.baidu.fsg.uid.worker.DisposableWorkerIdAssigner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author: Mr.zhang
 * @Date: 2020/3/11 11:42
 */
@Configuration
public class CachedUidConfig {

    @Bean
    public CachedUidGenerator cachedUidGenerator() {
        CachedUidGenerator cachedUidGenerator = new CachedUidGenerator();
        cachedUidGenerator.setWorkerIdAssigner(disposableWorkerIdAssigner());
        //自定义配置，可不配，走默认值
        cachedUidGenerator.setTimeBits(timeBits);
        //机器id，最多可支持约420w次机器启动。内置实现为在启动时由数据库分配，默认分配策略为用后即弃，后续可提供复用策略。
        cachedUidGenerator.setWorkerBits(workerBits);
        //每秒下的并发序列，13 bits可支持每秒8192个并发。（注意下这个地方，默认支持qps最大为8192个）
        cachedUidGenerator.setSeqBits(seqBits);
        // 时间节点生成增量
        cachedUidGenerator.setEpochStr(epochStr);
        //默认:3， 原bufferSize=8192, 扩容后bufferSize= 8192 << 3 = 65536
        cachedUidGenerator.setBoostPower(boostPower);
        //另外一种RingBuffer填充时机, 在Schedule线程中, 周期性检查填充
        // 默认:不配置此项, 即不实用Schedule线程. 如需使用, 请指定Schedule线程时间间隔, 单位:秒
         cachedUidGenerator.setScheduleInterval(scheduleInterval);
        //拒绝策略: 当环已空, 无法继续获取时
//        默认无需指定, 将记录日志, 并抛出UidGenerateException异常. 如有特殊需求, 请实现RejectedTakeBufferHandler接口(支持Lambda表达式)
//        cachedUidGenerator.setRejectedTakeBufferHandler();
        return cachedUidGenerator;
    }

    @Bean
    public  DisposableWorkerIdAssigner disposableWorkerIdAssigner() {
        return new DisposableWorkerIdAssigner();
    }

    @Value(value = "${cachedUid.timeBits:29}")
    private Integer timeBits;
    @Value(value = "${cachedUid.workerBits:21}")
    private Integer workerBits;
    @Value(value = "${cachedUid.seqBits:13}")
    private Integer seqBits;
    @Value(value = "${cachedUid.epochStr:2020-03-10}")
    private String epochStr;
    @Value(value = "${cachedUid.boostPower:3}")
    private Integer boostPower;
    @Value(value = "${cachedUid.scheduleInterval: 60}")
    private Integer scheduleInterval;
}
