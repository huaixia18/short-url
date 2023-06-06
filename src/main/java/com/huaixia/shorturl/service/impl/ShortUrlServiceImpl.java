package com.huaixia.shorturl.service.impl;

import com.google.common.hash.Hashing;
import com.huaixia.shorturl.common.ApiResponse;
import com.huaixia.shorturl.dto.CreateShortUrlQuery;
import com.huaixia.shorturl.service.ShortUrlService;
import com.huaixia.shorturl.utils.DecimalToBase62Util;
import com.huaixia.shorturl.utils.RedisClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

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

    @Override
    public ApiResponse<String> createShortUrl(String url) {
        // 获得 10 进制
        long l = Hashing.murmur3_32_fixed().hashUnencodedChars(url).padToLong();
        // 10 进制转 62 进制
        String base62 = DecimalToBase62Util.decimalToBase62(l);
        // 存储到 redis
        redisClientUtil.set(base62, url);
        return ApiResponse.ok(shortUrl + base62);
    }

    @Override
    public RedirectView redirect(String key) {
        // 从 Redis 中取出长链地址
        String redirectUrl = redisClientUtil.get(key);

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(redirectUrl);
        return redirectView;

    }
}
