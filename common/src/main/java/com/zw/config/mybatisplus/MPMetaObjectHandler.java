/*
 * COPYRIGHT China Mobile (SuZhou) Software Technology Co.,Ltd. 2019
 *
 * The copyright to the computer program(s) herein is the property of
 * CMSS Co.,Ltd. The programs may be used and/or copied only with written
 * permission from CMSS Co.,Ltd. or in accordance with the terms and conditions
 * stipulated in the agreement/contract under which the program(s) have been
 * supplied.
 */

package com.zw.config.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 * Create by Tianhaobing ON 2019/2/25
 */
@Slf4j
public class MPMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(final MetaObject metaObject) {
        if (!metaObject.hasGetter("createDate") || !metaObject.hasGetter("updateDate") || !metaObject.hasGetter("isDelete")) {
            return;
        }
        log.warn(" ==> start insert fill ....");
        this.strictInsertFill(metaObject, "createDate", Date.class, new Date());
        this.strictInsertFill(metaObject, "updateDate", Date.class, new Date());
        this.strictInsertFill(metaObject, "isDelete", String.class, 0);
    }

    @Override
    public void updateFill(final MetaObject metaObject) {
        this.strictInsertFill(metaObject, "updateDate", Date.class, new Date());
    }
}