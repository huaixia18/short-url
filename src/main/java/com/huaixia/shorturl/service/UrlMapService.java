package com.huaixia.shorturl.service;

import com.huaixia.shorturl.domain.UrlMap;

/**
 * @author biliyu
 * @date 2023/6/7 08:57
 */
public interface UrlMapService {

    int insert(UrlMap urlMap);

    String getLongUrlByShortUrl(String shortUrl);

}
