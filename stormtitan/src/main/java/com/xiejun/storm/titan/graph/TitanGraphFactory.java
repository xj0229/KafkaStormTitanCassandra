package com.xiejun.storm.titan.graph;

import java.util.Map;

import org.apache.commons.configuration.BaseConfiguration;
import org.apache.commons.configuration.Configuration;

import com.thinkaurelius.titan.core.TitanFactory;
import com.tinkerpop.blueprints.Graph;

public class TitanGraphFactory implements GraphFactory{
	
	public static final String STORAGE_BACKEND = "titan.storage.backend";
	
	public static final String STORAGE_HOSTNAME = "titan.storage.hostname";

	public Graph makeGraph(Map conf) {
		// TODO Auto-generated method stub
		Configuration graphConf = new BaseConfiguration();
		
		graphConf.setProperty("storage.backend", conf.get(STORAGE_BACKEND));
		
		graphConf.setProperty("storage.hostname", conf.get(STORAGE_HOSTNAME));
		
		return (Graph)TitanFactory.open(graphConf);
	}

}
