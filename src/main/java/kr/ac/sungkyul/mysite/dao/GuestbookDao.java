package kr.ac.sungkyul.mysite.dao;

import java.util.List;
import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import kr.ac.sungkyul.mysite.vo.GuestbookVo;

@Repository

public class GuestbookDao {
	
	@Autowired
	private DataSource dataSource; // 인터페이스
	
	@Autowired
	private SqlSession sqlSession;
	
	public List<GuestbookVo> getList() {
		List<GuestbookVo> list = sqlSession.selectList("guestbook.getList");
		return list;

	}
	
	public void insert(GuestbookVo vo) {
		sqlSession.insert("guestbook.insert", vo);
	}
	
	public void delete(GuestbookVo vo) {
		sqlSession.delete("guestbook.delete", vo);
	}
}
