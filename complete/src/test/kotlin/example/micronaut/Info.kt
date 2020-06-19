package example.micronaut

import io.micronaut.core.annotation.Introspected

@Introspected
class Info {
    var git: GitInfo? = null

}