package com.xiejun.storm.titan.graph;

import java.util.Map;

import org.apache.storm.task.IMetricsContext;
import org.apache.storm.trident.state.State;
import org.apache.storm.trident.state.StateFactory;

import com.tinkerpop.blueprints.Graph;

public class GraphStateFactory implements StateFactory{
	
	private GraphFactory factory;
	
	public GraphStateFactory(GraphFactory factory){
		this.factory = factory;
	}

	public State makeState(Map conf, IMetricsContext metrics, int partitionIndex, int numPartitions) {
		// TODO Auto-generated method stub
		Graph graph = this.factory.makeGraph(conf);
		
		State state = new GraphState(graph);
		
		return null;
	}

}
