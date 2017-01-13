package com.xiejun.storm.titan.stormtitan;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.kafka.BrokerHosts;
import org.apache.storm.kafka.StringScheme;
import org.apache.storm.kafka.ZkHosts;
import org.apache.storm.kafka.trident.OpaqueTridentKafkaSpout;
import org.apache.storm.kafka.trident.TridentKafkaConfig;
import org.apache.storm.spout.MultiScheme;
import org.apache.storm.spout.SchemeAsMultiScheme;
import org.apache.storm.trident.Stream;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.state.StateFactory;
import org.apache.storm.tuple.Fields;

import com.xiejun.storm.titan.function.JsonProjectFunction;
import com.xiejun.storm.titan.graph.GraphFactory;
import com.xiejun.storm.titan.graph.GraphStateFactory;
import com.xiejun.storm.titan.graph.GraphUpdate;
import com.xiejun.storm.titan.graph.TitanGraphFactory;
import com.xiejun.storm.titan.graph.TweetGraphTupleProcessor;

public class TwitterGraphTopology {
	
	public static StormTopology buildTopology(){
		TridentTopology topology = new TridentTopology();
		
		BrokerHosts zk = new ZkHosts("192.168.1.114");
		
		TridentKafkaConfig spoutConf = new TridentKafkaConfig(zk, "ktest");
		
		spoutConf.scheme = (MultiScheme) new StringScheme();
		
//		spoutConf.scheme = new SchemeAsMultiScheme(new StringScheme());
		
		spoutConf.startOffsetTime = -2;
		
		OpaqueTridentKafkaSpout spout = new OpaqueTridentKafkaSpout(spoutConf);
		
		Stream spoutStream = topology.newStream("kafka-stream", spout);
		
		Fields jsonFields = new Fields("timestamp", "message");
		
		Stream parsedStream = spoutStream.each(spoutStream.getOutputFields(), new JsonProjectFunction(jsonFields), jsonFields);
		
		parsedStream = parsedStream.project(jsonFields);
		
		GraphFactory graphFactory = new TitanGraphFactory();
		
		GraphUpdate graphUpdate = new GraphUpdate(new TweetGraphTupleProcessor());
		
		StateFactory stateFactory = new GraphStateFactory(graphFactory);
		
		parsedStream.partitionPersist(stateFactory, parsedStream.getOutputFields(), graphUpdate, new Fields());
		
		return topology.build();
		
	}
	
	public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException, AuthorizationException{
		Config conf = new Config();
		
		conf.put(TitanGraphFactory.STORAGE_BACKEND, "cassandra");
		
		conf.put(TitanGraphFactory.STORAGE_HOSTNAME, "localhost");
		
		conf.setMaxSpoutPending(5);
		
		if(args.length == 0){
			LocalCluster cluster = new LocalCluster();
			
			cluster.submitTopology("twitter-analysis", conf, buildTopology());
		}else{
			conf.setNumWorkers(3);
			StormSubmitter.submitTopology(args[0], conf, buildTopology());
		}
		
		
		
	}

}
