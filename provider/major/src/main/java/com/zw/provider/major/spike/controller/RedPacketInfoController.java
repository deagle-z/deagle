package com.zw.provider.major.spike.controller;


import com.zw.provider.major.spike.query.OpenRedPackageQuery;
import com.zw.provider.major.spike.query.SaveRedPackageQuery;
import com.zw.provider.major.spike.service.RedPacketInfoService;
import com.zw.response.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 红包信息
表，新建⼀个红包插⼊⼀条记录 前端控制器
 * </p>
 *
 * @author zw
 * @since 2020-09-02
 */
@RestController
@Api(value = "RedPacketInfo" ,tags = "红包信息表，新建⼀个红包插⼊⼀条记录")
@RequestMapping("/RedPacketInfo")
public class RedPacketInfoController {

    @Resource
    RedPacketInfoService redPacketInfoService;

    @PostMapping("/SendRedPackage")
    @ApiOperation(value = "发送红包", httpMethod = "POST", response = R.class, notes = "发送红包")
    public R<?> sendRedPackage(@RequestBody SaveRedPackageQuery query) {
        redPacketInfoService.sendRedPackage(query.getTotalAmount(), query.getTotalPacket(), "1");
        return R.success("发送成功");
    }

    @PostMapping("/OpenRedPackage")
    @ApiOperation(value = "开红包", httpMethod = "POST", response = R.class, notes = "开红包")
    public R<?> openRedPackage(@RequestBody OpenRedPackageQuery query) {
        return R.success(redPacketInfoService.openRedPacket(query.getRedPacketId(), "1"));
    }
}
