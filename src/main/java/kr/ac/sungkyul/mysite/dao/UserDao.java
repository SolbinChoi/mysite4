package kr.ac.sungkyul.mysite.dao;

import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.ac.sungkyul.mysite.vo.UserVo;

@Repository
public class UserDao {

	@Autowired
	private DataSource dataSource; // 인터페이스
	
	@Autowired
	private SqlSession sqlSession;
	
	
	public void update(UserVo vo) { // 사용자 정보 업데이트
		sqlSession.update("user.update",vo);
	}
	
	public UserVo get(String email){ // 이메일 중복 체크
		UserVo vo = sqlSession.selectOne("user.getByEmail",email);
		return vo;
	}
		
	public UserVo get(Long userno) { // 사용자 번호
		UserVo vo = sqlSession.selectOne("user.getByNo", userno);
		// List<UserVo> vo = sqlSession.selectList("user.getByNo", userno);
		return vo;

	}

	public UserVo get(String email, String password) { // 로그인 할 때 이메일과 패스워드
		UserVo userVo = new UserVo();
		userVo.setEmail(email);
		userVo.setPassword(password);
		// 만약에 파라미터로 넘겨야 할 매핑 클래스가 없는 경우
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("email", email);
//		map.put("password",password);
//		UserVo vo = sqlSession.selectOne("user.getByEmailAndPassword", map);
		UserVo vo = sqlSession.selectOne("user.getByEmailAndPassword", userVo); // row 결과는 하나만 나올 수 있음
		return vo;

	}

	public void insert(UserVo vo) { // 회원가입
		int count = sqlSession.insert("user.insert",vo);
	}

}
