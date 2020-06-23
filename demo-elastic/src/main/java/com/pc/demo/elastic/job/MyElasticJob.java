package com.pc.demo.elastic.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

public class MyElasticJob implements SimpleJob {

  @Override
  public void execute(ShardingContext shardingContext) {
    System.out.println("start");
    switch (shardingContext.getShardingItem()) {
      case 0:
        // do something by sharding item 0
        System.out.println("do something by sharding item 0");
        break;
      case 1:
        // do something by sharding item 1
        System.out.println("do something by sharding item 1");
        break;
      case 2:
        // do something by sharding item 2
        System.out.println("do something by sharding item 2");
        break;
      // case n: ...
      default:
        System.out.println("do something by sharding item other");
    }

  }

}
