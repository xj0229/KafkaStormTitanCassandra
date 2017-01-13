package com.xiejun.storm.titan.graph;

import java.util.List;

import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.state.BaseStateUpdater;
import org.apache.storm.trident.tuple.TridentTuple;

public class GraphUpdate extends BaseStateUpdater<GraphState>{
	
	private GraphTupleProcessor processor;
	
	public GraphUpdate(GraphTupleProcessor processor){
		this.processor = processor;
	}

	public void updateState(GraphState state, List<TridentTuple> tuples, TridentCollector collector) {
		// TODO Auto-generated method stub
		state.update(tuples, collector, this.processor);
	}

}
