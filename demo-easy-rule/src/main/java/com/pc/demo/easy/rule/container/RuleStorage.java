package com.pc.demo.easy.rule.container;

import java.util.Collection;

import com.pc.demo.easy.rule.rules.BaseRule;

public interface RuleStorage {

  boolean contains(String groupName, BaseRule rule);

  boolean add(String groupName, BaseRule rule);

  boolean batchAdd(String groupName, Iterable<? extends BaseRule> rules);

  boolean remove(String groupName, BaseRule rule);

  boolean remove(String group);

  Collection<BaseRule> listObjByGroup(String group);

}
