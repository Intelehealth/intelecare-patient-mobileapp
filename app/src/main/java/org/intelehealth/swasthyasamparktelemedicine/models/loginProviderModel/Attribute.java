package org.intelehealth.swasthyasamparktelemedicine.models.loginProviderModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created By: Prajwal Waingankar on 15-Jun-21
 * Github: prajwalmw
 * Email: prajwalwaingankar@gmail.com
 */

public class Attribute {

    @SerializedName("display")
    @Expose
    private String display;
    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("attributeType")
    @Expose
    private AttributeType attributeType;
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("voided")
    @Expose
    private Boolean voided;
    /*@SerializedName("links")
    @Expose
    private List<Link__1> links = null;*/
    @SerializedName("resourceVersion")
    @Expose
    private String resourceVersion;

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public AttributeType getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(AttributeType attributeType) {
        this.attributeType = attributeType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getVoided() {
        return voided;
    }

    public void setVoided(Boolean voided) {
        this.voided = voided;
    }

/*    public List<Link__1> getLinks() {
        return links;
    }

    public void setLinks(List<Link__1> links) {
        this.links = links;
    }*/

    public String getResourceVersion() {
        return resourceVersion;
    }

    public void setResourceVersion(String resourceVersion) {
        this.resourceVersion = resourceVersion;
    }

}
