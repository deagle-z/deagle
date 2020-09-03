package com.zw.base.query;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 基础分页查询类
 *
 * @author zw562
 */
public class BasePageQuery {

	private Integer pageIndex = 1;

	private Integer pageSize = 20;

	@JsonIgnore
	private Integer startRow;

	@JsonIgnore
	private Integer endRow;

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getStartRow() {
		return (this.pageIndex - 1) * this.pageSize;
	}

	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}

	public Integer getEndRow() {
		return this.pageIndex * this.pageSize;
	}

	public void setEndRow(Integer endRow) {
		this.endRow = endRow;
	}
}
