package com.gt.interpackage.service;
import com.gt.interpackage.model.Employee;
import com.gt.interpackage.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
/**
 *
 * @author Luis
 */
@Service
public class AuthService {

    @Autowired
    private AuthRepository _authRepository;
    
    public Employee login(String username, String pass) throws Exception{
        try {
            Employee emp2 = _authRepository.findByUsernameAndPasswordAndActivoTrue(username, pass);
            return emp2;
        } catch(EntityNotFoundException e){
            return null;
        }
    }
    
}
