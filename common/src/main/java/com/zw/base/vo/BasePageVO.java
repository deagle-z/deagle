package com.zw.base.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 基础分页返回
 *
 * @author zw562
 */
@Data
public class BasePageVO<T> {

	private Boolean hasNextPage;

	private Long total ;

	private List<T> pageList;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonIgnoreProperties(ignoreUnknown = true)
	private String data;

	public Long getTotal() {
		if (null == total) {
			return 0L;
		}
		return total;
	}

	public Boolean getHasNextPage() {
		if (null == hasNextPage) {
			return false;
		}
		return hasNextPage;
	}

	public List<T> getPageList() {
		if(null == pageList){
			return new ArrayList<>();
		}
		return pageList;
	}
}
