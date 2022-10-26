package com.xm.crypto.persistence;

import com.xm.crypto.entity.CryptoRecord;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CryptoRepository extends PagingAndSortingRepository<CryptoRecord,String> {

    List<CryptoRecord> findBytimeStamp(@Param("updatedDate") Date updatedDate);

    @Query("SELECT a FROM CryptoRecord a where a.updatedDate BETWEEN :startDate and :endDate ORDER BY a.symbol")
    List<CryptoRecord> findByUpdatedDate(Date startDate,Date endDate, Sort sort);

    @Query("SELECT a FROM CryptoRecord a where a.updatedDate BETWEEN :startDate and :endDate AND a.symbol=:symbol ORDER BY a.symbol")
    List<CryptoRecord> findByUpdatedAndSymbol(@Param("startDate") Date startDate,@Param("endDate") Date endDate,@Param("symbol") String symbol,Sort sort);

    @Query("SELECT a FROM CryptoRecord a where a.symbol=:symbol")
    List<CryptoRecord> findBySymbol(@Param("symbol") String symbol,Sort sort);

    @Query("SELECT a FROM CryptoRecord a where a.updatedDate=:updatedDate and a.symbol=:symbol")
    List<CryptoRecord> findBytimeStampAndDate(@Param("updatedDate") Date updatedDate,@Param("symbol") String symbol);
}
