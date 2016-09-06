package kr.ac.sungkyul.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ac.sungkyul.mysite.dao.BoardDao;
import kr.ac.sungkyul.mysite.vo.BoardVo;

@Service
public class BoardService {

	@Autowired
	BoardDao boardDao;

	private final int LIST_PAGESIZE = 10; // 리스팅되는 게시물의 수
	private final int LIST_BLOCKSIZE = 5; // 페이지 리스트에서 표시되는 페이지 수

	// 1. 페이지 파라미터 가져오기

	public Map<String, Object> list(Long page, String keyword){
	      
	      long totalCount = boardDao.getTotalCount();
	      long pageCount = (long) Math.ceil((double) totalCount / LIST_PAGESIZE);
	      long blockCount = (long) Math.ceil((double) pageCount / LIST_BLOCKSIZE);
	      long currentBlock = (long) Math.ceil((double) page / LIST_BLOCKSIZE);
	      
	      // 4. page값 검증
	      if (page < 1) {
	         page = 1L;
	         currentBlock = 1;
	      } else if (page > pageCount) {
	         page = pageCount;
	         currentBlock = (int) Math.ceil((double) page / LIST_BLOCKSIZE);
	      }

	      // 5. 페이지를 그리기 위한 값 계산
	      long startPage = currentBlock == 0 ? 1 : (currentBlock - 1) * LIST_BLOCKSIZE + 1;
	      long endPage = (startPage - 1) + LIST_BLOCKSIZE;
	      long prevPage = (currentBlock > 1) ? (currentBlock - 1) * LIST_BLOCKSIZE : 0;
	      long nextPage = (currentBlock < blockCount) ? currentBlock * LIST_BLOCKSIZE + 1 : 0;
	      
	      List<BoardVo> list=boardDao.getList(page, LIST_PAGESIZE, keyword);
	      
	
	      Map<String, Object> map=new HashMap<String, Object>();
	      map.put("sizeList", LIST_PAGESIZE);
			map.put("firstPage", startPage);
			map.put("lastPage", endPage);
			map.put("prevPage", prevPage);
			map.put("nextPage", nextPage);
			map.put("currentPage", page);
			map.put("pageCount", pageCount);
			map.put("list", list);
			map.put("totalCount", totalCount);
			map.put("keyword", keyword);
	      return map;
	   }
	public void viewCount(Long no){
		boardDao.updateViewCount(no);
	}
	public BoardVo view(Long no){
		BoardVo vo = boardDao.get(no);
		return vo;
	}
	public void write(BoardVo vo){
		boardDao.insert(vo);
	}
	public void delete(BoardVo vo){
		boardDao.delete(vo);
	}
	
	public BoardVo boardInfo(Long no){
		BoardVo vo = boardDao.getInfo(no);
		return vo;
	}
	public void updateOrderNo(BoardVo vo){
		boardDao.updateGroupOrder(vo);
	}
	public void modify(BoardVo vo){
		boardDao.update(vo);
	}
}
