package com.sam.app.util;


import com.sam.app.domain.Role;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleService extends AbstractEntityService<Role> {

    public RoleService() {
        super(Role.class);
    }

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

}
