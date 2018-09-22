package org.wso2.carbon.consent.mgt.endpoint.dto;


import io.swagger.annotations.*;
import com.fasterxml.jackson.annotation.*;

import javax.validation.constraints.NotNull;





@ApiModel(description = "")
public class PurposeEntityAssociationDTO  {
  
  
  
  private String purposeId = null;
  
  
  private String externalEntityName = null;
  
  
  private String externalEntityType = null;

  
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
    sb.append("class PurposeEntityAssociationDTO {\n");
    
    sb.append("  purposeId: ").append(purposeId).append("\n");
    sb.append("  externalEntityName: ").append(externalEntityName).append("\n");
    sb.append("  externalEntityType: ").append(externalEntityType).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
