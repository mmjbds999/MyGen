package com.hy.tools.main;

import com.hy.tools.gen.MavenGenJsp;

/**
 * <pre>
 * 生成哦
 * </pre>
 * 
 * @author 黄云
 * 2015年12月18日
 */
public class GenMain {

	public static void main(String[] args) {
		//---------------------原始web项目------------------------
		
//		GenEntityForHibernateConf.genAll();//entity--1
//		GenDao.gen();//dao--1
//		GenVo.genVoList(false);//vo--2
//		GenService.genServiceList();//Service--2
//		GenJsp.genByVo();//jspAndAction--3
		
		
		//----------------------maven--------------------------
		
//		MavenGenEntityForHibernateConf.genAll();//entity--1//
//		MavenGenDao.gen();//dao--1
//		MavenGenVo.genVoList(false);//vo--2
//		MavenGenService.genServiceList();//Service--2
		MavenGenJsp.genByVo();//jspAndAction--3
	}
	
}
