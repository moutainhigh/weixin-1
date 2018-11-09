/**
  * Copyright 2018 bejson.com 
  */
package cn.siyue.platform.weixin.common.dto.weixin.card.request.createcard.advancedinfo;

/**
 * Auto-generated: 2018-08-20 10:20:26
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Use_condition {

    private String accept_category;
    private String reject_category;
    private boolean can_use_with_other_discount;
    public void setAccept_category(String accept_category) {
         this.accept_category = accept_category;
     }
     public String getAccept_category() {
         return accept_category;
     }

    public void setReject_category(String reject_category) {
         this.reject_category = reject_category;
     }
     public String getReject_category() {
         return reject_category;
     }

    public void setCan_use_with_other_discount(boolean can_use_with_other_discount) {
         this.can_use_with_other_discount = can_use_with_other_discount;
     }
     public boolean getCan_use_with_other_discount() {
         return can_use_with_other_discount;
     }

}