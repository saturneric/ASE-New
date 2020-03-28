package com.codesdream.ase.service;

import com.codesdream.ase.model.leaves.Leave;
import com.codesdream.ase.repository.leaves.LeaveRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class  LeavesService implements ILeavesService {

    @Resource
    private LeaveRepository leaveRepository;


    @Override
    public Optional<Leave> findLeaveByTitle(String title) {
        return Optional.empty();
    }

    @Override
    public Optional<Leave> findLeaveByCreator(String creatorName) {
        return Optional.empty();
    }

    @Override
    public Leave save( Leave leave) {
        return leaveRepository.save(leave);
    }

    @Override
    public Optional<Leave> findLeaveById(int id) {
        return leaveRepository.findById(id);
    }



    @Override
    public void delete(Leave leave) {
        leaveRepository.delete(leave);
    }

    @Override
    public Leave update(Leave leave) {
        return leaveRepository.save(leave);
    }

    @Override
    public Leave createLeave(Leave leave) {
        return leaveRepository.save(leave);
    }


/*@Override
    public Leave findActivitiesInTheCharge(User user) {

    }*/

}
