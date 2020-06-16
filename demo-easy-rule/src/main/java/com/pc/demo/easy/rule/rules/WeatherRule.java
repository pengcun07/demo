package com.pc.demo.easy.rule.rules;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;

@Rule(name = "weather rule", description = "if it rains then take an umbrella")
public class WeatherRule extends BaseRule{

  public WeatherRule(int priority) {
    super(priority);
  }


  @Condition
  public boolean itRains(@Fact("rain") boolean rain) {
    return rain;
  }


  @Action
  public void takeAnUmbrella() {
    System.out.println("It rains, take an umbrella! and priority is " + getPriority());
  }
  
}
