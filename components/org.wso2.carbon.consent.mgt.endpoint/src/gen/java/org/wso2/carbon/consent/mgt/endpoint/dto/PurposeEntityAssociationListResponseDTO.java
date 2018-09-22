package org.wso2.carbon.consent.mgt.endpoint.dto;

import java.util.ArrayList;
import java.util.List;
import org.wso2.carbon.consent.mgt.endpoint.dto.PurposeEntityAssociationGetResponseDTO;

import io.swagger.annotations.*;
import com.fasterxml.jackson.annotation.*;

import javax.validation.constraints.NotNull;





@ApiModel(description = "")
public class PurposeEntityAssociationListResponseDTO  {
  
  
  
  private List<PurposeEntityAssociationGetResponseDTO> associations = new ArrayList<PurposeEntityAssociationGetResponseDTO>();

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("associations")
  public List<PurposeEntityAssociationGetResponseDTO> getAssociations() {
    return associations;
  }
  public void setAssociations(List<PurposeEntityAssociationGetResponseDTO> associations) {
    this.associations = associations;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class PurposeEntityAssociationListResponseDTO {\n");
    
    sb.append("  associations: ").append(associations).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
