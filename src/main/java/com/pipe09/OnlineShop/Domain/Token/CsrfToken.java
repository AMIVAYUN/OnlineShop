package com.pipe09.OnlineShop.Domain.Token;

import java.io.Serializable;

public interface CsrfToken extends Serializable {
    String getHeaderName();
    String getParameterName();
    String getToken();
}
