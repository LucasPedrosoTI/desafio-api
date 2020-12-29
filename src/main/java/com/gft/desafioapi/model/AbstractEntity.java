package com.gft.desafioapi.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@MappedSuperclass
@AllArgsConstructor
public class AbstractEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(example = "1", position = -1)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

}
