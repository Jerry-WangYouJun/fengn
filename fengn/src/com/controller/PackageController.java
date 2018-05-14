package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.common.CodeUtil;
import com.model.Grid;
import com.model.Packages;
import com.model.Pagination;
import com.service.PackagesService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/pac")
public class PackageController {

	@Autowired
	PackagesService service;

	@RequestMapping("/query")
	public void quetyList(Packages pac, HttpSession session, String pageNo, String pageSize,
			HttpServletResponse response) {
		// System.out.println(userName);
		Grid grid = new Grid();
		Pagination page = new Pagination(pageNo, pageSize, 100);
		CodeUtil.initPagination(page);
		List<Packages> list = service.queryList(pac, page);
		grid.setTotal(Long.valueOf(service.queryCardTotal(pac)));
		grid.setRows(list);
		PrintWriter out;
		try {
			response.setContentType("text/html;charset=UTF-8");
			out = response.getWriter();
			JSONObject json = new JSONObject();
			json = JSONObject.fromObject(grid);
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/addInit")
	public ModelAndView addInit() {
		ModelAndView mv = new ModelAndView("/agent/user/pac_add");
		Packages pac = new Packages();
		mv.addObject("pac", pac);
		return mv;
	}

	@RequestMapping("/insert")
	public void insert(Packages pac, HttpSession session, HttpServletResponse response) {
		if (pac.getId() != null && pac.getId() > 0) {
			service.update(pac);
		} else {
			service.insert(pac);
		}
		response.setContentType("text/text;charset=UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			JSONObject json = new JSONObject();
			json.put("msg", "操作成功");
			json.put("success", true);
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/updateInit/{id}")
	public ModelAndView updateInit(@PathVariable("id") Integer id) {
		ModelAndView mv = new ModelAndView("/agent/user/pac_add");
		Packages pac = new Packages();
		pac.setId(id);
		List<Packages> list = service.queryList(pac, new Pagination());
		mv.addObject("pac", list.get(0));
		return mv;
	}

	@RequestMapping(value = "/pac_delete/{id}")
	public void delete(@PathVariable("id") Integer id, HttpServletResponse response) {
		response.setContentType("text/text;charset=UTF-8");
		PrintWriter out;
		try {
			service.delete(id);
			out = response.getWriter();
			JSONObject json = new JSONObject();
			json.put("msg", "操作成功");
			json.put("success", true);
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
