package com.compoldata.induflow.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.compoldata.induflow.auth.AuthenticatedUser;
import com.compoldata.induflow.model.Employee;
import com.compoldata.induflow.repository.appRepositories.EmployeeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AutheticationService implements UserDetailsService {
    @Autowired
    private final EmployeeRepository employeeRepository;

    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      return employeeRepository.findByEmployeeEmail(username)
      .map(AuthenticatedUser::new)
      .orElseThrow(() -> new UsernameNotFoundException("Invalid credentials"));
    }

    
    //retorna o usuario autenticado
    public Employee getAutheticatedUser(){
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); 
      if (authentication != null && authentication.getPrincipal() instanceof AuthenticatedUser) {
      AuthenticatedUser user =(AuthenticatedUser) authentication.getPrincipal();
        Employee employee = user.getEmployee(); 
         return employee;
      }
      return null;  
    }
    
}
