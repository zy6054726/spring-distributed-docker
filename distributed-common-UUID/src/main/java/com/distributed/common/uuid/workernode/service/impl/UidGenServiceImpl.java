package com.distributed.common.uuid.workernode.service.impl;

import com.baidu.fsg.uid.UidGenerator;
import com.distributed.common.uuid.workernode.service.UidGenService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: Mr.zhang
 * @Date: 2020/3/11 13:18
 */
@Service
public class UidGenServiceImpl implements UidGenService {

    @Resource
    private UidGenerator uidGenerator;

    @Override
    public long getUid() {
        return uidGenerator.getUID();
    }

    @Override
    public String parseUid(Long uid) {
        return uidGenerator.parseUID(uid);
    }
}
