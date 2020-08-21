package webiste.lhc.heron.commo.enums;

/**
 * @ProjectName: heron
 * @Package: webiste.lhc.heron.commo.enums
 * @ClassName: MenuEnum
 * @Author: lhc
 * @Description: TODO
 * @Date: 2020/8/16 下午 05:41
 */
public enum MenuEnum {
    /**
     * 菜单
     */
    menu("M"),
    /**
     * 按钮
     */
    button("B");

    private final String type;


    MenuEnum(String type) {
        this.type = type;
    }

    public String val() {
        return type;
    }
}
