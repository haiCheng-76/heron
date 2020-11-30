package website.lhc.heron.commo.enums;

/**
 * @ProjectName: heron
 * @Package: webiste.lhc.heron.commo.enums
 * @ClassName: MenuEnum
 * @Author: lhc
 * @Description: 菜单类型枚举
 * @Date: 2020/8/16 下午 05:41
 */
public enum MenuEnum {
    /**
     * 目录
     */
    DIR("D"),
    /**
     * 菜单
     */
    MENU("M"),
    /**
     * 按钮
     */
    BUTTON("B");

    private final String type;


    MenuEnum(String type) {
        this.type = type;
    }

    public String val() {
        return type;
    }
}
