package com.damoyo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.damoyo.domain.BoardCateVO;
import com.damoyo.domain.BoardPageDTO;
import com.damoyo.domain.BoardSearchCriteria;
import com.damoyo.domain.BoardVO;
import com.damoyo.domain.MeetVO;
import com.damoyo.domain.UserVO;
import com.damoyo.service.BoardService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/board/*")
@AllArgsConstructor
public class BoardController {
	
	@Autowired
	private BoardService service;
	
	@GetMapping("/list")
	public void list(Model model, HttpSession session, BoardSearchCriteria cri) {
		// user, meet 정보
		UserVO userInfo = (UserVO) session.getAttribute("userInfo");
		MeetVO meetInfo = (MeetVO) session.getAttribute("meetInfo");
		Map<String, Object> infos = new HashMap<String, Object>();
		infos.put("user", userInfo);
		infos.put("meet", meetInfo);
		
		// 게시판 카테고리
		List<BoardCateVO> category = service.getBoardCate();
		
		// 게시판_Meet에 해당하는 것만 출력
		if(cri.getKeyword() == null)
			cri.setKeyword("");
		List<BoardVO> boardList = service.getBoards(cri, meetInfo.getM_num());
		int total = service.getTotalBoard(cri, meetInfo.getM_num());
		BoardPageDTO boardPages = new BoardPageDTO(total, cri);
		
		model.addAttribute("infos", infos);
		model.addAttribute("category", category);
		model.addAttribute("list", boardList);
		model.addAttribute("boardPages", boardPages);
	}
	
	@GetMapping("/detail")
	public void detail(Model model, Long b_num, HttpSession session) throws JsonProcessingException {
		UserVO userInfo = (UserVO) session.getAttribute("userInfo");
		MeetVO meetInfo = (MeetVO) session.getAttribute("meetInfo");
		BoardVO boardInfo = service.getBoard(b_num);
		log.info("board 정보 : "+boardInfo);
		Map<String, Object> infos = new HashMap<String, Object>();
		infos.put("user", userInfo);
		infos.put("meet", meetInfo);
		infos.put("board", boardInfo);
		
		// JSON형태로 객체 전송_.js에서 읽기 위해
		ObjectMapper mapper = new ObjectMapper();
		String board = mapper.writeValueAsString(boardInfo);
		String user = mapper.writeValueAsString(userInfo);
		model.addAttribute("board", board);
		model.addAttribute("user", user);
		
		model.addAttribute("infos", infos);
	}
	
	@PostMapping("/delete")
	public String delete(Long b_num, RedirectAttributes rttr) {
		log.info(b_num);
		
		service.delete(b_num);
//		service.delete(6L);
		
//		rttr.addFlashAttribute("delete", "success");
		
		return "redirect:/board/list";
	}
	
	@GetMapping("/write")
	public void write(HttpSession session, Model model) {
		UserVO userInfo = (UserVO) session.getAttribute("userInfo");
		MeetVO meetInfo = (MeetVO) session.getAttribute("meetInfo");
		List<BoardCateVO> category = service.getBoardCate();
		Map<String, Object> infos = new HashMap<String, Object>();
		infos.put("user", userInfo);
		infos.put("meet", meetInfo);
		infos.put("category", category);
		
		model.addAttribute("infos", infos);
	}
	
	@PostMapping("/register")
	public String register(BoardVO vo, RedirectAttributes rttr) {
		service.insert(vo);
		return "redirect:/board/list";
	}
	
	
}
