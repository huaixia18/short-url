# 短链项目介绍

## 背景

1. 什么是短链

   短链即短链接，是将原始长网址压缩而成的短网址。

2. 短链原理

   通过短链找到原始链接（长链接），然后重定向到原始链接地址即可。

3. 为什么需要短链

   可为短信预留更多的内容空间。比如微博、极客时间等网站都在使用短链。

## 技术栈

本项目使用的技术栈主要包括：

- SpringBoot
- Redis
- 通用 mapper
- Guava

## 核心职责

本项目的核心职责包括：

- 通过原始链接生成唯一短链地址。
- 短链地址重定向到原始链接地址。

## 架构图

以下是该项目的架构图：

![image-20230712104358793](/Users/biliyu/Library/Application Support/typora-user-images/image-20230712104358793.png)

## 数据库表设计

本项目的数据库表主要包括：

- url_map

以下是本项目的数据库表设计：

```sql
create schema shortUrl collate utf8mb4_bin;

-- auto-generated definition
create table url_map
(
    id        int auto_increment comment '主键'
        primary key,
    short_url varchar(200) default '' not null comment '短链接',
    long_url  varchar(500) default '' not null comment '长链接',
    constraint uk_short_url
        unique (short_url)
)
    comment '长链短链对应表';
```



## 面试问题挖掘

以下为本项目的面试问题挖掘：

- {问题1}
- {问题2}
- {问题3}