package com.hy.tools.main;

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
//		MavenGenDao.gen();   //dao--1
		
		//第一次生成VoList时，记得将参数设置true(参数为true时：在src目录下生成正式使用的VoList，参数为false时：在项目根目录的gen目录下面生成临时的VOList)
//		MavenGenVo.genVoList(false);    //vo--2
//		MavenGenForms.genForms();    //forms--3
//		MavenGenService.genServiceList();    //Service--4
//		MavenGenJsp.genByVo();    //jspAndAction--5
	}
	
}
