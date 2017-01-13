package com.xiejun.storm.titan.graph;

import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.tuple.TridentTuple;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

public class TweetGraphTupleProcessor implements GraphTupleProcessor{

	public void process(Graph g, TridentTuple tuple, TridentCollector collector) {
		// TODO Auto-generated method stub
		Long timestamp = tuple.getLong(0);
		
		JSONObject json = (JSONObject)tuple.get(1);
		
		Vertex user = findOrCreateUser(g, (String)json.get("user"), (String)json.get("name"));
		
		JSONArray hashtags = (JSONArray) json.get("hashtags");
		
		for(int i = 0; i < hashtags.size(); i++){
			Vertex v = finderOrCreateVertex(g, "hashtag", ((String)hashtags.get(i)).toLowerCase());
			
			createEdgeAtTime(g, user, v, "mentions", timestamp);
			
		}
		
		
	}

}
