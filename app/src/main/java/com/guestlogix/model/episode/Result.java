package com.guestlogix.model.episode;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Result {

  private Integer id;
  private String name;
  private String airDate;
  private String episode;
  private List<String> characters = null;
  private String url;
  private String created;
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAirDate() {
    return airDate;
  }

  public void setAirDate(String airDate) {
    this.airDate = airDate;
  }

  public String getEpisode() {
    return episode;
  }

  public void setEpisode(String episode) {
    this.episode = episode;
  }

  public List<String> getCharacters() {
    return characters;
  }

  public void setCharacters(List<String> characters) {
    this.characters = characters;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getCreated() {
    return created;
  }

  public void setCreated(String created) {
    this.created = created;
  }

  public Map<String, Object> getAdditionalProperties() {
    return this.additionalProperties;
  }

  public void setAdditionalProperty(String name, Object value) {
    this.additionalProperties.put(name, value);
  }


  public static final DiffUtil.ItemCallback<Result> DIFF_CALLBACK =
      new DiffUtil.ItemCallback<Result>() {
        @Override
        public boolean areItemsTheSame(@NonNull Result oldItem, @NonNull Result newItem) {
          return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Result oldItem, @NonNull Result newItem) {
          return oldItem.equals(newItem);
        }
      };

  @SuppressWarnings("EqualsWhichDoesntCheckParameterClass") @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }

    Result result = (Result) obj;
    return result.id == this.id;
  }
}