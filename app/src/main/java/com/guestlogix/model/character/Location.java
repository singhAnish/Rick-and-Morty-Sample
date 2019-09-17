package com.guestlogix.model.character;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.HashMap;
import java.util.Map;

public class Location implements Parcelable
{

  private String name;
  private String url;
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();
  public final static Parcelable.Creator<Location> CREATOR = new Creator<Location>() {


    @SuppressWarnings({
        "unchecked"
    })
    public Location createFromParcel(Parcel in) {
      return new Location(in);
    }

    public Location[] newArray(int size) {
      return (new Location[size]);
    }

  }
      ;

  protected Location(Parcel in) {
    this.name = ((String) in.readValue((String.class.getClassLoader())));
    this.url = ((String) in.readValue((String.class.getClassLoader())));
    this.additionalProperties = ((Map<String, Object> ) in.readValue((Map.class.getClassLoader())));
  }

  public Location() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Map<String, Object> getAdditionalProperties() {
    return this.additionalProperties;
  }

  public void setAdditionalProperty(String name, Object value) {
    this.additionalProperties.put(name, value);
  }

  public void writeToParcel(Parcel dest, int flags) {
    dest.writeValue(name);
    dest.writeValue(url);
    dest.writeValue(additionalProperties);
  }

  public int describeContents() {
    return 0;
  }

}