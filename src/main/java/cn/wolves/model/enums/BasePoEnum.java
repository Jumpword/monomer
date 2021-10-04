package cn.wolves.model.enums;

/**
 * @author Jumping
 */

public enum BasePoEnum implements ValueEnum<String>{
    /**创建时间*/
    CREATE_TIME("createTime"),
    /**更新时间*/
    UPDATE_TIME("updateTime"),
    /**版本号*/
    VERSION("version")
    ;

    private final String Value;

    BasePoEnum(String value) {
        Value = value;
    }

    @Override
    public String getValue() {
        return Value;
    }
}
