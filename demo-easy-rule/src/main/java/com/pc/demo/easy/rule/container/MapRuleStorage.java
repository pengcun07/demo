package com.pc.demo.easy.rule.container;

import java.util.Collection;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.pc.demo.easy.rule.rules.BaseRule;

public class MapRuleStorage implements RuleStorage {

  private final Multimap<String, BaseRule> map = HashMultimap.create();

  @Override
  public boolean contains(String groupName, BaseRule rule) {
    return map.containsEntry(groupName, rule);
  }

  @Override
  public boolean add(String groupName, BaseRule rule) {
    if (map.containsEntry(groupName, rule)) {
      map.remove(groupName, rule);
    }
    return map.put(groupName, rule);
  }

  @Override
  public boolean remove(String groupName, BaseRule rule) {
    return map.remove(groupName, rule);
  }

  @Override
  public boolean remove(String group) {
    return !map.removeAll(group).isEmpty();
  }

  @Override
  public Collection<BaseRule> listObjByGroup(String group) {
    return map.get(group);
  }

}
