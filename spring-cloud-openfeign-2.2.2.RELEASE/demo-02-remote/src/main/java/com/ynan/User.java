package com.ynan;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author yuannan
 * @Date 2022/1/1 20:53
 */
@Data
@AllArgsConstructor
public class User {

	@NotNull(message = "name 不能为空")
	private String name;
	private Integer age;
	private String desc;

}
