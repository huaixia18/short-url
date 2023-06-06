package com.huaixia.shorturl.controller;

import com.huaixia.shorturl.common.ApiResponse;
import com.huaixia.shorturl.service.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

/**
 * @author biliyu
 * @date 2023/6/6 09:39
 */
@RestController
public class ShortUrlController {

    @Autowired
    private ShortUrlService shortUrlService;

    @GetMapping("/createShortUrl")
    public ApiResponse<String> createShortUrl(String url) {
        return shortUrlService.createShortUrl(url);
    }

    @GetMapping(value="/{key}")
    public RedirectView redirect (@PathVariable String key) {
        return shortUrlService.redirect(key);
    }


}
