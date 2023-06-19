package com.huaixia.shorturl.service.impl;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.google.common.hash.Hashing;
import com.huaixia.shorturl.common.ApiResponse;
import com.huaixia.shorturl.domain.UrlMap;
import com.huaixia.shorturl.dto.CreateShortUrlQuery;
import com.huaixia.shorturl.service.ShortUrlService;
import com.huaixia.shorturl.service.UrlMapService;
import com.huaixia.shorturl.utils.BloomFilterUtil;
import com.huaixia.shorturl.utils.DecimalToBase62Util;
import com.huaixia.shorturl.utils.RedisClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

/**
 * @author biliyu
 * @date 2023/6/6 10:48
 */
@Slf4j
@Service
public class ShortUrlServiceImpl implements ShortUrlService {

    @Value("${huaixia.url}")
    private String shortUrl;

    @Autowired
    private RedisClientUtil redisClientUtil;

    @Autowired
    private BloomFilterUtil bloomFilterUtil;

    @Autowired
    private UrlMapService urlMapService;

    @Override
    public ApiResponse<String> createShortUrl(String url) {
        // 获得 10 进制
        long hash = Hashing.murmur3_32_fixed().hashUnencodedChars(url).padToLong();
        // 10 进制转 62 进制
        String base62 = DecimalToBase62Util.decimalToBase62(hash);
        checkBase62(url, base62);
        // 存入数据库
        UrlMap urlMap = new UrlMap();
        urlMap.setShortUrl(base62);
        urlMap.setLongUrl(url);
        int insert = urlMapService.insert(urlMap);
        if (insert > 0) {
            bloomFilterUtil.bfreserve(base62, 0.01, 100);
            bloomFilterUtil.bfadd(base62, url);
            return ApiResponse.ok(shortUrl + base62);
        }
        return ApiResponse.ok();

    }


    @Override
    public RedirectView redirect(String key) {
        // 从 Redis 中取出长链地址
        String redirectUrl = redisClientUtil.get(key);
        // 重定向
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(redirectUrl);
        return redirectView;
    }

    private void checkBase62(String url, String base62) {
        Boolean bfexists = bloomFilterUtil.bfexists(base62, url);
        String newBase62;
        // 考虑到布隆过滤器存在误判问题，如果返回存在，查询数据库确定是否真的存在
        if (bfexists) {
            String longUrl = urlMapService.getLongUrlByShortUrl(base62);
            if (StringUtils.isNoneBlank(longUrl)) {
                newBase62 = base62 + RandomStringUtils.random(1);
                checkBase62(url, newBase62);
            }
        }
    }

}
