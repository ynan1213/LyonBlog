//package com.ynan.typeHandler;
//
//import java.lang.reflect.Constructor;
//import java.sql.CallableStatement;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.Objects;
//import org.apache.ibatis.type.BaseTypeHandler;
//import org.apache.ibatis.type.JdbcType;
//
///**
// * @Author yuannan
// * @Date 2022/2/28 14:33
// * @Description 数据库敏感信息加密解
// */
//public class EncryptTypeHandler<T> extends BaseTypeHandler<T> {
//
//    public static final String KEY = "1234567890ABCWMS";
//
//    /**
//     * 参数类型
//     */
//    private Class<T> type;
//
//    public EncryptTypeHandler(Class<T> type) {
//        if (type == null) {
//            throw new IllegalArgumentException("Type argument cannot be null");
//        }
//        this.type = type;
//    }
//
//    @Override
//    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
//        String s = encryptValue(parameter);
//        ps.setString(i, s);
//    }
//
//    @Override
//    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
//        String columnValue = rs.getString(columnName);
//        return getValue(columnValue);
//    }
//
//    @Override
//    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
//        String columnValue = rs.getString(columnIndex);
//        return getValue(columnValue);
//    }
//
//    @Override
//    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
//        String columnValue = cs.getString(columnIndex);
//        return getValue(columnValue);
//    }
//
//    private T getValue(String columnValue) {
//
//        //        if (StringUtils.isBlank(columnValue)) {
//        //            return null;
//        //        }
//        String value = decryptValue(columnValue);
//        //        if (StringUtils.isBlank(value)) {
//        //            return null;
//        //        }
//        try {
//            Constructor<T> declaredConstructor = type.getDeclaredConstructor(String.class);
//            T t = declaredConstructor.newInstance(value);
//            return t;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return (T) value;
//    }
//
//
//    private String decryptValue(String columnValue) {
//        try {
//            //            AESUtils.decryptFromBase64BySpecificKey(columnValue, KEY);
//            return null;
//        } catch (Exception e) {
//            //            log.error("decrypt error filed {}, error {}", columnValue, e.getMessage());
//        }
//        return columnValue;
//    }
//
//    private String encryptValue(T param) throws SQLException {
//        String value = "";
//        if (Objects.nonNull(param)) {
//            if (param instanceof String) {
//                value = (String) param;
//            } else {
//                value = param.toString();
//            }
//        }
//        try {
//            //            AESUtils.encryptBase64BySpecificKey(value, KEY);
//            return null;
//        } catch (Exception e) {
//            //            log.error("encrypt error filed {}  msg {}", param, e.getMessage());
//            throw new SQLException(e);
//        }
//    }
//}
