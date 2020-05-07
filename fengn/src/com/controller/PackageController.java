package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model.InfoPackage;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONString;
import net.sf.json.util.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.common.CodeUtil;
import com.dao.PackageMapper;
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
	@Autowired
	PackageMapper pacDao;

	@ResponseBody
	@RequestMapping("/query")
	public Grid quetyList(Packages pac, HttpSession session, String pageNo, String pageSize,
			HttpServletResponse response) {
		// System.out.println(userName);
		String agentId = session.getAttribute("agentId").toString();
		pac.setAgentId(agentId);
		Pagination page = new Pagination(pageNo, pageSize, 100);
		CodeUtil.initPagination(page);
		List<Packages> list = service.queryList(pac, page);
		int total = service.queryCardTotal(pac);
		Grid grid = new Grid();
		grid.setRows(list);
		grid.setTotal((long)total);
		return grid;
		//return list;
	}


    @RequestMapping("/getPacList")
    public void getPacList(HttpSession session, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        PrintWriter out;
        try {
            String agentId = session.getAttribute("agentId").toString();
            List<Packages> list = service.getPacListByAgentId(agentId);
            String jsonStr = JSONArray.fromObject(list).toString();

            out = response.getWriter();
            JSONObject json = new JSONObject();
            json.put("success", true);
            json.put("dataInfo", list);
            out.println(json);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @RequestMapping("/getPacAll")
    public void getPacAll(HttpSession session, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        PrintWriter out;
        try {
            List<Packages> list = pacDao.selectAll();
            out = response.getWriter();
            JSONObject json = new JSONObject();
            json.put("success", true);
            json.put("dataInfo", list);
            out.println(json);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	@RequestMapping("/pac_move")
	public void pacMove(String pacids , String agentid ,String parentAgentId ,Double childcost , HttpSession session,HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			////添加逻辑 先查询代理商下面是否有相同的套餐 
			
			int count  = service.queryByPacIdAndAgentId(pacids,agentid);
			JSONObject json = new JSONObject();
			System.out.println("pacMove   count =="+count);
			if(count != 0 )
			{			
				json.put("msg", "操作失败，该运营商已经分配相同套餐，请查询后重试。");
				json.put("success", false);				
			}
			else
			{
				parentAgentId = session.getAttribute("agentId").toString();
				service.insertPacRef(pacids,agentid,parentAgentId  , childcost);
				json.put("msg", "操作成功");
				json.put("success", true);
			}
			out = response.getWriter();			
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

	@RequestMapping("/pac_edit")
	public void insert(Packages pac, HttpSession session, HttpServletResponse response) {
        String agentId = session.getAttribute("agentId").toString();
        pac.setAgentId(agentId);
		if (pac.getId() != null && pac.getId() > 0) {
		    if("1".equals(agentId)){
                service.update(pac);
            }
			service.updateChildsPacRef(pac);
		} else {
			service.insert(pac);
            service.insertChildsPacRef(pac);
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

	@RequestMapping(value = "/pac_delete")
	public void delete(Integer id, HttpServletResponse response) {
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
