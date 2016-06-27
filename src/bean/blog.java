package bean;

//存储一篇博客的bean

public class blog {
	private String author;
	private String title;
	private String blogAbstract;
	private String content;
	private String dates;
	private String tag;
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBlogAbstract() {
		return blogAbstract;
	}
	public void setBlogAbstract(String blogAbstract) {
		this.blogAbstract = blogAbstract;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDates() {
		return dates;
	}
	public void setDates(String dates) {
		this.dates = dates;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
}
