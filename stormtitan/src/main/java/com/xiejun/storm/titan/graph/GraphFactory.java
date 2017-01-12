package com.xiejun.storm.titan.graph;

import java.util.Map;

import com.tinkerpop.blueprints.Graph;

public interface GraphFactory {
	public Graph makeGraph(Map conf);

}
