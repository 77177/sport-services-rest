package org.application.services;

import lombok.RequiredArgsConstructor;
import org.application.dtos.AppUserDto;
import org.application.models.users.*;
import org.application.repositories.users.*;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserRepo appUserRepo;
    private final TrainerRepo trainerRepo;
    private final AdminRepo adminRepo;
    private final SecurityRepo securityRepo;
    private final LearnerRepo learnerRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ModelMapper modelMapper;


    @Transactional
    public List<AppUser> getTrainers() {
        List<AppUser> all = appUserRepo.findAll();

        return all.stream()
                .filter(appUser -> appUser.getAuthority().equals("ROLE_TRAINER"))
                .collect(toList());
    }

    @Transactional
    public long createUser(AppUser appUser) {

        appUser.setPassword(bCryptPasswordEncoder.encode(appUser.getPassword()));

        String authority = appUser.getAuthority();

        long id = -1;

        if (authority.equals("ROLE_TRAINER")) {
            Trainer trainer = new Trainer();
            trainer.apply(appUser);
            trainer = trainerRepo.save(trainer);
            id = trainer.getId();
        } else if (authority.equals("ROLE_USER")) {
            Learner learner = new Learner();
            learner.apply(appUser);
            learner = learnerRepo.save(learner);
            id = learner.getId();
        } else if (authority.equals("ROLE_ADMIN")) {
            Admin admin = new Admin();
            admin.apply(appUser);
            admin = adminRepo.save(admin);
            id = admin.getId();
        } else if (authority.equals("ROLE_SECURITY")) {
            SecurityUser securityUser = new SecurityUser();
            securityUser.apply(appUser);
            securityUser = securityRepo.save(securityUser);
            id = securityUser.getId();
        } else {
            throw new IllegalArgumentException("Broken role");
        }

        return id;
    }

    @Transactional
    public long createUser(AppUserDto appUserDto) {

        AppUser appUser = modelMapper.map(appUserDto, AppUser.class);

        appUser.setPassword(new BCryptPasswordEncoder().encode(appUser.getPassword()));

        String authority = appUser.getAuthority();

        long id = -1;

        if (authority.equals("ROLE_TRAINER")) {
            Trainer trainer = new Trainer();
            trainer.apply(appUser);
            trainer = trainerRepo.save(trainer);
            id = trainer.getId();
        } else if (authority.equals("ROLE_USER")) {
            Learner learner = new Learner();
            learner.apply(appUser);
            learner = learnerRepo.save(learner);
            id = learner.getId();
        } else if (authority.equals("ROLE_ADMIN")) {
            Admin admin = new Admin();
            admin.apply(appUser);
            admin = adminRepo.save(admin);
            id = admin.getId();
        } else if (authority.equals("ROLE_SECURITY")) {
            SecurityUser securityUser = new SecurityUser();
            securityUser.apply(appUser);
            securityUser = securityRepo.save(securityUser);
            id = securityUser.getId();
        } else {
            throw new IllegalArgumentException("Broken role");
        }

        return id;
    }

    @Transactional
    public AppUserDto getUser(Long id){
        return modelMapper.map(appUserRepo.findOne(id), AppUserDto.class);
    }

    @Transactional
    public AppUserDto deleteUser(Long id){
        AppUser appUser = appUserRepo.findOne(id);
        appUserRepo.delete(appUser);
        return modelMapper.map(appUser, AppUserDto.class);
    }

    @Transactional
    public AppUser getCurrentUserInfo() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return appUserRepo.findByUsername(user.getUsername());
    }
}
