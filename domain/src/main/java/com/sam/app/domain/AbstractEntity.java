package com.sam.app.domain;

import java.io.Serializable;

interface AbstractEntity extends Serializable {
	Long getId();

	void setId(Long id);
}
