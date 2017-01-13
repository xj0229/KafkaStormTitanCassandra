package com.xiejun.storm.titan.graph;

import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.tuple.TridentTuple;

import com.tinkerpop.blueprints.Graph;

public interface GraphTupleProcessor {
	
	public void process(Graph g, TridentTuple tuple, TridentCollector collector);

}
