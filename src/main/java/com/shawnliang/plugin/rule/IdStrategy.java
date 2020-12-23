package com.shawnliang.plugin.rule;

public enum IdStrategy {
  auto("AUTO"),
  id_worker("ID_WORKER"),
  uuid("UUID"),
  input("INPUT");

  private String value;

  IdStrategy(String value) {
    this.value = value;
  }

  public String getValue() {
    return this.value;
  }
}
