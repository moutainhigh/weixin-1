package cn.siyue.platform.weixin.common.constants;

public class MessageReplyConstant {

    public static class Type {
        public static final Integer SUBSCRIBE_REPLY = 1;
        public static final Integer NO_KEYWORD_REPLY = 2;
        public static final Integer KEYWORD_REPLY = 3;
    }

    public static class ReplyType {
        public static final Integer TEXT = 1;
        public static final Integer IMAGE = 2;
        public static final Integer VOICE = 3;
        public static final Integer VIDEO = 4;
        public static final Integer NEWS = 5;
    }

    public static class MatchType {
        public static final Integer FULL = 1;
        public static final Integer CONTAIN = 2;
    }
}
