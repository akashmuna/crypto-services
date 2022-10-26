package com.xm.crypto.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CryptoPropertiesMasterModel details of each Crypto Max,Min,Normalized Range
 */
@ApiModel(description = "CryptoPropertiesMasterModel details of each Crypto Max,Min,Normalized Range")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2022-10-26T13:06:10.008+05:30")

public class CryptoPropertiesMasterModel   {
  @JsonProperty("min")
  private BigDecimal min = null;

  @JsonProperty("max")
  private BigDecimal max = null;

  @JsonProperty("oldest")
  private BigDecimal oldest = null;

  @JsonProperty("newest")
  private BigDecimal newest = null;

  @JsonProperty("normRange")
  private BigDecimal normRange = null;

  public CryptoPropertiesMasterModel min(BigDecimal min) {
    this.min = min;
    return this;
  }

  /**
   * Minimum price of the Crypto
   * @return min
  **/
  @ApiModelProperty(value = "Minimum price of the Crypto")

  @Valid

  public BigDecimal getMin() {
    return min;
  }

  public void setMin(BigDecimal min) {
    this.min = min;
  }

  public CryptoPropertiesMasterModel max(BigDecimal max) {
    this.max = max;
    return this;
  }

  /**
   * Maximum price of the Crypto
   * @return max
  **/
  @ApiModelProperty(value = "Maximum price of the Crypto")

  @Valid

  public BigDecimal getMax() {
    return max;
  }

  public void setMax(BigDecimal max) {
    this.max = max;
  }

  public CryptoPropertiesMasterModel oldest(BigDecimal oldest) {
    this.oldest = oldest;
    return this;
  }

  /**
   * oldest price of the Crypto
   * @return oldest
  **/
  @ApiModelProperty(value = "oldest price of the Crypto")

  @Valid

  public BigDecimal getOldest() {
    return oldest;
  }

  public void setOldest(BigDecimal oldest) {
    this.oldest = oldest;
  }

  public CryptoPropertiesMasterModel newest(BigDecimal newest) {
    this.newest = newest;
    return this;
  }

  /**
   * Newest price of the Crypto
   * @return newest
  **/
  @ApiModelProperty(value = "Newest price of the Crypto")

  @Valid

  public BigDecimal getNewest() {
    return newest;
  }

  public void setNewest(BigDecimal newest) {
    this.newest = newest;
  }

  public CryptoPropertiesMasterModel normRange(BigDecimal normRange) {
    this.normRange = normRange;
    return this;
  }

  /**
   * Normalized Range of that Crypto
   * @return normRange
  **/
  @ApiModelProperty(value = "Normalized Range of that Crypto")

  @Valid

  public BigDecimal getNormRange() {
    return normRange;
  }

  public void setNormRange(BigDecimal normRange) {
    this.normRange = normRange;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CryptoPropertiesMasterModel cryptoPropertiesMasterModel = (CryptoPropertiesMasterModel) o;
    return Objects.equals(this.min, cryptoPropertiesMasterModel.min) &&
        Objects.equals(this.max, cryptoPropertiesMasterModel.max) &&
        Objects.equals(this.oldest, cryptoPropertiesMasterModel.oldest) &&
        Objects.equals(this.newest, cryptoPropertiesMasterModel.newest) &&
        Objects.equals(this.normRange, cryptoPropertiesMasterModel.normRange);
  }

  @Override
  public int hashCode() {
    return Objects.hash(min, max, oldest, newest, normRange);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CryptoPropertiesMasterModel {\n");
    
    sb.append("    min: ").append(toIndentedString(min)).append("\n");
    sb.append("    max: ").append(toIndentedString(max)).append("\n");
    sb.append("    oldest: ").append(toIndentedString(oldest)).append("\n");
    sb.append("    newest: ").append(toIndentedString(newest)).append("\n");
    sb.append("    normRange: ").append(toIndentedString(normRange)).append("\n");
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

