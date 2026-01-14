package com.kh.todoapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 추가할 할일 정보를 담을 DTO 객체
 * - title : 할일 내용
 */
@NoArgsConstructor
@Getter
@Setter
public class CreateTodo {
	@NotBlank(message="title은 필수 항목입니다.")
	private String title;

	@Override
	public String toString() {
		return String.format("%s", this.title);
	}
}
