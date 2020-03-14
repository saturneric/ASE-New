package com.codesdream.ase.service;

import com.codesdream.ase.model.leaves.Leave;
import com.codesdream.ase.repository.leaves.LeaveRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class LeavesService implements ILeavesService {

    @Resource
    private LeaveRepository leaveRepository;

    @Override
    public Optional<Leave> findLeaveByTitle(String title) {
        return LeaveRepository.findByTitle(title);
    }

    @Override
    public Optional<leave> findLeaveByCreator(String creatorName) {
        return LeaveRepository.findByCreator(creatorName);
    }

    @Override
    public Leave save(Leave Leave) {
        return LeaveRepository.save(Leave);
    }

    @Override
    public Leave addReport(Leave Leave, Report report) {
        Leave.setReport(report);
        return update(Leave);
    }

    @Override
    public void delete(Leave Leave) {
        LeaveRepository.delete(Leave);
    }

    @Override
    public Leave update(Leave Leave) {
        return LeaveRepository.save(Leave);
    }

    @Override
    public Leave createLeave(Leave Leave) {
        return LeaveRepository.save(Leave);
    }

    /*@Override
    public Leave findActivitiesInTheCharge(User user) {

    }*/

}
