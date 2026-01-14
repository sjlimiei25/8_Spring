package com.kh.todoapi.todo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 할일의 상태를 담은 DTO 객체
 * - status : 변경할 상태
 */
@NoArgsConstructor
@Getter
@Setter
public class UpdateTodo {
	
	private Long id;
	
	@NotBlank(message="status 는 필수 항목입니다.")
	@Pattern(message="상태값은 IN_PROGRESS 또는 COMPLETED 만 허용합니다."
			, regexp="^(IN_PROGRESS|COMPLETED)$")
	private String status;

}
