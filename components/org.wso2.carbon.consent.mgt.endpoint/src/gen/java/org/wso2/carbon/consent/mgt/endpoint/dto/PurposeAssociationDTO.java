package org.wso2.carbon.consent.mgt.endpoint.dto;


import io.swagger.annotations.*;
import com.fasterxml.jackson.annotation.*;

import javax.validation.constraints.NotNull;





@ApiModel(description = "")
public class PurposeAssociationDTO  {
  
  
  
  private String externalEntityName = null;
  
  
  private String externalEntityType = null;

  
  /**
   * The name of the external entity
   **/
  @ApiModelProperty(value = "The name of the external entity")
  @JsonProperty("externalEntityName")
  public String getExternalEntityName() {
    return externalEntityName;
  }
  public void setExternalEntityName(String externalEntityName) {
    this.externalEntityName = externalEntityName;
  }

  
  /**
   * The type of the external entity
   **/
  @ApiModelProperty(value = "The type of the external entity")
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
    sb.append("class PurposeAssociationDTO {\n");
    
    sb.append("  externalEntityName: ").append(externalEntityName).append("\n");
    sb.append("  externalEntityType: ").append(externalEntityType).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
