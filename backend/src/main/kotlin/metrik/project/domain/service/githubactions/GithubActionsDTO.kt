package metrik.project.domain.service.githubactions

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import metrik.infrastructure.utlils.toTimestamp
import org.slf4j.LoggerFactory
import java.time.ZonedDateTime

data class WorkflowRepository(
    val workflows: List<WorkFlowDetails>
)

data class WorkFlowDetails(
    val id: String,
    val name: String
)

@JsonNaming(SnakeCaseStrategy::class)
data class BuildSummaryDTO(
    val totalCount: Int = 0
)

@JsonNaming(SnakeCaseStrategy::class)
data class BuildDetailDTO(var workflowRuns: MutableList<WorkflowRuns>)

@JsonNaming(SnakeCaseStrategy::class)
data class WorkflowRuns(
    val id: String,
    val name: String,
    val runNumber: Int = 0,
    val status: String,
    val conclusion: String,
    val url: String,
    val createdAt: ZonedDateTime?,
    val updatedAt: ZonedDateTime?,
    val jobs: MutableList<Jobs> = mutableListOf()
) {
    private var logger = LoggerFactory.getLogger(this.javaClass.name)

    private fun getBuildStartedTimestamp(): Long? =
        when {
            createdAt != null -> createdAt.toTimestamp()
            updatedAt != null -> updatedAt.toTimestamp()
            else -> null
        }
}

@JsonNaming(SnakeCaseStrategy::class)
data class Jobs(
    val runId: String,
    val name: String,
    val status: String,
    val conclusion: String,
    val startedAt: ZonedDateTime?,
    val completedAt: ZonedDateTime?,
    val steps: List<Steps>
)

@JsonNaming(SnakeCaseStrategy::class)
data class Steps(
    val name: String,
    val status: String,
    val conclusion: String,
    val startedAt: ZonedDateTime?,
    val completedAt: ZonedDateTime?
)