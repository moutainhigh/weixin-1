/**
  * Copyright 2018 bejson.com 
  */
package cn.siyue.platform.weixin.common.dto.weixin.card.request.createcard.advancedinfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2018-08-20 10:20:26
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class Abs {

    @JsonProperty("abstract")
    private String abs;
    private List<String> icon_url_list;

}