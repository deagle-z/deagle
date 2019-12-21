package com.zw.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


/**
 * @author zw
 * @description mongo page分页
 * @date 2019/12/18
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeaglePageable<T> implements Pageable {

    private QueryPage<T> queryPage;
    private String sortColumn;

    @Override
    public int getPageNumber() {
        return queryPage.currentPage;
    }

    @Override
    public int getPageSize() {
        return queryPage.size;
    }

    @Override
    public long getOffset() {
        return (getPageNumber() - 1) * getPageSize();
    }

    @Override
    public Sort getSort() {
        return Sort.by(sortColumn);
    }

    @Override
    @Deprecated
    public Pageable next() {
        return null;
    }

    @Override
    @Deprecated
    public Pageable previousOrFirst() {
        return null;
    }

    @Override
    @Deprecated
    public Pageable first() {
        return null;
    }

    @Override
    @Deprecated
    public boolean hasPrevious() {
        return false;
    }
}
