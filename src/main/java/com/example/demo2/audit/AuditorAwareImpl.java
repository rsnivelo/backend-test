package com.example.demo2.audit;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
    //TODO connect with some auth server and get the user.
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("dummyUser");
    }
}
