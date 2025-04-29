package org.practice.service;

import org.practice.record.User;

public interface IAuthService {
    void signIn(User user);
    void signOut(User user);
    boolean isAuthorized(User user);
    User getCurrentUser();
}
