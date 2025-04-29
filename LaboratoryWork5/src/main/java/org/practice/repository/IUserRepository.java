package org.practice.repository;

import org.practice.record.User;

public interface IUserRepository extends IDataRepository<User> {
    User getByLogin(String login);
}
