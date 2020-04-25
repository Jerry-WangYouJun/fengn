package com.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.AgentDao;
import com.dao.AgentMapper;
import com.model.Agent;
import com.model.Pagination;
import com.model.QueryData;
import com.model.TreeNode;

@Service
public class AgentService {
	  
	 @Autowired
	 AgentDao dao ;
	 @Autowired
	 AgentMapper mapper;
	 
	 Map<Integer , List<Agent>> mapTree = new HashMap<>();

	public List<Agent> queryList(QueryData qo, Pagination page) {
		return dao.queryList(qo,page );
	}

	public int insert(Agent agent ) {
		 Integer parentId = dao.queryPrentIdByCode(agent.getCode());
		 agent.setParentId(parentId);
		 agent.setCode(dao.getMaxCode(agent.getCode(), parentId));
		 return  mapper.insert(agent);
	}

	public void update(Agent agent) {
		//dao.update(agent);
		mapper.updateByPrimaryKey(agent);
	}

	public void delete(Integer id) {
		dao.delete(id);
	}

	public List<TreeNode> getTreeData(Integer agentid  , String urlType , String  groupType, HttpServletRequest request) {
		 List<Agent> agentList = dao.queryTreeList(urlType);
		 mapTree  = getMap(agentList);
		 return getNodeList(agentList , agentid , urlType , groupType ,  request);
	}

	private List<TreeNode> getNodeList(List<Agent> agentList ,Integer agentId , String urlType, String  groupType, HttpServletRequest request) {
		List<TreeNode> nodeList = new ArrayList<>();
		for(Agent agent : agentList){
			  if(agentId.equals(agent.getId())){
				  TreeNode  node =  new TreeNode();
				  node.setId(agent.getId()+"");
				  String text = "" ;
				  switch (urlType) {
				case "card":
					text = "丰宁/永思卡-"+ agent.getName() ;
					break;
				case "unicom_card":
					text = "联通卡-"+ agent.getName() ;
					break;
				case "cmcc_card":
					text = "移动卡-"+ agent.getName() ;
					break;
				case "kickback":
					text = "丰宁/永思返佣-"+ agent.getName() ;
					break;
				case "unicom_kickback":
					text = "联通返佣-"+ agent.getName() ;
					break;
				case "cmcc_kickback":
					text = "移动返佣-"+ agent.getName() ;
					break;
				case "cmoit_card":
					text = "移动卡-"+ agent.getName() ;
					break;
				case "cmoit_kickback":
					text = "移动返佣-"+ agent.getName() ;
					break;
				case "all_kickback":
				  	text = "返佣管理-"+ agent.getName() ;
				  	break;
				default:
					break;
				}
				  node.setText(text);
				  node.setMenu(agent.getName());
				  node.getAttributes().setPriUrl(request.getContextPath() +  "/" + groupType + "/" + urlType + "_list.jsp" ); 
				  node.getAttributes().setAgentId(agent.getId());
				  node.getAttributes().setUrlType(urlType);
				  List<Agent> firstListTemp = new ArrayList<>();
				  firstListTemp =mapTree.get(agentId);
				  if(firstListTemp !=null && firstListTemp.size() > 0){
					  node.setChildren(agentTreeData(firstListTemp , urlType , groupType, request));
				  }
				  nodeList.add(node);
			  }
		}
		return nodeList;
	}
	
	private  List<TreeNode>  agentTreeData(List<Agent> listTemp  , String urlType , String  groupType , HttpServletRequest request){
			List<TreeNode> nodeList = new ArrayList<>();
			 for(Agent agent : listTemp){
				 TreeNode tn = new TreeNode();
					tn.setId(agent.getId() + "");
					String text = "" ;
					  switch (urlType) {
					  case "card":
							text = "丰宁/永思卡-"+ agent.getName() ;
							break;
						case "unicom_card":
							text = "联通卡-"+ agent.getName() ;
							break;
						case "cmcc_card":
							text = "移动卡-"+ agent.getName() ;
							break;
						case "kickback":
							text = "丰宁/永思返佣-"+ agent.getName() ;
							break;
						case "unicom_kickback":
							text = "联通返佣-"+ agent.getName() ;
							break;
						case "cmcc_kickback":
							text = "联通返佣-"+ agent.getName() ;
							break;
						case "all_kickback":
						  text = "返佣管理-"+ agent.getName() ;
						  break;
						case "cmoit_card":
							text = "移动卡-"+ agent.getName() ;
							break;
						case "cmoit_kickback":
							text = "移动返佣-"+ agent.getName() ;
							break;
					default:
						break;
					}
					  tn.setText(text);
					  tn.setMenu(agent.getName());
					String timeType = "" ;
					if("kickback".equals(urlType)) {
						timeType = "?timeType=0";
					}
					tn.getAttributes().setPriUrl(request.getContextPath() +  "/" + groupType + "/" + urlType + "_list.jsp" ); 
					tn.getAttributes().setAgentId(agent.getId());
					tn.getAttributes().setUrlType(urlType);
					if(mapTree.containsKey(agent.getId())){
						tn.setChildren(agentTreeData(mapTree.get(agent.getId()), urlType , groupType, request) );
					}
					nodeList.add(tn);
			 }
		return nodeList ;
	}
	
	private Map<Integer, List<Agent>> getMap(List<Agent> agentList) {
		mapTree =  new HashMap<>(); 
		for(Agent agent : agentList){
			   Integer  parentId = agent.getParentId() ;
			   if(mapTree.containsKey(parentId)){
				   mapTree.get(parentId).add(agent);
			   }else{
				  List<Agent> listTemp = new ArrayList<>();
				  listTemp.add(agent);
				   mapTree.put(parentId, listTemp);
			   }
		}
		return mapTree;
	}

	public void updateCardAgent(String iccids, String agentid) {
		   dao.updateCardAgent(iccids,agentid );
	}

	public List<String> getTypeList() {
		List<String> list = dao.queryTypeList();
		return list;
	}

	public int queryTatol(QueryData qo) {
		return dao.queryTotal(qo);
	}

	public int checkUser(String userNo) {
		return  dao.checkUser(userNo);
	}

	public Agent getById(Integer id) {
		return mapper.selectByPrimaryKey(id);
	}
	
	 
}
