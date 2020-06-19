package example.micronaut

import io.micronaut.core.annotation.Introspected

@Introspected
class GitInfo {
    var commit: GitCommit? = null

}