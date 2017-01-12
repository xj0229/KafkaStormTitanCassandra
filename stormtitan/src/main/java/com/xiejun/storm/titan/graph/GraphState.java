package com.xiejun.storm.titan.graph;

import java.util.List;

import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.state.State;
import org.apache.storm.trident.tuple.TridentTuple;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.TransactionalGraph;

public class GraphState implements State{
	
	private Graph graph;

	public GraphState(Graph graph) {
		// TODO Auto-generated constructor stub
		this.graph = graph;
	}

	public void beginCommit(Long txid) {
		// TODO Auto-generated method stub
		
	}

	public void commit(Long txid) {
		// TODO Auto-generated method stub
		if(this.graph instanceof TransactionalGraph){
			((TransactionalGraph)this.graph).commit();
		}
	}
	
	public void update(List<TridentTuple> tuples, TridentCollector collector, GraphTupleProcessor processor){
		for(TridentTuple tuple : tuples){
			processor.process(this.graph, tuple, collector);
		}
	}

}
