/**
  * Copyright 2018 bejson.com 
  */
package cn.siyue.platform.weixin.common.dto.weixin.card.request.createcard.advancedinfo;

/**
 * Auto-generated: 2018-08-20 10:29:44
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Time_limit {

    private String type;
    private int begin_hour;
    private int end_hour;
    private int begin_minute;
    private int end_minute;
    public void setType(String type) {
         this.type = type;
     }
     public String getType() {
         return type;
     }

    public void setBegin_hour(int begin_hour) {
         this.begin_hour = begin_hour;
     }
     public int getBegin_hour() {
         return begin_hour;
     }

    public void setEnd_hour(int end_hour) {
         this.end_hour = end_hour;
     }
     public int getEnd_hour() {
         return end_hour;
     }

    public void setBegin_minute(int begin_minute) {
         this.begin_minute = begin_minute;
     }
     public int getBegin_minute() {
         return begin_minute;
     }

    public void setEnd_minute(int end_minute) {
         this.end_minute = end_minute;
     }
     public int getEnd_minute() {
         return end_minute;
     }

}