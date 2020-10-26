package com.distributed.common.uuid.workernode.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.distributed.common.uuid.workernode.service.UidGenService;
import javax.annotation.Resource;

/**
 * 生成uid
 * @author: Mr.zhang
 * @Date: 2020/3/11 13:20
 */
@RequestMapping("/getUid")
@RestController
public class UidGenController {
    @Resource
    private UidGenService uidGenService;

    @GetMapping("/")
    public Long getUid() {
        return uidGenService.getUid();
    }

    @GetMapping("/{uid}")
    public String longToString(@PathVariable("uid") Long uid) {
       return  uidGenService.parseUid(uid);
    }
}
