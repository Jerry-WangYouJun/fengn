package com.model;

import java.util.List;

public class Pagination {
	//第几页
	 private int pageNo = 1;
	 //每页几条
	 private int pageSize = 50;
	 //共几页
	 private int pageIndex  ;
	 //总条数
	 private int total ;
	 private List list;
	
	public Pagination() {
		super();
	}
	public Pagination(int pageNo, int pageSize) {
		super();
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}
	
	public Pagination(String pageNo, String pageSize) {
		super();
		this.pageNo = pageNo==null?1:Integer.valueOf(pageNo);
		this.pageSize = pageSize==null?10:Integer.valueOf(pageSize);
	}
	
	public Pagination(String pageNo, String pageSize , int initSize) {
		super();
		this.pageNo = pageNo==null?1:Integer.valueOf(pageNo);
		this.pageSize = pageSize==null?initSize:Integer.valueOf(pageSize);
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageIndex() {
		
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	 
}
