package com.ynan.typeHandler;

import com.ynan.enum1.AgeEnum;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

/**
 * @Author yuannan
 * @Date 2022/3/3 23:29
 */
public class MyEnumTypeHandler extends BaseTypeHandler<AgeEnum> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, AgeEnum parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getAge());
    }

    @Override
    public AgeEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int age = rs.getInt(columnName);
        return AgeEnum.fromInt(age);
    }

    @Override
    public AgeEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return AgeEnum.fromInt(rs.getInt(columnIndex));
    }

    @Override
    public AgeEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return AgeEnum.fromInt(cs.getInt(columnIndex));
    }
}
