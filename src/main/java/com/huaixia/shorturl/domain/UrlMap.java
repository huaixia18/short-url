package com.huaixia.shorturl.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author biliyu
 * @date 2023/6/7 08:55
 */
@Data
@Table(name = "url_map")
public class UrlMap {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "short_url")
    private String shortUrl;

    @Column(name = "long_url")
    private String longUrl;

}
