package com.pc.demo.easy.rule.rules;

import java.util.Objects;

import org.jeasy.rules.annotation.Priority;

public abstract class BaseRule {

  private int priority = Integer.MAX_VALUE;

  public BaseRule(int priority) {
    super();
    this.priority = priority;
  }

  @Override
  public boolean equals(Object obj) {
    if ( Objects.isNull(obj)) {
      return false;
    }
    System.out.println(this.getClass().getName());
    System.out.println(obj.getClass().getName());
    if (!Objects.equals(this.getClass().getName(), obj.getClass().getName())) {
      return false;
    }
    
    if (obj instanceof BaseRule) {
      BaseRule baseRule = (BaseRule) obj;
      return baseRule.getPriority() == this.priority;
    }

    return false;
  }

  @Override
  public int hashCode() {
    return priority;
  }

  @Priority
  public int getPriority() {
    return priority;
  }
}
