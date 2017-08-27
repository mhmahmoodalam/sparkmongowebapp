package com.madie.webspark;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

public class WebSparkStyle {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final Configuration configuration= new Configuration();
		configuration.setClassForTemplateLoading(WebSparkStyle.class, "/");
		Spark.get("/",new Route() {
			
			public Object handle(Request arg0, Response arg1) throws Exception {
				StringWriter stringWriter= new StringWriter();
				Template helloTemplate= configuration.getTemplate("helloworl.ftl");
				Map<String,Object> templateMap= new HashMap<String, Object>();
				templateMap.put("name","madie");
				
				helloTemplate.process(templateMap, stringWriter);
				return stringWriter;
			}
		});
	}

}
