package kr.ac.sungkyul.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ac.sungkyul.mysite.dao.GuestbookDao;
import kr.ac.sungkyul.mysite.vo.GuestbookVo;

@Service
public class GuestbookService {
	
	@Autowired
	private GuestbookDao guestbookDao;
	
	public List<GuestbookVo> list(){
		List<GuestbookVo> list = guestbookDao.getList();
		return list;
	}
	
	public void insert(GuestbookVo vo){
		guestbookDao.insert(vo);
	}
	
	public void delete(GuestbookVo vo){
		guestbookDao.delete(vo);
	}
}
