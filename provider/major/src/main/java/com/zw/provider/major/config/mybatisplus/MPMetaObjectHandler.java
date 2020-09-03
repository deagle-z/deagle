/*
 * COPYRIGHT China Mobile (SuZhou) Software Technology Co.,Ltd. 2019
 *
 * The copyright to the computer program(s) herein is the property of
 * CMSS Co.,Ltd. The programs may be used and/or copied only with written
 * permission from CMSS Co.,Ltd. or in accordance with the terms and conditions
 * stipulated in the agreement/contract under which the program(s) have been
 * supplied.
 */

package com.zw.provider.major.config.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.zw.util.LocalDateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * mybatisPlus 统一处理新增/修改 必改字段
 *
 * @author zw
 * @date 2020/8/6
 */
@Slf4j
@Component
public class MPMetaObjectHandler implements MetaObjectHandler {

    public static final String CREATE_BY = "createBy";
    public static final String UPDATE_BY = "updateBy";

    @Override
    public void insertFill(final MetaObject metaObject) {
        log.warn(" ======> start insert fill ....<======");

        final LocalDateTime now = LocalDateTimeUtil.nowTime();
        this.strictInsertFill(metaObject, "createDate", LocalDateTime.class,now );
        this.strictInsertFill(metaObject, "updateDate", LocalDateTime.class, now);
        this.strictInsertFill(metaObject, "deleteFlag", String.class, "0");
    }

    @Override
    public void updateFill(final MetaObject metaObject) {
        this.strictInsertFill(metaObject, "updateDate", Date.class, new Date());
    }
}