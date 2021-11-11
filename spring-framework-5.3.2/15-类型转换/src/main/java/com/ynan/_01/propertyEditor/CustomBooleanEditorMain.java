package com.ynan._01.propertyEditor;

import org.springframework.beans.propertyeditors.CustomBooleanEditor;

/**
 * @Author yuannan
 * @Date 2021/10/17 09:54
 */
public class CustomBooleanEditorMain {

	public static void main(String[] args) {
		CustomBooleanEditor editor = new CustomBooleanEditor(true);

		editor.setAsText("off");
		System.out.println(editor.getAsText());

		Object value = editor.getValue();
		System.out.println(value);

	}
}
