package com.ismail.shop.services;


import com.ismail.shop.dtos.RoleDTO;
import com.ismail.shop.exceptions.RoleNotFoundException;

import java.util.List;

public interface RoleService {
    List<RoleDTO> getAllRoles();
    RoleDTO getOneRoleByID(Long id) throws RoleNotFoundException;
    RoleDTO createRole(RoleDTO roleDTO);
    void deleteRoleByID(Long id) throws RoleNotFoundException;
}
