package com.xm.crypto.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "CRYPTO_RECORD")
@NamedQuery(name = "CryptoRecord.findBytimeStamp", query = "SELECT a FROM CryptoRecord a where a.updatedDate=:updatedDate")
@Getter
@Setter
@ToString
@AllArgsConstructor
public class CryptoRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Column(name = "SYMBOL")
    private String symbol;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    public CryptoRecord(){
        id = this.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!super.equals(o))
            return false;
        if (getClass() != o.getClass())
            return false;
        CryptoRecord that = (CryptoRecord) o;
        if(symbol == null){
            if(that.symbol!=null)
                return false;
        }else if (!symbol.equals(that.symbol))
            return false;
        if(price == null){
            if(that.price!=null)
                return false;
        }else if (!price.equals(that.price))
            return false;
        if(updatedDate == null){
            if(that.updatedDate!=null)
                return false;
        }else if (!updatedDate.equals(that.updatedDate))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 7;
        int result = super.hashCode();
        result = prime * result + ((symbol!=null)? 0 : symbol.hashCode());
        result = prime * result + ((price!=null)? 0 : price.hashCode());
        result = prime * result + ((updatedDate!=null)? 0 : updatedDate.hashCode());
        return result;
    }
}
