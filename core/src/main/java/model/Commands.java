package model;

import java.io.Serializable;

public enum Commands implements Serializable {
    GET_FILE,
    SEND_FILE,
    INFO,
    REFRESH,
    AUTH
}
