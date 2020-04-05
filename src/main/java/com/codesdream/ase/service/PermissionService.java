package com.codesdream.ase.service;

import com.codesdream.ase.component.permission.UserFPCListGenerator;
import com.codesdream.ase.component.permission.UserFSRGenerator;
import com.codesdream.ase.exception.badrequest.AlreadyExistException;
import com.codesdream.ase.exception.notfound.NotFoundException;
import com.codesdream.ase.model.permission.*;
import com.codesdream.ase.repository.permission.*;
import javafx.util.Pair;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

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

    @Resource
    private FunctionRepository functionRepository;

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
    public Optional<Tag> findTag(Integer id) {
        return tagRepository.findById(id);
    }

    @Override
    public Iterable<Tag> findAllTag() {
        return tagRepository.findAll();
    }

    @Override
    public Set<Tag> findTags(List<Integer> ids) {
        Set<Tag> tagSet = new HashSet<>();
        for(Integer id : ids){
            Optional<Tag> tag = findTag(id);
            if(!tag.isPresent()) throw new NotFoundException(id.toString());
            tagSet.add(tag.get());
        }
        return tagSet;
    }

    @Override
    public Optional<FunctionalPermissionContainer> findFPC(String name) {
        return fpcRepository.findByName(name);
    }

    public Iterable<FunctionalPermissionContainer> findAllFPC() {
        return fpcRepository.findAll();
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
    public Iterable<ScopePermissionContainer> findALLSPC() {
        return spcRepository.findAll();
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
    public Set<PermissionContainersCollection> findPCCs(Set<Integer> pccs) {
        Set<PermissionContainersCollection> set = new HashSet<>();
        for(Integer id : pccs){
            Optional<PermissionContainersCollection> pcc = findPCC(id);
            if(!pcc.isPresent()) throw new NotFoundException(String.format("PCCId: %d",id));
            set.add(pcc.get());
        }
        return set;
    }

    @Override
    public Optional<PermissionContainersCollection> findPCC(Integer id) {
        return pccRepository.findById(id);
    }

    @Override
    public Optional<Function> findFunction(Integer id) {
        return functionRepository.findById(id);
    }

    @Override
    public Optional<Function> findFunction(String name) {
        return functionRepository.findByName(name);
    }

    @Override
    public Set<Function> findFunctions(Set<Integer> funcs) {
        Set<Function> set = new HashSet<>();
        for(Integer id : funcs){
            Optional<Function> function = findFunction(id);
            if(!function.isPresent()) throw new NotFoundException(id.toString());
            set.add(function.get());
        }
        return set;
    }

    @Override
    public Iterable<Function> findAllFunction() {
        return functionRepository.findAll();
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
    public Set<User> getUsersFromTag(Tag tag) {
        return new HashSet<>(tag.getUsers());
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
    public FunctionalPermissionContainer addRoleToFPC(FunctionalPermissionContainer fpc, Function function) {
        fpc.getFunctions().add(function);
        return update(fpc);
    }

    @Override
    public FunctionalPermissionContainer addRolesToFPC(FunctionalPermissionContainer fpc, Collection<Function> functions) {
        for(Function function : functions){
            fpc = addRoleToFPC(fpc, function);
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
            throw new AlreadyExistException(tag.getName());
        return tagRepository.save(tag);
    }

    @Override
    public Function save(Function function) {
        if(functionRepository.findByName(function.getName()).isPresent())
            throw new AlreadyExistException(function.getName());
        return functionRepository.save(function);
    }

    @Override
    public void delete(Tag tag) {
        tagRepository.delete(tag);
    }

    @Override
    public FunctionalPermissionContainer save(FunctionalPermissionContainer fpc) {
        if(fpcRepository.findByName(fpc.getName()).isPresent())
            throw new AlreadyExistException(fpc.getName());
        return fpcRepository.save(fpc);
    }

    @Override
    public ScopePermissionContainer save(ScopePermissionContainer spc) {
        if(spcRepository.findByName(spc.getName()).isPresent())
            throw new AlreadyExistException(spc.getName());
        return spcRepository.save(spc);
    }

    @Override
    public PermissionContainersCollection save(PermissionContainersCollection pcc) {
        if(pccRepository.findByName(pcc.getName()).isPresent())
            throw new  RuntimeException(pcc.getName());
        return pccRepository.save(pcc);
    }

    @Override
    public Tag update(Tag tag) {
        if(!tagRepository.findByName(tag.getName()).isPresent())
            throw new NotFoundException(tag.getName());
        return tagRepository.save(tag);
    }

    @Override
    public Function update(Function function) {
        if(!functionRepository.findByName(function.getName()).isPresent())
            throw new NotFoundException(function.getName());
        return functionRepository.save(function);
    }

    @Override
    public FunctionalPermissionContainer update(FunctionalPermissionContainer fpc) {
        if(!fpcRepository.findByName(fpc.getName()).isPresent())
            throw new NotFoundException(fpc.getName());
        return fpcRepository.save(fpc);
    }

    @Override
    public ScopePermissionContainer update(ScopePermissionContainer spc) {
        if(!spcRepository.findByName(spc.getName()).isPresent())
            throw new NotFoundException(spc.getName());
        return spcRepository.save(spc);
    }

    @Override
    public PermissionContainersCollection update(PermissionContainersCollection pcc) {
        if(!pccRepository.findByName(pcc.getName()).isPresent())
            throw new NotFoundException(pcc.getName());
        return pccRepository.save(pcc);
    }
}
