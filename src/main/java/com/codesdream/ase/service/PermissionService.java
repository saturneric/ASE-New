package com.codesdream.ase.service;

import com.codesdream.ase.component.permission.UserFPCListGenerator;
import com.codesdream.ase.component.permission.UserFSRGenerator;
import com.codesdream.ase.model.permission.*;
import com.codesdream.ase.repository.permission.FunctionalPermissionContainerRepository;
import com.codesdream.ase.repository.permission.PermissionContainersCollectionRepository;
import com.codesdream.ase.repository.permission.ScopePermissionContainerRepository;
import com.codesdream.ase.repository.permission.TagRepository;
import javafx.util.Pair;
import org.apache.poi.ss.formula.functions.T;
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
    private PermissionContainersCollectionRepository pccRepository;

    @Resource
    private IUserService userService;

    @Resource
    private UserFPCListGenerator userFPCListGenerator;

    @Resource
    private UserFSRGenerator userFSRGenerator;

    @Override
    public FunctionalPermissionContainer getDefaultFPC(String name) {
        return new FunctionalPermissionContainer(name);
    }

    @Override
    public ScopePermissionContainer getDefaultSPC(String name) {
        return new ScopePermissionContainer(name);
    }

    @Override
    public PermissionContainersCollection getDefaultPCC(String name) {
        return new PermissionContainersCollection(name);
    }

    @Override
    public Tag getDefaultTag(String name) {
        return new Tag(name);
    }

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
    public PermissionContainersCollection addRelationItemToPCC(PermissionContainersCollection pcc,
                                                               FunctionalPermissionContainer fpc,
                                                               ScopePermissionContainer spc)
    {
        if(!findFPC(fpc.getId()).isPresent()){
            throw new RuntimeException("FPC NOT In Database");
        }
        if(!findSPC(spc.getId()).isPresent()){
            throw new RuntimeException("SPC NOT In Database");
        }
        FunctionalScopeRelation relation = new FunctionalScopeRelation();
        relation.setFunctionalPermissionContainer(fpc);
        relation.setScopePermissionContainer(spc);
        pcc.getFunctionalScopeRelations().add(relation);
        return update(pcc);

    }

    @Override
    public PermissionContainersCollection addRelationItemsToPCC(PermissionContainersCollection pcc,
                                                                Collection<Pair<FunctionalPermissionContainer, ScopePermissionContainer>> fspcPairs)
    {
        for(Pair<FunctionalPermissionContainer, ScopePermissionContainer> fspc :fspcPairs){
            pcc = addRelationItemToPCC(pcc, fspc.getKey(), fspc.getValue());
        }
        return pcc;
    }

    @Override
    public Tag addUserToTag(Tag tag, User user) {
        // 检查用户是否存在
        if(!userService.checkIfUserExists(user.getUsername()).getKey())
            throw new RuntimeException("User Not Exist");
        tag.getUsers().add(user);
        return update(tag);
    }

    @Override
    public Tag addUsersToTag(Tag tag, Collection<User> users) {
        for(User user :users){
            tag = addUserToTag(tag, user);
        }
        return tag;
    }

    @Override
    public FunctionalPermissionContainer addRoleToFPC(FunctionalPermissionContainer fpc, String role) {
        fpc.getRoles().add(role);
        return update(fpc);
    }

    @Override
    public FunctionalPermissionContainer addRolesToFPC(FunctionalPermissionContainer fpc, Collection<String> roles) {
        for(String role : roles){
            fpc = addRoleToFPC(fpc, role);
        }
        return fpc;
    }

    @Override
    public ScopePermissionContainer addTagToSPC(ScopePermissionContainer spc, Tag tag) {
        if(!tagRepository.findByName(tag.getName()).isPresent())
            throw new RuntimeException("Tag Not Exist");
        spc.getTags().add(tag);
        return update(spc);
    }

    @Override
    public ScopePermissionContainer addTagsToSPC(ScopePermissionContainer spc, Collection<Tag> tags) {
        for(Tag tag :tags){
            spc = addTagToSPC(spc, tag);
        }
        return spc;
    }

    @Override
    public Tag addPCCToTag(Tag tag, PermissionContainersCollection pcc) {
        if(!pccRepository.findByName(pcc.getName()).isPresent())
            throw new RuntimeException("PCC Not Exist");
        tag.getPermissionContainersCollections().add(pcc);
        return update(tag);
    }

    @Override
    public Tag addPCCsToTag(Tag tag, Collection<PermissionContainersCollection> pccs) {
        for(PermissionContainersCollection pcc : pccs) {
            tag = addPCCToTag(tag, pcc);
        }
        return tag;
    }

    @Override
    public Tag save(Tag tag) {
        if(tagRepository.findByName(tag.getName()).isPresent())
            throw new RuntimeException("Tag Already Exist");
        return tagRepository.save(tag);
    }

    @Override
    public FunctionalPermissionContainer save(FunctionalPermissionContainer fpc) {
        if(fpcRepository.findByName(fpc.getName()).isPresent())
            throw new RuntimeException("FPC Already Exist");
        return fpcRepository.save(fpc);
    }

    @Override
    public ScopePermissionContainer save(ScopePermissionContainer spc) {
        if(spcRepository.findByName(spc.getName()).isPresent())
            throw new RuntimeException("SPC Already Exist");
        return spcRepository.save(spc);
    }

    @Override
    public PermissionContainersCollection save(PermissionContainersCollection pcc) {
        if(pccRepository.findByName(pcc.getName()).isPresent())
            throw new  RuntimeException("PCC Already Exist");
        return pccRepository.save(pcc);
    }

    @Override
    public Tag update(Tag tag) {
        if(!tagRepository.findByName(tag.getName()).isPresent())
            throw new RuntimeException(("Tag Not Exist"));
        return tagRepository.save(tag);
    }

    @Override
    public FunctionalPermissionContainer update(FunctionalPermissionContainer fpc) {
        if(!fpcRepository.findByName(fpc.getName()).isPresent())
            throw new RuntimeException("FPC Not Exist");
        return fpcRepository.save(fpc);
    }

    @Override
    public ScopePermissionContainer update(ScopePermissionContainer spc) {
        if(!spcRepository.findByName(spc.getName()).isPresent())
            throw new RuntimeException("SPC Not Exist");
        return spcRepository.save(spc);
    }

    @Override
    public PermissionContainersCollection update(PermissionContainersCollection pcc) {
        if(!pccRepository.findByName(pcc.getName()).isPresent())
            throw new RuntimeException("PCC Not Exist");
        return pccRepository.save(pcc);
    }
}
