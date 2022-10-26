package com.xm.crypto.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.xm.crypto.model.CryptoServicesMasterModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Collection of CryptoServicesMasterModel.
 */
@ApiModel(description = "Collection of CryptoServicesMasterModel.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2022-10-26T13:06:10.008+05:30")

public class CryptoServicesMasterCollection   {
  @JsonProperty("cryptoServicesMasterModelList")
  @Valid
  private List<CryptoServicesMasterModel> cryptoServicesMasterModelList = null;

  public CryptoServicesMasterCollection cryptoServicesMasterModelList(List<CryptoServicesMasterModel> cryptoServicesMasterModelList) {
    this.cryptoServicesMasterModelList = cryptoServicesMasterModelList;
    return this;
  }

  public CryptoServicesMasterCollection addCryptoServicesMasterModelListItem(CryptoServicesMasterModel cryptoServicesMasterModelListItem) {
    if (this.cryptoServicesMasterModelList == null) {
      this.cryptoServicesMasterModelList = new ArrayList<CryptoServicesMasterModel>();
    }
    this.cryptoServicesMasterModelList.add(cryptoServicesMasterModelListItem);
    return this;
  }

  /**
   * Get cryptoServicesMasterModelList
   * @return cryptoServicesMasterModelList
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<CryptoServicesMasterModel> getCryptoServicesMasterModelList() {
    return cryptoServicesMasterModelList;
  }

  public void setCryptoServicesMasterModelList(List<CryptoServicesMasterModel> cryptoServicesMasterModelList) {
    this.cryptoServicesMasterModelList = cryptoServicesMasterModelList;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CryptoServicesMasterCollection cryptoServicesMasterCollection = (CryptoServicesMasterCollection) o;
    return Objects.equals(this.cryptoServicesMasterModelList, cryptoServicesMasterCollection.cryptoServicesMasterModelList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cryptoServicesMasterModelList);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CryptoServicesMasterCollection {\n");
    
    sb.append("    cryptoServicesMasterModelList: ").append(toIndentedString(cryptoServicesMasterModelList)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

