package com.poc.thorntail.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.poc.thorntail.dbentities.GreetingEntity;
//import javax.persistence.EntityManager;
//
//import com.poc.thorntail.dbentities.GreetingEntity;
import com.poc.thorntail.model.Greeting;
import com.poc.thorntail.resource.JPAFactory;

@ApplicationScoped
public class GreetingService {
	
	@Inject
	  private JPAFactory jpaFactory;
	
	public List<Greeting> getGreetings(String dbUserName , String dbPassword){
		
		List<Greeting> greetingList = null;
        greetingList = useJDBC(greetingList, dbUserName,dbPassword);
        
       useJPA(greetingList);
        return greetingList;
	}

	private void useJPA(List<Greeting> greetingList) {
		//String suffix = name != null ? name : "World";
        EntityManager em = jpaFactory.getEntityManager();
        System.out.println("em==="+em);
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(GreetingEntity.class));
        List<GreetingEntity> greetings = em.createQuery(cq).getResultList();
        System.out.println("greetingList==="+greetings);
        for(GreetingEntity dbEntity : greetings) {
        	Greeting greeting = new Greeting(dbEntity.getId()+ " "+dbEntity.getGreeting());
        	greetingList.add(greeting);
        }
	}

	private List<Greeting> useJDBC(List<Greeting> greetingList,String dbUserName , String dbPassword) {
		try {
        Class.forName("org.postgresql.Driver");
        System.out.println("dbUserName="+dbUserName+"dbPassword="+dbPassword);
        Connection conn = DriverManager.getConnection("jdbc:postgresql://172.30.9.14:5432/sampledb", dbUserName, dbPassword);
        System.out.println("conn====="+conn);
        String sqlStmt = "select id,greeting from greeting";
        Statement stmt = conn.createStatement();
        System.out.println("stmt====="+stmt);
        ResultSet rs = stmt.executeQuery(sqlStmt);
        System.out.println("rs====="+rs);
        greetingList = new ArrayList<Greeting>();
        while (rs.next()) {
                Greeting greeting = new Greeting(rs.getInt(1)+ " "+rs.getString(2));
                greetingList.add(greeting);
        }
        rs.close();
        conn.close();
        }catch(Exception e) {
        	e.printStackTrace();
        }
		return greetingList;
	}
		

}
