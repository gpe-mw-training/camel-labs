
package com.redhat.gpte.training.camel.model;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "code",
    "status",
    "copyright",
    "attributionText",
    "attributionHTML",
    "etag",
    "data"
})
public class Record {

    @JsonProperty("code")
    private Integer code;
    @JsonProperty("status")
    private String status;
    @JsonProperty("copyright")
    private String copyright;
    @JsonProperty("attributionText")
    private String attributionText;
    @JsonProperty("attributionHTML")
    private String attributionHTML;
    @JsonProperty("etag")
    private String etag;
    @JsonProperty("data")
    private Data data;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("code")
    public Integer getCode() {
        return code;
    }

    @JsonProperty("code")
    public void setCode(Integer code) {
        this.code = code;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("copyright")
    public String getCopyright() {
        return copyright;
    }

    @JsonProperty("copyright")
    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    @JsonProperty("attributionText")
    public String getAttributionText() {
        return attributionText;
    }

    @JsonProperty("attributionText")
    public void setAttributionText(String attributionText) {
        this.attributionText = attributionText;
    }

    @JsonProperty("attributionHTML")
    public String getAttributionHTML() {
        return attributionHTML;
    }

    @JsonProperty("attributionHTML")
    public void setAttributionHTML(String attributionHTML) {
        this.attributionHTML = attributionHTML;
    }

    @JsonProperty("etag")
    public String getEtag() {
        return etag;
    }

    @JsonProperty("etag")
    public void setEtag(String etag) {
        this.etag = etag;
    }

    @JsonProperty("data")
    public Data getData() {
        return data;
    }

    @JsonProperty("data")
    public void setData(Data data) {
        this.data = data;
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
