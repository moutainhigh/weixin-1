package cn.siyue.platform.weixin.common.constants;

public class WeixinConstant {

    public static final Integer PAGE_ONE = 1;

    public static final Integer PAGE_SIZE_ALL = 10000;

    public static class CardCouponConstant {
        /**
         * 待发布
         */
        public static final Integer STATUS_READY_PUBLISH = 0;

        /**
         * 审核中
         */
        public static final Integer STATUS_CHECKING = 1;

        /**
         * 审核通过
         */
        public static final Integer STATUS_CARD_NOT_PASS_CHECK = 2;

        /**
         * 审核不通过
         */
        public static final Integer STATUS_CARD_PASS_CHECK = 3;

        /**
         * 已删除
         */
        public static final Integer STATUS_CARD_DEL = 4;

    }

    public static class CouponConstant {
        /**
         * 领取
         */
        public static final Integer STATUS_ID_RECEIPT = 1;

        /**
         * 核销
         */
        public static final Integer STATUS_ID_CONSUME = 2;

        /**
         * 失效
         */
        public static final Integer STATUS_ID_INVALIDATE = 3;

        /**
         * 已删除
         */
        public static final Integer STATUS_ID_DELETE = 4;
    }

    public static class CouponTaskConstant {

        public static final Integer TYPE_ID_CASH = 1;
        public static final Integer TYPE_ID_DISCOUNT = 2;
        public static final Integer TYPE_ID_GIFT = 3;
        public static final Integer TYPE_ID_MEMBER_CARD = 4;
    }

    public static class CardTypeConstant {
        public static final String CARD_TYPE_CASH = "CASH";

        public static final String CARD_TYPE_DISCOUNT = "DISCOUNT";

        public static final String CARD_TYPE_GIFT = "GIFT";

        public static final String CARD_TYPE_MEMBER_CARD = "MEMBER_CARD";
    }
}
