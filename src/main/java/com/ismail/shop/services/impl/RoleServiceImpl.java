package com.ismail.shop.services.impl;

import com.ismail.shop.dtos.RoleDTO;
import com.ismail.shop.entities.Role;
import com.ismail.shop.exceptions.RoleNotFoundException;
import com.ismail.shop.mappers.RoleMapper;
import com.ismail.shop.repositories.RoleRepository;
import com.ismail.shop.services.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;
    private RoleMapper roleMapper;

    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public List<RoleDTO> getAllRoles() {
        List<Role> roles = this.roleRepository.findAll();
        List<RoleDTO> roleDTOS = roles.stream().map(role -> {
            return this.roleMapper.toDTO(role);
        }).collect(Collectors.toList());
        return roleDTOS;
    }

    @Override
    public RoleDTO getOneRoleByID(Long id) throws RoleNotFoundException {
        Role role = this.roleRepository.findById(id).orElseThrow(()->new RoleNotFoundException("Can not find any role with that "+id));
        return this.roleMapper.toDTO(role);
    }

    @Override
    public RoleDTO createRole(RoleDTO roleDTO) {
        Role role = this.roleMapper.toEntity(roleDTO);
        Role savedRole = this.roleRepository.save(role);
        return this.roleMapper.toDTO(savedRole);
    }

    @Override
    public void deleteRoleByID(Long id) throws RoleNotFoundException {
        Role role = this.roleRepository.findById(id).orElseThrow(()->new RoleNotFoundException("Can not find any role with that "+id));
        this.roleRepository.deleteById(id);
    }
}
