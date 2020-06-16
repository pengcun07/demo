package com.pc.demo.easy.rule.utils;

import com.pc.demo.easy.rule.rules.BaseRule;

import groovy.lang.GroovyClassLoader;

public final class DynamicRuleUtils {

  private DynamicRuleUtils() {}
  
  /**
   * groovy class loader
   */
  private static GroovyClassLoader groovyClassLoader = new GroovyClassLoader();
  
  /**
   * 获取规则实例对象
   **/
  public static BaseRule getRuleInstance(String codeSource) throws Exception {
    Class<?> clazz = groovyClassLoader.parseClass(codeSource);
    if (clazz != null) {
      Object instance = clazz.newInstance();
      if (instance!=null) {
        if (instance instanceof BaseRule) {
          return (BaseRule) instance;
        } else {
          throw new IllegalArgumentException(">>>>>>>>>>> RuleFactory getRuleInstance error, "
              + "cannot convert from instance["+ instance.getClass() +"] to BaseRule");
        }
      }
    }
    throw new IllegalArgumentException(">>>>>>>>>>> RuleFactory, getRuleInstance error, instance is null");
  }
}
