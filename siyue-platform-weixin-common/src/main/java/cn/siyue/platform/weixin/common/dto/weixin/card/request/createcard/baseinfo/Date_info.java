/**
  * Copyright 2018 bejson.com 
  */
package cn.siyue.platform.weixin.common.dto.weixin.card.request.createcard.baseinfo;

import lombok.Data;

/**
 * Auto-generated: 2018-08-20 10:20:26
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class Date_info {

    private String type;
    private Long begin_timestamp;
    private Long end_timestamp;
    private Integer fixed_term;
    private Integer fixed_begin_term;
}