package io.rafflethor.security

import groovy.transform.ToString
import groovy.transform.Immutable

@ToString
@Immutable(copyWith = true)
class User {
    UUID id
    String username
    Set<String> roles = [] as Set
}
