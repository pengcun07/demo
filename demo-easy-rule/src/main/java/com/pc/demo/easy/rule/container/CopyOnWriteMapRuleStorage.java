package com.pc.demo.easy.rule.container;

import java.util.Collection;

import com.pc.demo.easy.rule.rules.BaseRule;
import com.pc.demo.easy.rule.utils.CopyOnWriteMultimap;

public class CopyOnWriteMapRuleStorage implements RuleStorage {
  
  private final CopyOnWriteMultimap<String, BaseRule> multimap = new CopyOnWriteMultimap<String, BaseRule>();
  
  @Override
  public boolean contains(String groupName, BaseRule rule) {
    return multimap.containsValue(groupName, rule);
  }

  @Override
  public boolean add(String groupName, BaseRule rule) {
    if (multimap.containsValue(groupName, rule)) {
      multimap.removeValue(groupName, rule);
    }
    return multimap.put(groupName, rule);
  }

  @Override
  public boolean remove(String groupName, BaseRule rule) {
    if (multimap.containsValue(groupName, rule)) {
      return multimap.removeValue(groupName, rule);
    }
    return false;
  }

  @Override
  public boolean remove(String groupName) {
    return multimap.removeKey(groupName) != null;
  }

  @Override
  public Collection<BaseRule> listObjByGroup(String groupName) {
    return multimap.get(groupName);
  }

}
