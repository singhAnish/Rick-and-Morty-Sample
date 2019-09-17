package com.guestlogix.model.episode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EpisodeSchema {

  private Info info;
  private List<Result> results = null;
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  public Info getInfo() {
    return info;
  }

  public void setInfo(Info info) {
    this.info = info;
  }

  public List<Result> getResults() {
    return results;
  }

  public void setResults(List<Result> results) {
    this.results = results;
  }

  public Map<String, Object> getAdditionalProperties() {
    return this.additionalProperties;
  }

  public void setAdditionalProperty(String name, Object value) {
    this.additionalProperties.put(name, value);
  }

}