package edu.wtbu.pojo;

public class Result {
	private String flag;
	private Object data;
	private Page page;
	
	public Result() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Result(String flag, Object data, Page page) {
		super();
		this.flag = flag;
		this.data = data;
		this.page = page;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
}
