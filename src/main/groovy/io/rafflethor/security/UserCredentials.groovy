package io.rafflethor.security

import groovy.transform.Immutable

@Immutable(copyWith = true)
class UserCredentials {
    String username
    String password
}
