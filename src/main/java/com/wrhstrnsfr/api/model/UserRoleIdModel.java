package com.wrhstrnsfr.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleIdModel implements Serializable {

	@OneToOne
	@JoinColumn(name = "user_id")
	private UserModel user;

	@ManyToOne
	@JoinColumn(name = "role_id")
	private RoleModel role;
}
