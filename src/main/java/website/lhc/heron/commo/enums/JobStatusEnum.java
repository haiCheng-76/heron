package website.lhc.heron.commo.enums;

/**
 * @description: job状态枚举
 * @author: 582895699@qq.com
 * @time: 2020/9/28 下午 10:57
 */
public enum JobStatusEnum {
    /**
     * 运行
     */
    START(0, "运行"),
    /**
     * 停止
     */
    STOP(1, "停止"),

    /**
     * 暂停
     */
    PAUSE(2, "暂停");

    private final int code;
    private final String value;

    JobStatusEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public static JobStatusEnum getEnumByKey(String key) {
        for (JobStatusEnum statusEnum : JobStatusEnum.values()) {
            if (statusEnum.getValue() == key) {
                return statusEnum;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
