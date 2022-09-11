package id.co.rimaubank.jambhala.service.esb;

import org.springframework.lang.Nullable;

public enum EsbStatus {

    SUCCESS("0",  "Successful"),
    HOST_ERROR("100", "Host Failure"),
    ESB_ERROR("200", "ESB Failure");


    private static final EsbStatus[] VALUES = values();
    private final String statusCode;
    private final String reasonPhrase;

    EsbStatus(String statusCode, String reasonPhrase) {
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
    }

    public String value() {
        return this.statusCode;
    }


    public String getReasonPhrase() {
        return this.reasonPhrase;
    }


    public boolean isSuccessful() {
        return this == SUCCESS;
    }

    public boolean isFailed() {
        return this == ESB_ERROR || this == HOST_ERROR;
    }


    public String toString() {
        return this.statusCode + " " + this.name();
    }



    @Nullable
    public static EsbStatus resolve(String statusCode) {
        EsbStatus[] var1 = VALUES;
        int var2 = var1.length;

        for (EsbStatus status : var1) {
            if (status.statusCode.equals(statusCode)) {
                return status;
            }
        }

        return null;
    }

}
