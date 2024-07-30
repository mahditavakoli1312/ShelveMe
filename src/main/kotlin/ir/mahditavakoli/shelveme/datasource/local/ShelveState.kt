package ir.mahditavakoli.shelveme.datasource.local

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.*

@State(
    name = "ShelveState",
    storages = [Storage("ShelveState.xml")]
)
@Service
class ShelveState : PersistentStateComponent<ShelveState.State> {

    data class State(
        var rememberShelve: Boolean = false,
        var autoShelve: Boolean = false,
        var periodically: Boolean = false,
        var periodDuration: Long = 30
    )

    private var state: State = State()

    override fun getState(): State {
        return state
    }

    override fun loadState(state: State) {
        this.state = state
    }

    var rememberShelve: Boolean
        get() = this.state.rememberShelve
        set(value) {
            this.state.rememberShelve = value
        }

    var autoShelve: Boolean
        get() = this.state.autoShelve
        set(value) {
            this.state.autoShelve = value
        }

    var periodically: Boolean
        get() = this.state.periodically
        set(value) {
            this.state.periodically = value
        }

    var periodDuration: Long
        get() = this.state.periodDuration
        set(value) {
            this.state.periodDuration = value
        }

    companion object {
        fun saveRememberShelveState(rememberShelve: Boolean) {
            val samangarState = ApplicationManager.getApplication().service<ShelveState>()
            // Set values
            samangarState.rememberShelve = rememberShelve
        }

        fun saveAutoShelveState(autoShelve: Boolean) {
            val samangarState = ApplicationManager.getApplication().service<ShelveState>()
            // Set values
            samangarState.autoShelve = autoShelve
        }

        fun savePeriodicallyShelveState(periodically: Boolean) {
            val samangarState = ApplicationManager.getApplication().service<ShelveState>()
            // Set values
            samangarState.periodically = periodically
        }

        fun savePeriodDuration(period: Long) {
            val samangarState = ApplicationManager.getApplication().service<ShelveState>()
            // Set values
            samangarState.periodDuration = period
        }

        fun getShelveSate(): State {
            val shelveState = ApplicationManager.getApplication().service<ShelveState>()
            kotlin.runCatching {
                // Get values
                val value1 = shelveState.rememberShelve
                val value2 = shelveState.autoShelve
                val value3 = shelveState.periodically
                val value4 = shelveState.periodDuration

                println("value1: $value1")
                println("value2: $value2")
                println("value3: $value3")
                println("value4: $value4")

            }
            return shelveState.state
        }
    }
}


