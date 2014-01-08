package com.sam.app.model;

import java.io.Serializable;

interface AbstractEntity extends Serializable {
	Long getId();

	void setId(Long id);
}
