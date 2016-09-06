package kr.ac.sungkyul.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import kr.ac.sungkyul.mysite.vo.BoardVo;

@Repository
public class BoardDao {

	
	private Connection getConnection() throws SQLException {

		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("error : " + e);
		}
		return conn;
	}
	public void delete(BoardVo vo) { // 게시물 삭제
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn=getConnection();
		
			String sql="delete from boards where no =? ";
			pstmt=conn.prepareStatement(sql);

			pstmt.setLong(1, vo.getNo());
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("연결 오류 : " + e);
		} finally {
			try {

				if (conn != null) {
					conn.close();
				}
				if(pstmt!=null){
					pstmt.close();
				}
			} catch (SQLException e) {
				System.out.println("error : " + e);
			}
		}
	}
	
	public void update(BoardVo vo) { // 게시물 수정
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			String sql = null;
			sql = "update boards set title=?, content=? where no=?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setLong(3, vo.getNo());
			
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error : " + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error : " + e);
			}
		}
	}
	
	public void updateViewCount(Long no){ // 조회수 증가
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();

			String sql = "update boards SET view_count=VIEW_COUNT+1 where no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, no);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("error : " + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error : " + e);
			}
		}
	}
	public List<BoardVo> getList(long page, int pagesize, String keyword) { // 게시판 리스트 가져오기
	      List<BoardVo> list = new ArrayList<BoardVo>();
	      Connection conn = null;
	      PreparedStatement pstmt  = null;
	      ResultSet rs = null;
	      try {
	         conn = getConnection();

	        
	         String sql = (keyword ==null || "".equals(keyword)) ?
	        		 "select * from(select c.*,rownum rn from(select a.no,a.title,b.name,a.user_no,a.view_count,to_char(a.reg_date, 'yyyy-mm-dd pm hh:mi:ss'),a.depth from boards a, users b where a.user_no=b.no order by group_no desc, order_no asc) c) where ?<=rn and rn<=?"
	        		 : "SELECT * FROM (SELECT c.*, rownum rn FROM (  SELECT a.no, a.title, b.name, a.user_no, a.view_count, to_char(reg_date, 'yyyy-mm-dd pm hh:mi:ss'), depth"
	        			 + "FROM boards a,  users b WHERE a.user_no = b.no AND (title like ? OR content like ?)"
	        			 + "ORDER BY group_no DESC, order_no ASC) c )"
	        			 + "WHERE ?  <= rn AND rn <= ?" ;
	        			
	         pstmt = conn.prepareStatement(sql);
	         
	         if( keyword ==null || "".equals(keyword)){
	        	 pstmt.setLong(1, (page-1)*pagesize + 1);
	        	 pstmt.setLong(2, pagesize);
	         } else {
	        	 pstmt.setString(1, "%"+keyword+"%");
	        	 pstmt.setString(2, "%"+keyword+"%");
	        	 pstmt.setLong(3, (page-1)*pagesize + 1);
	        	 pstmt.setLong(4, pagesize);
	         }
	         
	         rs = pstmt.executeQuery();

	         while (rs.next()) {
	            Long no = rs.getLong(1);
	            String title = rs.getString(2);
	            String name = rs.getString(3);
	            Long userNo=rs.getLong(4);
	            Integer count = rs.getInt(5);
	            String date = rs.getString(6);
	            Integer depth = rs.getInt(7);

	            BoardVo vo = new BoardVo();
	            vo.setNo(no);
	            vo.setTitle(title);
	            vo.setName(name);
	            vo.setUserNo(userNo);
	            vo.setCount(count);
	            vo.setDate(date);
	            vo.setDepth(depth);


	            list.add(vo);
	         }
	      } catch (SQLException e) {
	         System.out.println("error : " + e);
	      } finally {
	         try {

	            if (rs != null) {
	               rs.close();
	            }

	            if (pstmt != null) {
	            	pstmt.close();
	            }
	            if (conn != null) {
	               conn.close();
	            }
	         } catch (SQLException e) {
	            System.out.println("error : " + e);
	         }
	      }

	      return list;

	   }

	
	
	// 게시물 보기(view)
	public BoardVo get(Long no) {
		BoardVo vo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			conn = getConnection();

			String sql = "select no, title, content, user_no from boards where no=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, no);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				vo = new BoardVo();
				vo.setNo(rs.getLong(1));
				vo.setTitle(rs.getString(2));
				vo.setContent(rs.getString(3));
				vo.setUserNo(rs.getLong(4));
			}

		} catch (SQLException e) {
			System.out.println("error : " + e);
		}finally {
			try {
				if(rs != null){
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error : " + e);
			}
		}

		return vo;

	}

	public int getTotalCount(){ // 게시물 갯수 반환
		int totalCount = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			conn = getConnection();
			
			String sql = "select count(*) from boards";
					
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			if(rs.next()){
				totalCount = rs.getInt(1);
			}
		}catch(SQLException e){
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
				if(rs!=null){
					rs.close();
				}
			} catch (SQLException e) {
				System.out.println("error : " + e);
			}
		}
		return totalCount;
	}
	
	
	public void insert(BoardVo vo){ // 글 작성
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try{
			conn=getConnection();
			
			String sql = (vo.getGroupNo()==null) ? 
					"insert into boards values(seq_boards.nextval, ?, ?, sysdate, 0,"
					+ "nvl((select max(group_no) from boards),0)+1, 1,1, ?)" :
					
						"insert into boards values(seq_boards.nextval, ?, ?, sysdate, 0, ?, ?, ?, ?) ";
			pstmt = conn.prepareStatement(sql);
			
			if(vo.getGroupNo()==null){
				pstmt.setString(1, vo.getTitle());
				pstmt.setString(2, vo.getContent());
				pstmt.setLong(3, vo.getUserNo());
			}else {
				pstmt.setString(1, vo.getTitle());
				pstmt.setString(2, vo.getContent());
				pstmt.setInt(3, vo.getGroupNo());
				pstmt.setInt(4, vo.getGroupOrderNo());
				pstmt.setInt(5, vo.getDepth());
				pstmt.setLong(6, vo.getUserNo());
			}
			
			pstmt.executeUpdate();
			
		}catch(SQLException e){
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error : " + e);
			}
		}
	
	}

	public void updateGroupOrder( BoardVo boardVo ) { // 답글 작성 시 그룹의 order_no을 업데이트
		Connection conn = null;
		PreparedStatement pstmt = null;
		try{
			conn = getConnection();
			String sql = 
				"UPDATE boards" +  
				"      SET order_no = order_no + 1" +
				" WHERE group_no = ?" +
				"    AND order_no >= ?";  
			pstmt = conn.prepareStatement( sql );
			pstmt.setInt( 1, boardVo.getGroupNo());
			pstmt.setInt( 2, boardVo.getGroupOrderNo());
			
			pstmt.executeUpdate();
		} catch( SQLException ex ) {
			System.out.println( "error:" + ex );
		} finally {
			try{
				if( pstmt != null ) {
					pstmt.close();
				}
				if( conn != null ) {
					conn.close();
				}
			}catch( SQLException ex ) {
				ex.printStackTrace();
			}
		}		
	}
	public BoardVo getInfo(Long no){
		BoardVo vo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			conn = getConnection();
			String sql ="select no, title, content, group_no, order_no, depth from boards where no=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, no);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				vo = new BoardVo();
				vo.setNo(rs.getLong(1));
				vo.setTitle(rs.getString(2));
				vo.setContent(rs.getString(3));
				vo.setGroupNo(rs.getInt(4));
				vo.setGroupOrderNo(rs.getInt(5));
				vo.setDepth(rs.getInt(6));
			}
		}catch( SQLException ex ) {
			System.out.println( "error:" + ex );
		} finally {
			try{
				if( pstmt != null ) {
					pstmt.close();
				}
				if( conn != null ) {
					conn.close();
				}
			}catch( SQLException ex ) {
				ex.printStackTrace();
			}
		}
		return vo;
	}
	
}
