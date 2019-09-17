package com.guestlogix.model.character;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CharacterSchema implements Parcelable {

  private Integer id;
  private String name;
  private String status;
  private String species;
  private String type;
  private String gender;
  private Origin origin;
  private Location location;
  private String image;
  private List<String> episode = null;
  private String url;
  private String created;
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();
  public final static Parcelable.Creator<CharacterSchema> CREATOR = new Creator<CharacterSchema>() {

    @SuppressWarnings({
        "unchecked"
    })
    public CharacterSchema createFromParcel(Parcel in) {
      return new CharacterSchema(in);
    }

    public CharacterSchema[] newArray(int size) {
      return (new CharacterSchema[size]);
    }
  };

  protected CharacterSchema(Parcel in) {
    this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
    this.name = ((String) in.readValue((String.class.getClassLoader())));
    this.status = ((String) in.readValue((String.class.getClassLoader())));
    this.species = ((String) in.readValue((String.class.getClassLoader())));
    this.type = ((String) in.readValue((String.class.getClassLoader())));
    this.gender = ((String) in.readValue((String.class.getClassLoader())));
    this.origin = ((Origin) in.readValue((Origin.class.getClassLoader())));
    this.location = ((Location) in.readValue((Location.class.getClassLoader())));
    this.image = ((String) in.readValue((String.class.getClassLoader())));
    in.readList(this.episode, (java.lang.String.class.getClassLoader()));
    this.url = ((String) in.readValue((String.class.getClassLoader())));
    this.created = ((String) in.readValue((String.class.getClassLoader())));
    this.additionalProperties = ((Map<String, Object>) in.readValue((Map.class.getClassLoader())));
  }

  public CharacterSchema() {
  }

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

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getSpecies() {
    return species;
  }

  public void setSpecies(String species) {
    this.species = species;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public Origin getOrigin() {
    return origin;
  }

  public void setOrigin(Origin origin) {
    this.origin = origin;
  }

  public Location getLocation() {
    return location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public List<String> getEpisode() {
    return episode;
  }

  public void setEpisode(List<String> episode) {
    this.episode = episode;
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

  public void writeToParcel(Parcel dest, int flags) {
    dest.writeValue(id);
    dest.writeValue(name);
    dest.writeValue(status);
    dest.writeValue(species);
    dest.writeValue(type);
    dest.writeValue(gender);
    dest.writeValue(origin);
    dest.writeValue(location);
    dest.writeValue(image);
    dest.writeList(episode);
    dest.writeValue(url);
    dest.writeValue(created);
    dest.writeValue(additionalProperties);
  }

  public int describeContents() {
    return 0;
  }


  public static final DiffUtil.ItemCallback<CharacterSchema> DIFF_CALLBACK =
      new DiffUtil.ItemCallback<CharacterSchema>() {
        @Override
        public boolean areItemsTheSame(@NonNull CharacterSchema oldItem,
            @NonNull CharacterSchema newItem) {
          return oldItem.id.equals(newItem.id);
        }

        @Override
        public boolean areContentsTheSame(@NonNull CharacterSchema oldItem,
            @NonNull CharacterSchema newItem) {
          return oldItem.equals(newItem);
        }
      };

  @SuppressWarnings("EqualsWhichDoesntCheckParameterClass") @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }

    CharacterSchema result = (CharacterSchema) obj;
    return result.id.equals(this.id);
  }
}