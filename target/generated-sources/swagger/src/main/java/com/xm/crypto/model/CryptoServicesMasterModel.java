package com.xm.crypto.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.xm.crypto.model.CryptoPropertiesMasterModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CryptoServicesMasterModel
 */
@ApiModel(description = "CryptoServicesMasterModel")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2022-10-26T13:06:10.008+05:30")

public class CryptoServicesMasterModel   {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("updatedDate")
  private String updatedDate = null;

  @JsonProperty("symbol")
  private String symbol = null;

  @JsonProperty("price")
  private BigDecimal price = null;

  @JsonProperty("errorStatus")
  private String errorStatus = null;

  @JsonProperty("compNormalizedPrice")
  private BigDecimal compNormalizedPrice = null;

  @JsonProperty("cryptoProp")
  private CryptoPropertiesMasterModel cryptoProp = null;

  public CryptoServicesMasterModel id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Id of the record
   * @return id
  **/
  @ApiModelProperty(value = "Id of the record")


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public CryptoServicesMasterModel updatedDate(String updatedDate) {
    this.updatedDate = updatedDate;
    return this;
  }

  /**
   * Updated Date (example 2022-02-25 01:21:32)
   * @return updatedDate
  **/
  @ApiModelProperty(required = true, value = "Updated Date (example 2022-02-25 01:21:32)")
  @NotNull


  public String getUpdatedDate() {
    return updatedDate;
  }

  public void setUpdatedDate(String updatedDate) {
    this.updatedDate = updatedDate;
  }

  public CryptoServicesMasterModel symbol(String symbol) {
    this.symbol = symbol;
    return this;
  }

  /**
   * Symbol Code
   * @return symbol
  **/
  @ApiModelProperty(required = true, value = "Symbol Code")
  @NotNull


  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public CryptoServicesMasterModel price(BigDecimal price) {
    this.price = price;
    return this;
  }

  /**
   * Price
   * @return price
  **/
  @ApiModelProperty(required = true, value = "Price")
  @NotNull

  @Valid

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public CryptoServicesMasterModel errorStatus(String errorStatus) {
    this.errorStatus = errorStatus;
    return this;
  }

  /**
   * Error Message
   * @return errorStatus
  **/
  @ApiModelProperty(value = "Error Message")


  public String getErrorStatus() {
    return errorStatus;
  }

  public void setErrorStatus(String errorStatus) {
    this.errorStatus = errorStatus;
  }

  public CryptoServicesMasterModel compNormalizedPrice(BigDecimal compNormalizedPrice) {
    this.compNormalizedPrice = compNormalizedPrice;
    return this;
  }

  /**
   * Compared Normalized Price
   * @return compNormalizedPrice
  **/
  @ApiModelProperty(value = "Compared Normalized Price")

  @Valid

  public BigDecimal getCompNormalizedPrice() {
    return compNormalizedPrice;
  }

  public void setCompNormalizedPrice(BigDecimal compNormalizedPrice) {
    this.compNormalizedPrice = compNormalizedPrice;
  }

  public CryptoServicesMasterModel cryptoProp(CryptoPropertiesMasterModel cryptoProp) {
    this.cryptoProp = cryptoProp;
    return this;
  }

  /**
   * Get cryptoProp
   * @return cryptoProp
  **/
  @ApiModelProperty(value = "")

  @Valid

  public CryptoPropertiesMasterModel getCryptoProp() {
    return cryptoProp;
  }

  public void setCryptoProp(CryptoPropertiesMasterModel cryptoProp) {
    this.cryptoProp = cryptoProp;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CryptoServicesMasterModel cryptoServicesMasterModel = (CryptoServicesMasterModel) o;
    return Objects.equals(this.id, cryptoServicesMasterModel.id) &&
        Objects.equals(this.updatedDate, cryptoServicesMasterModel.updatedDate) &&
        Objects.equals(this.symbol, cryptoServicesMasterModel.symbol) &&
        Objects.equals(this.price, cryptoServicesMasterModel.price) &&
        Objects.equals(this.errorStatus, cryptoServicesMasterModel.errorStatus) &&
        Objects.equals(this.compNormalizedPrice, cryptoServicesMasterModel.compNormalizedPrice) &&
        Objects.equals(this.cryptoProp, cryptoServicesMasterModel.cryptoProp);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, updatedDate, symbol, price, errorStatus, compNormalizedPrice, cryptoProp);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CryptoServicesMasterModel {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    updatedDate: ").append(toIndentedString(updatedDate)).append("\n");
    sb.append("    symbol: ").append(toIndentedString(symbol)).append("\n");
    sb.append("    price: ").append(toIndentedString(price)).append("\n");
    sb.append("    errorStatus: ").append(toIndentedString(errorStatus)).append("\n");
    sb.append("    compNormalizedPrice: ").append(toIndentedString(compNormalizedPrice)).append("\n");
    sb.append("    cryptoProp: ").append(toIndentedString(cryptoProp)).append("\n");
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

