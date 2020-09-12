package org.application.services;

import org.application.models.users.*;
import org.application.repositories.users.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class AppUserService {

    private AppUserRepo appUserRepo;

    private TrainerRepo trainerRepo;

    private AdminRepo adminRepo;

    private SecurityRepo securityRepo;

    private LearnerRepo learnerRepo;

    public AppUserService(AppUserRepo appUserRepo, TrainerRepo trainerRepo, AdminRepo adminRepo, SecurityRepo securityRepo, LearnerRepo learnerRepo) {
        this.appUserRepo = appUserRepo;
        this.trainerRepo = trainerRepo;
        this.adminRepo = adminRepo;
        this.securityRepo = securityRepo;
        this.learnerRepo = learnerRepo;
    }

    @Transactional
    public List<AppUser> getTrainers() {
        List<AppUser> all = appUserRepo.findAll();

        return all.stream()
                .filter(appUser -> appUser.getAuthority().equals("ROLE_TRAINER"))
                .collect(toList());
    }

    @Transactional
    public long createUser(AppUser appUser) {

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
    public AppUser getCurrentUserInfo() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return appUserRepo.findByUsername(user.getUsername());
    }
}
