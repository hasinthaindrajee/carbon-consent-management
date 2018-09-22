package org.wso2.carbon.consent.mgt.endpoint.dto;


import io.swagger.annotations.*;
import com.fasterxml.jackson.annotation.*;

import javax.validation.constraints.NotNull;





@ApiModel(description = "")
public class PurposeEntityAssociationGetResponseDTO  {
  
  
  
  private String associationId = null;
  
  
  private String purposeId = null;
  
  
  private Integer tenantId = null;
  
  
  private String externalEntityName = null;
  
  
  private String externalEntityType = null;

  
  /**
   * Purpose entity association ID
   **/
  @ApiModelProperty(value = "Purpose entity association ID")
  @JsonProperty("associationId")
  public String getAssociationId() {
    return associationId;
  }
  public void setAssociationId(String associationId) {
    this.associationId = associationId;
  }

  
  /**
   * Id of the purpose which is associated with the external entity
   **/
  @ApiModelProperty(value = "Id of the purpose which is associated with the external entity")
  @JsonProperty("purposeId")
  public String getPurposeId() {
    return purposeId;
  }
  public void setPurposeId(String purposeId) {
    this.purposeId = purposeId;
  }

  
  /**
   * Tenant Id which is relevant to the association
   **/
  @ApiModelProperty(value = "Tenant Id which is relevant to the association")
  @JsonProperty("tenantId")
  public Integer getTenantId() {
    return tenantId;
  }
  public void setTenantId(Integer tenantId) {
    this.tenantId = tenantId;
  }

  
  /**
   * The name of the external entity which is associated
   **/
  @ApiModelProperty(value = "The name of the external entity which is associated")
  @JsonProperty("externalEntityName")
  public String getExternalEntityName() {
    return externalEntityName;
  }
  public void setExternalEntityName(String externalEntityName) {
    this.externalEntityName = externalEntityName;
  }

  
  /**
   * The type of the external entity which is associated
   **/
  @ApiModelProperty(value = "The type of the external entity which is associated")
  @JsonProperty("externalEntityType")
  public String getExternalEntityType() {
    return externalEntityType;
  }
  public void setExternalEntityType(String externalEntityType) {
    this.externalEntityType = externalEntityType;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class PurposeEntityAssociationGetResponseDTO {\n");
    
    sb.append("  associationId: ").append(associationId).append("\n");
    sb.append("  purposeId: ").append(purposeId).append("\n");
    sb.append("  tenantId: ").append(tenantId).append("\n");
    sb.append("  externalEntityName: ").append(externalEntityName).append("\n");
    sb.append("  externalEntityType: ").append(externalEntityType).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
