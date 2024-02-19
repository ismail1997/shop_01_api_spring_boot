package com.ismail.shop.controllers;


import com.ismail.shop.dtos.RoleDTO;
import com.ismail.shop.entities.Role;
import com.ismail.shop.exceptions.RoleNotFoundException;
import com.ismail.shop.services.RoleService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/roles",produces = MediaType.APPLICATION_JSON_VALUE)
public class RoleRestController {
    private RoleService roleService;

    public RoleRestController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public List<RoleDTO> getAllRoles(){
        return this.roleService.getAllRoles();
    }

    @GetMapping("/{id}")
    public RoleDTO getOneRoleByID(@PathVariable("id") Long id) throws RoleNotFoundException {
        return this.roleService.getOneRoleByID(id);
    }


    @PostMapping()
    public RoleDTO createRole(@RequestBody RoleDTO roleDTO)
    {
        return  this.roleService.createRole(roleDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteRoleByID(@PathVariable("id") Long id) throws RoleNotFoundException {
        this.roleService.deleteRoleByID(id);
    }


    @PutMapping("/{id}")
    public RoleDTO updateRole(@PathVariable("id") Long id,@RequestBody RoleDTO roleDTO) throws RoleNotFoundException {
        return this.roleService.updateRoleDto(id,roleDTO);
    }

    @GetMapping("/check-existence/{name}")
    public boolean checkIfRoleExistedOrNot(@PathVariable("name") String name){
        return this.roleService.checkIfRoleExistedOrNot(name);
    }

}
