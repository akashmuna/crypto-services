package com.xm.crypto.util;

import org.springframework.data.domain.Sort;

import javax.persistence.criteria.Order;
import java.util.ArrayList;
import java.util.List;

public class CryptoConstants {

    public static final String[] HEADERS = { "timestamp", "symbol", "price" };
    public static final String ERROR = "E";
    public static final String INFORMATION = "I";
    public static final String SORTING_COL = "updatedDate";
    public static final String DATE_FOMRAT = "yyyy-MM-ddHH:mm:ss";

    public static final String CPS_ERR_01 = "CPS-ERR-01";
    public static final String CPS_ERR_02 = "CPS-ERR-02";
    public static final String CPS_ERR_03 = "CPS-ERR-03";
    public static final String CPS_ERR_04 = "CPS-ERR-04";
    public static final String CPS_ERR_05 = "CPS-ERR-05";
    public static final String CPS_ERR_06 = "CPS-ERR-06";
    public static final String CPS_ERR_07 = "CPS-ERR-07";
    public static final String CPS_ERR_08 = "CPS-ERR-08";
    public static final String CPS_ERR_09 = "CPS-ERR-09";
    public static final String CPS_ERR_10 = "CPS-ERR-10";

    public static final String START_TIME = "00:00:00";
    public static final String END_TIME = "23:59:59";
    public static final long SCHEDULE = 86400000L;
    public static final String SQL_FILE = "schema.sql";

    public static final String SUCCESS = "CPS-SUCCESS-01";

    public static final String LOCAL = "local";
    public static final String DOCKER = "docker";
}
