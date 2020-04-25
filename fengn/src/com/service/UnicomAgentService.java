package com.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.UnicomAgentDao;
import com.model.Agent;
import com.model.Pagination;
import com.model.QueryData;
import com.model.TreeNode;

@Service
public class UnicomAgentService {
	  
	 @Autowired
	 UnicomAgentDao dao ;
	 Map<Integer , List<Agent>> mapTree = new HashMap<>();

	public List<Agent> queryList(QueryData qo, Pagination page) {
		return dao.queryList(qo,page);
	}

	public List<TreeNode> getTreeData(Integer agentid  , String urlType, HttpServletRequest request) {
		QueryData qo = new QueryData();
		 List<Agent> agentList = dao.queryList(qo, new Pagination());
		 mapTree  = getMap(agentList);
		 return getNodeList(agentList , agentid , urlType , request);
	}

	private List<TreeNode> getNodeList(List<Agent> agentList ,Integer agentId , String urlType, HttpServletRequest request) {
		List<TreeNode> nodeList = new ArrayList<>();
		for(Agent agent : agentList){
			  if(agentId.equals(agent.getId())){
				  TreeNode  node =  new TreeNode();
				  node.setId(agent.getId()+"");
				  node.setText(agent.getName());
//				  String timeType = "" ;
//				  if("kickback".equals(urlType)) {
//						timeType = "?timeType=0";
//				  }
				  node.getAttributes().setPriUrl(request.getContextPath() +  "/treeindex/" + urlType + "/" + agent.getId() ); 
				  List<Agent> firstListTemp = new ArrayList<>();
				  firstListTemp =mapTree.get(agentId);
				  if(firstListTemp !=null && firstListTemp.size() > 0){
					  node.setChildren(agentTreeData(firstListTemp , urlType , request));
				  }
				  nodeList.add(node);
			  }
		}
		return nodeList;
	}
	
	private  List<TreeNode>  agentTreeData(List<Agent> listTemp  , String urlType , HttpServletRequest request){
			List<TreeNode> nodeList = new ArrayList<>();
			 for(Agent agent : listTemp){
				 TreeNode tn = new TreeNode();
					tn.setId(agent.getId() + "");
					tn.setText(agent.getName());
					String timeType = "" ;
					if("kickback".equals(urlType)) {
						timeType = "?timeType=0";
					}
					tn.getAttributes().setPriUrl(request.getContextPath() + "/treeindex/" + urlType + "/" + agent.getId() + timeType); 
					if(mapTree.containsKey(agent.getId())){
						tn.setChildren(agentTreeData(mapTree.get(agent.getId()), urlType ,request) );
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

	public void updateCardAgent(String iccids, String agentid , String table ) {
		   dao.updateCardAgent(iccids,agentid , table );
	}
	
	public void updateCardAgent(String iccids, String agentid ) {
		   dao.updateCardAgent(iccids,agentid  );
	}
	

	public int queryTatol(QueryData qo) {
		return dao.queryTotal(qo);
	}

	public void updateOrderStatus(Integer id) {
		dao.updateOrderStatus(id);
	}


	public void updateCardAgent(String iccids, String agentid, String table, String pacId) {
		dao.updateCardAgent(iccids,agentid , table,pacId );
	}
}
