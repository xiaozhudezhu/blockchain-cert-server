package com.swinginwind.core.pager;

import java.util.List;

/**
 * <pre>
 * 功能说明：
 * </pre>
 * 
 * @author <a href="mailto:shao.gq@gener-tech.com">ShaoGuoqing</a>
 * @version 1.0
 */

public class JqgridResponse<T> extends JSONResponse {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6267290390033812823L;

	/**
     * 当前页
     */
    private int page;

    /**
     * 总页数
     */
    private int total;

    /**
     * 总记录数
     */
    private long records;

    /**
     * 返回数据
     */
    private List<T> rows;
    

    protected JqgridResponse() {
    	super();
    }
    
    public JqgridResponse(Page<T> pager) {
    	super();
        this.setPage(pager.getPageNum());
        this.setTotal(pager.getPages());
        this.setRecords(pager.getTotal());
        this.setRows(pager.getResult());
    }
    
    public JqgridResponse(Integer page, Integer total, Integer records, List<T> rows) {
    	super();
    	this.setPage(page);
        this.setTotal(total);
        this.setRecords(records);
        this.setRows(rows);
    }
    

    public int getPage() {
        return page;
    }

    public long getRecords() {
        return records;
    }

    public List<T> getRows() {
        return rows;
    }

    public int getTotal() {
        return total;
    }

    public void setPage(Integer page) {
        this.page = page;
        this.put("page", page);
    }

    public void setRecords(long records) {
        this.records = records;
        this.put("records", records);
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
        this.put("rows", rows);
    }

    public void setTotal(Integer total) {
        this.total = total;
        this.put("total", total);
    }

    @Override
    public String toString() {
        return "JqgridResponse [page=" + page + ", total=" + total + ", records=" + records + "]";
    }
}
