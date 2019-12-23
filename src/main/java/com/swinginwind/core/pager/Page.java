package com.swinginwind.core.pager;

import java.io.Serializable;
import java.util.List;

/**
 * 分页对象，里面包括分页信息和数据结果
 */
public class Page<E> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int pageNum = 1;//当前页x
	private int pageSize = 15;//当前最大记录数x
	private int startRow;
	private int endRow;
	private long total; //记录数
	private int pages; //总页数x
	private List<E> result;//x

	public Page() {
		
	}
	
	public Page(Integer pageNum, Integer pageSize) {
		if (pageNum == null || pageNum < 1) {
			pageNum = 1;
		}
		if (pageSize == null || pageSize < 1) {
			pageSize = 15;
		}
		this.pageNum = pageNum;
		this.pageSize = pageSize;
	}

	public List<E> getResult() {
		return result;
	}

	public void setResult(List<E> result) {
		this.result = result;
	}

	public int getPages() {
		// 计算总页数
		long totalPage = this.getTotal() / this.getPageSize()
				+ ((this.getTotal() % this.getPageSize() == 0) ? 0 : 1);
		this.setPages((int) totalPage);
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public int getEndRow() {
		this.endRow = pageNum * pageSize;
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getStartRow() {
		this.startRow = this.pageNum > 0
				? (this.pageNum - 1) * this.pageSize
				: 0;
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "Page{" + "pageNum=" + pageNum + ", pageSize=" + pageSize
				+ ", startRow=" + startRow + ", endRow=" + endRow
				+ ", total=" + total + ", pages=" + pages + '}';
	}
}