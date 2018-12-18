package com.example.app.controller;

import java.io.PrintWriter;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.app.modal.Word;
import com.example.app.service.Dictionary;
import com.example.app.service.WordService;

@Controller
public class WordController {
	@Autowired
	WordService m_objWordService;
	
	@Autowired
	Dictionary m_objDictionary;

	@RequestMapping(value = "/index")
	public ModelAndView wordController() {
		return new ModelAndView("index");
	}

	@RequestMapping(value="/findValidWords",method=RequestMethod.POST)
	public @ResponseBody String findValidWords(Word p_objWord, HttpServletRequest request,HttpServletResponse response) {
		try {
			p_objWord.setWordsList(p_objWord.getWords().split(","));
			char chMatrix[][] = m_objWordService.createCharMatrix(p_objWord);
			Set<String> l_setValidWords = m_objWordService.findValidWords(chMatrix);
			JSONObject l_objJson = new JSONObject();
			l_objJson.put("output", l_setValidWords);
			PrintWriter l_objPrintWriter = response.getWriter();
			l_objPrintWriter.print(l_objJson);
			l_objPrintWriter.flush();
			l_objPrintWriter.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return "index";
	}
	
	@RequestMapping(value="/searchWords",method=RequestMethod.POST)
	public @ResponseBody String searchWord(String searchWord,HttpServletRequest request,HttpServletResponse response) {
		try {
			Set<String> l_lstSearchWords =	m_objDictionary.searchByPrefix(searchWord.toLowerCase());
			JSONObject l_objJson = new JSONObject();
			l_objJson.put("searchWords", l_lstSearchWords);
			PrintWriter l_objPrintWriter = response.getWriter();
			l_objPrintWriter.print(l_objJson);
			l_objPrintWriter.flush();
			l_objPrintWriter.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "index";
	}
	 

}
