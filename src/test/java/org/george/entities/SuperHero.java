package org.george.entities;

import com.fasterxml.jackson.annotation.*;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "fullName",
        "birthDate",
        "city",
        "mainSkill",
        "gender",
        "phone"
})
@ToString
public class SuperHero {

    @JsonProperty("id")
    private Integer id;
    @CsvBindByPosition(position = 0)
    @JsonProperty("fullName")
    private String fullName;
    @CsvBindByPosition(position = 1)
    @JsonProperty("birthDate")
    private String birthDate;
    @CsvBindByPosition(position = 2)
    @JsonProperty("city")
    private String city;
    @CsvBindByPosition(position = 3)
    @JsonProperty("mainSkill")
    private String mainSkill;
    @CsvBindByPosition(position = 4)
    @JsonProperty("gender")
    private String gender;
    @CsvBindByPosition(position = 5)
    @JsonProperty("phone")
    private String phone;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    public SuperHero(String fullName, String birthDate, String city, String mainSkill, String gender, String phone) {
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.city = city;
        this.mainSkill = mainSkill;
        this.gender = gender;
        this.phone = phone;
    }

    public SuperHero() {
    }


    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("fullName")
    public String getFullName() {
        return fullName;
    }

    @JsonProperty("fullName")
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @JsonProperty("birthDate")
    public String getBirthDate() {
        return birthDate;
    }

    @JsonProperty("birthDate")
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    @JsonProperty("city")
    public String getCity() {
        return city;
    }

    @JsonProperty("city")
    public void setCity(String city) {
        this.city = city;
    }

    @JsonProperty("mainSkill")
    public String getMainSkill() {
        return mainSkill;
    }

    @JsonProperty("mainSkill")
    public void setMainSkill(String mainSkill) {
        this.mainSkill = mainSkill;
    }

    @JsonProperty("gender")
    public String getGender() {
        return gender;
    }

    @JsonProperty("gender")
    public void setGender(String gender) {
        this.gender = gender;
    }

    @JsonProperty("phone")
    public String getPhone() {
        return phone;
    }

    @JsonProperty("phone")
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}