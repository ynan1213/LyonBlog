package com.ynan.typeHandler;

import com.ynan.enum1.GenderEnum;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

/**
 * @Author yuannan
 * @Date 2022/3/3 23:29
 */
@MappedTypes(value = GenderEnum.class)
public class MyEnumTypeHandler extends BaseTypeHandler<GenderEnum> {

    private Class<GenderEnum> type;

    private GenderEnum[] enums;

    public MyEnumTypeHandler(Class<GenderEnum> xxx) {
        this.type = xxx;
        enums = xxx.getEnumConstants();
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, GenderEnum parameter, JdbcType jdbcType)
        throws SQLException {
        ps.setInt(i, parameter.ordinal());
    }

    @Override
    public GenderEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int ordinal = rs.getInt(columnName);
        return enums[ordinal];
    }

    @Override
    public GenderEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int ordinal = rs.getInt(columnIndex);
        return enums[ordinal];
    }

    @Override
    public GenderEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int ordinal = cs.getInt(columnIndex);
        return enums[ordinal];
    }
}
