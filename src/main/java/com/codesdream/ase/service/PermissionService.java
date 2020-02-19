package com.codesdream.ase.service;

import com.codesdream.ase.component.permission.UserFPCListGenerator;
import com.codesdream.ase.component.permission.UserFSRGenerator;
import com.codesdream.ase.model.permission.*;
import com.codesdream.ase.repository.permission.FunctionalPermissionContainerRepository;
import com.codesdream.ase.repository.permission.ScopePermissionContainerRepository;
import com.codesdream.ase.repository.permission.TagRepository;
import javafx.util.Pair;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class PermissionService implements IPermissionService {

    @Resource
    private TagRepository tagRepository;

    @Resource
    private FunctionalPermissionContainerRepository fpcRepository;

    @Resource
    private ScopePermissionContainerRepository spcRepository;

    @Resource
    private UserFPCListGenerator userFPCListGenerator;

    @Resource
    private UserFSRGenerator userFSRGenerator;

    @Override
    public Optional<Tag> findTag(String name) {
        return tagRepository.findByName(name);
    }

    @Override
    public Optional<FunctionalPermissionContainer> findFPC(String name) {
        return fpcRepository.findByName(name);
    }

    @Override
    public Optional<ScopePermissionContainer> findSPC(String name) {
        return spcRepository.findByName(name);
    }

    @Override
    public Optional<FunctionalPermissionContainer> findFPC(int id) {
        return fpcRepository.findById(id);
    }

    @Override
    public Optional<ScopePermissionContainer> findSPC(int id) {
        return spcRepository.findById(id);
    }

    @Override
    public Collection<PermissionContainersCollection> getPCCs(Tag tag) {
        return new ArrayList<>(tag.getPermissionContainersCollections());
    }

    @Override
    public Collection<Tag> getTagsFromSPC(ScopePermissionContainer spc) {
        return new ArrayList<>(spc.getTags());
    }

    @Override
    public Collection<Tag> getTagsFromUser(User user) {
        return new ArrayList<>(user.getTags());
    }

    @Override
    public Collection<FunctionalPermissionContainer> getFPCs(
            PermissionContainersCollection pcc)
    {

        Collection<PermissionContainersCollection> pccCollections =
                new ArrayList<PermissionContainersCollection>(){{
            add(pcc);
        }};

        // 生成功能性与范围性权限容器关联对
        Collection<FunctionalScopeRelation> fsr =
                userFSRGenerator.generateFSRs(pccCollections);
        return userFPCListGenerator.generateFPC(fsr);
    }

    @Override
    public Collection<User> getUsersFromTag(Tag tag) {
        return new ArrayList<>(tag.getUsers());
    }

    @Override
    public void addRelationItemToPCCollection(PermissionContainersCollection pcc,
                                              FunctionalPermissionContainer fpc,
                                              ScopePermissionContainer spc)
    {
        if(!findFPC(fpc.getId()).isPresent()){

        }
        FunctionalScopeRelation relation = new FunctionalScopeRelation();
        relation.setFunctionalPermissionContainer(fpc);
        relation.setScopePermissionContainer(spc);
        pcc.getFunctionalScopeRelations().add(relation);
        update(pcc);

    }

    @Override
    public void addRelationItemsToPCC(PermissionContainersCollection pcc,
                                      Collection<Pair<FunctionalPermissionContainer, ScopePermissionContainer>> fspcPairs)
    {

    }

    @Override
    public void addUserToTag(Tag tag, User user) {

    }

    @Override
    public void addUsersToTag(Tag tag, Collection<User> users) {

    }

    @Override
    public void addRoleToFPC(FunctionalPermissionContainer fpc, String role) {

    }

    @Override
    public void addRolesToFPC(FunctionalPermissionContainer fpc, Collection<String> roles) {

    }

    @Override
    public void save(Tag tag) {

    }

    @Override
    public void save(FunctionalPermissionContainer fpc) {

    }

    @Override
    public void save(ScopePermissionContainer spc) {

    }

    @Override
    public void save(PermissionContainersCollection pcc) {

    }

    @Override
    public void update(FunctionalPermissionContainer fpc) {

    }

    @Override
    public void update(ScopePermissionContainer spc) {

    }

    @Override
    public void update(PermissionContainersCollection pcc) {

    }
}
