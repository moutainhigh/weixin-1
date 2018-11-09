package cn.siyue.platform.weixin.common.dto.weixin.card.request.createcard.cardtype;

import lombok.Data;

@Data
public class MemberCardInfoDto extends CardInfoDto {
    private String background_pic_url;
    private String prerogative;
    private Boolean auto_activate;
    private Boolean wx_activate;
    private Boolean supply_bonus;
    private String bonus_url;
    private Boolean supply_balance;
    private String balance_url;
    private CustomField custom_field1;
    private CustomField custom_field2;
    private CustomField custom_field3;
    private String bonus_cleared;
    private BonusRule bonus_rules;
    private String balance_rules;
    private String activate_url;
    private String activate_app_brand_user_name;
    private String activate_app_brand_pass;
    private CustomCell custom_cell1;
    private Integer discount;

    @Data
    public static class CustomField {
        private String name_type;
        private String name;
        private String url;
    }

    @Data
    public static class CustomCell {
        private String name;
        private String tips;
        private String url;
    }

    @Data
    public static class BonusRule {
        private Integer cost_money_unit;
        private Integer increase_bonus;
        private Integer max_increase_bonus;
        private Integer init_increase_bonus;
        private Integer cost_bonus_unit;
        private Integer reduce_money;
        private Integer least_moneyto_use_bonus;
        private Integer max_reduce_bonus;
    }
}
