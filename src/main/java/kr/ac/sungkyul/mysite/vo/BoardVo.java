package kr.ac.sungkyul.mysite.vo;

public class BoardVo {
	private Long no;
	private String title; // 제목
	private String content; // 내용
	private Integer count; // 조회수
	private Integer groupNo; // 그룹 번호
	private Integer groupOrderNo; // 그룹 내 정렬번호
	private Integer depth; // 글 깊이(답글)
	private Long userNo; // 회원번호
	private String date; // 등록일
	private String name; // 회원이름
	
	
	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Integer getGroupNo() {
		return groupNo;
	}
	public void setGroupNo(Integer groupNo) {
		this.groupNo = groupNo;
	}
	public Integer getGroupOrderNo() {
		return groupOrderNo;
	}
	public void setGroupOrderNo(Integer groupOrderNo) {
		this.groupOrderNo = groupOrderNo;
	}
	public Integer getDepth() {
		return depth;
	}
	public void setDepth(Integer depth) {
		this.depth = depth;
	}

	public Long getUserNo() {
		return userNo;
	}
	public void setUserNo(Long userNo) {
		this.userNo = userNo;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "BoardVo [no=" + no + ", title=" + title + ", content=" + content + ", count=" + count + ", groupNo="
				+ groupNo + ", groupOrderNo=" + groupOrderNo + ", depth=" + depth + ", UserNo=" + userNo + ", date="
				+ date + ", name=" + name + "]";
	}
	
}
