package com.pc.demo.easy.rule.utils;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;

import com.pc.demo.easy.rule.container.MapRuleStorage;
import com.pc.demo.easy.rule.container.RuleStorage;
import com.pc.demo.easy.rule.rules.BaseRule;

public final class DynamicRuleFactory {

  private DynamicRuleFactory() {

  }

  private RuleStorage ruleStorage = new MapRuleStorage();

  public static DynamicRuleFactory getRuleInstance() {
    return RuleFactoryHolder.instance;
  }

  private static class RuleFactoryHolder {
    static DynamicRuleFactory instance = new DynamicRuleFactory();
  }

  public boolean addRuleToStorage(String groupName, String codeSource) {
    try {
      BaseRule rule = DynamicRuleUtils.getRuleInstance(codeSource);
      return ruleStorage.add(groupName, rule);
    } catch (Exception e) {
      return false;
    }
  }

  public boolean removeRuleFromStorage(String groupName, String codeSource) {
    try {
      BaseRule rule = DynamicRuleUtils.getRuleInstance(codeSource);
      if (ruleStorage.contains(groupName, rule) && !ruleStorage.remove(groupName, rule)) {
        return false;
      }
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public boolean addRuleToStorage(String groupName, BaseRule rule) {
    try {
      return ruleStorage.add(groupName, rule);
    } catch (Exception e) {
      return false;
    }
  }

  public boolean removeRuleFromStorage(String groupName, BaseRule rule) {
    try {
      if (ruleStorage.contains(groupName, rule) && !ruleStorage.remove(groupName, rule)) {
        return false;
      }
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public Builder builder() {
    return new Builder(this);
  }

  public class Builder {
    private Rules rules = new Rules();
    private Facts facts = new Facts();
    private RulesEngine engine = new DefaultRulesEngine();
    private RuleStorage ruleStorage;

    public Builder(DynamicRuleFactory dynamicRuleFactory) {
      ruleStorage = dynamicRuleFactory.ruleStorage;
    }

    public Builder setParameter(String name, Object value) {
      facts.put(name, value);
      return this;
    }

    public Builder setParameter(Map<String, Object> map) {
      if (map == null || map.isEmpty()) {
        return this;
      }

      for (Entry<String, Object> entry : map.entrySet()) {
        facts.put(entry.getKey(), entry.getValue());
      }
      return this;
    }

    public Builder addRuleGroup(String groupName) {
      Collection<BaseRule> rs = ruleStorage.listObjByGroup(groupName);
      rs.stream().forEach(rules::register);
      return this;
    }

    public Builder run() {
      engine.fire(rules, facts);
      return this;
    }

    public <T> T getParameter(String pName, Class<T> pType) {
      return facts.get(pName);
    }
  }

}
