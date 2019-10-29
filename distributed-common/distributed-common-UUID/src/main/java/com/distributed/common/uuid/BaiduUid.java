package com.distributed.common.uuid;

import com.baidu.fsg.uid.impl.CachedUidGenerator;
import com.baidu.fsg.uid.impl.DefaultUidGenerator;
import com.baidu.fsg.uid.worker.DisposableWorkerIdAssigner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 百度雪花算法属性配置
 */
@Component
public class BaiduUid {

    @Autowired
    private DisposableWorkerIdAssigner disposableWorkerIdAssigner;

    @Bean(value = "defaultUidGenerator")
    public DefaultUidGenerator initDefaultUid() {
        DefaultUidGenerator defaultUidGenerator = new DefaultUidGenerator();
        defaultUidGenerator.setWorkerIdAssigner(disposableWorkerIdAssigner);
        defaultUidGenerator.setTimeBits(29);
        defaultUidGenerator.setWorkerBits(21);
        defaultUidGenerator.setSeqBits(13);
        defaultUidGenerator.setEpochStr(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        return defaultUidGenerator;
    }

    @Bean(value = "cachedUidGenerator")
    public CachedUidGenerator initCacheUid() {
        CachedUidGenerator cachedUidGenerator = new CachedUidGenerator();
        cachedUidGenerator.setWorkerIdAssigner(disposableWorkerIdAssigner);
        cachedUidGenerator.setTimeBits(29);
        cachedUidGenerator.setWorkerBits(21);
        cachedUidGenerator.setSeqBits(13);
        cachedUidGenerator.setEpochStr(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        cachedUidGenerator.setBoostPower(3);
        cachedUidGenerator.setScheduleInterval(60);
        return cachedUidGenerator;
    }
}
