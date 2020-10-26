package com.distributed.common.uuid.workernode.service;

/**
 * @author: Mr.zhang
 * @Date: 2020/3/11 13:18
 */
public interface UidGenService {
    public long getUid();

    public String parseUid(Long uid);
}
