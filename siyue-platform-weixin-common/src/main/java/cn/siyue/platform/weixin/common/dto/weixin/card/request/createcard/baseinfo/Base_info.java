/**
  * Copyright 2018 bejson.com 
  */
package cn.siyue.platform.weixin.common.dto.weixin.card.request.createcard.baseinfo;

import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2018-08-20 10:20:26
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class Base_info {

    private String logo_url;
    private String brand_name;
    private String code_type;
    private String title;
    private String color;
    private String notice;
    private String service_phone;
    private String description;
    private Date_info date_info;
    private Sku sku;
    private Integer use_limit;
    private Integer get_limit;
    private Boolean use_custom_code;
    private Boolean bind_openid;
    private Boolean can_share;
    private Boolean can_give_friend;
    private List<Integer> location_id_list;
    private String center_title;
    private String center_sub_title;
    private String center_url;
    private String custom_url_name;
    private String custom_url;
    private String custom_url_sub_title;
    private String promotion_url_name;
    private String promotion_url;
    private String source;


}