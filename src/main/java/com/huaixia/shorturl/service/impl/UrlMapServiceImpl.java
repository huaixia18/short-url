package com.huaixia.shorturl.service.impl;

import com.huaixia.shorturl.domain.UrlMap;
import com.huaixia.shorturl.mapper.UrlMapMapper;
import com.huaixia.shorturl.service.UrlMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

/**
 * @author biliyu
 * @date 2023/6/7 08:58
 */
@Service
public class UrlMapServiceImpl implements UrlMapService {

    @Autowired
    private UrlMapMapper urlMapMapper;


    @Override
    public int insert(UrlMap urlMap) {
        return urlMapMapper.insertSelective(urlMap);
    }

    @Override
    public String getLongUrlByShortUrl(String shortUrl) {
        Example example = new Example(UrlMap.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("short_rul", shortUrl);
        UrlMap urlMap = urlMapMapper.selectOneByExample(example);
        if (urlMap != null) {
            return urlMap.getLongUrl();
        }
        return "";
    }
}
