package com.codesdream.ase.repository.leaves;
import com.codesdream.ase.model.activity.Report;
import com.codesdream.ase.model.leaves.Leave;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LeaveRepository extends CrudRepository<Leave, Integer>{


}
