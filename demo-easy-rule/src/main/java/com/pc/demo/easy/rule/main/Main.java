package com.pc.demo.easy.rule.main;

import com.pc.demo.easy.rule.rules.WeatherRule;
import com.pc.demo.easy.rule.utils.DynamicRuleFactory;

public class Main {
  
  public static void main(String[] args) {
    WeatherRule rule = new WeatherRule(1);
    WeatherRule weatherRule = new WeatherRule(2);
    System.out.println(rule.equals(weatherRule));

    DynamicRuleFactory.getRuleInstance().addRuleToStorage("weather", weatherRule);
    
    DynamicRuleFactory.getRuleInstance().builder().setParameter("rain", true)
        .addRuleGroup("weather").run();

  }

}
