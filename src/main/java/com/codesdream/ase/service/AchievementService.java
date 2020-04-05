package com.codesdream.ase.service;

import com.codesdream.ase.component.auth.ASEPasswordEncoder;
import com.codesdream.ase.component.auth.ASEUsernameEncoder;
import com.codesdream.ase.component.permission.UserRolesListGenerator;
import com.codesdream.ase.exception.badrequest.UserInformationIllegalException;
import com.codesdream.ase.exception.notfound.UserNotFoundException;
import com.codesdream.ase.exception.badrequest.UsernameAlreadyExistException;
import com.codesdream.ase.model.information.BaseStudentInfo;
import com.codesdream.ase.model.permission.User;
import com.codesdream.ase.repository.permission.UserRepository;
import javafx.util.Pair;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class AchievementService {

}
