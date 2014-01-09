package com.sam.app.domain;

import javax.persistence.*;


@Entity
//@NamedQuery(name = "getAllEmployees", query = "SELECT e FROM Employee e")
@Table(name = "employee")
public final class Employee implements AbstractEntity {
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private String name;
	
//	@OneToMany(mappedBy = "role")
//	private final Map<Long, Role> roles = new HashMap<>(); 
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
	
//	public void addRole(Role role) {
//		roles.put(role.getId(), role);
//	}
//	
//	public void removeRole(long id) {
//		roles.remove(id);
//	}
//	
//	public Collection<Role> getRoles() {
//		return roles.values();
//	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + "]";
	}
	
}
