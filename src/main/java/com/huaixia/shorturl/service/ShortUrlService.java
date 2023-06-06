package com.huaixia.shorturl.service;

import com.huaixia.shorturl.common.ApiResponse;
import org.springframework.web.servlet.view.RedirectView;

/**
 * @author biliyu
 * @date 2023/6/6 10:47
 */
public interface ShortUrlService {
    ApiResponse<String> createShortUrl(String url);

    RedirectView redirect(String key);
}
