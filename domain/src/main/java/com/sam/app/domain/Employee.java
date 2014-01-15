package com.sam.app.domain;

import java.util.*;

import javax.persistence.*;

@Entity
@NamedQueries
        ({
                @NamedQuery(name = "all_employees", query = "SELECT e FROM Employee e"),

        })
@Table(name = "employee")
public final class Employee implements AbstractEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "department_id")
	private Department department;

	@OneToMany
	@JoinTable(name = "employee_role",
			joinColumns = @JoinColumn(name = "emp_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id"))
	@OrderColumn(name = "roles_order")
	private List<Role> roles = new ArrayList<Role>();

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

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

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + "]";
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		roles.addAll(roles);
	}

    public void addRole(Role role) {
        roles.add(role);
    }

    public void deleteRole(Role role) {
        roles.remove(role);
    }
}
