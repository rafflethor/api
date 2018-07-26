package io.rafflethor.security

interface SecurityRepository {

    User login(String username, String password)

}
