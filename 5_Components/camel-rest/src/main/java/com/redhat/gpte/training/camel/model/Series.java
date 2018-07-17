
package com.redhat.gpte.training.camel.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.*;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "available",
    "collectionURI",
    "items",
    "returned"
})
public class Series {

    @JsonProperty("available")
    private Integer available;
    @JsonProperty("collectionURI")
    private String collectionURI;
    @JsonProperty("items")
    private List<ItemSeries> items = new ArrayList<ItemSeries>();
    @JsonProperty("returned")
    private Integer returned;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("available")
    public Integer getAvailable() {
        return available;
    }

    @JsonProperty("available")
    public void setAvailable(Integer available) {
        this.available = available;
    }

    @JsonProperty("collectionURI")
    public String getCollectionURI() {
        return collectionURI;
    }

    @JsonProperty("collectionURI")
    public void setCollectionURI(String collectionURI) {
        this.collectionURI = collectionURI;
    }

    @JsonProperty("items")
    public List<ItemSeries> getItems() {
        return items;
    }

    @JsonProperty("items")
    public void setItems(List<ItemSeries> items) {
        this.items = items;
    }

    @JsonProperty("returned")
    public Integer getReturned() {
        return returned;
    }

    @JsonProperty("returned")
    public void setReturned(Integer returned) {
        this.returned = returned;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
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
