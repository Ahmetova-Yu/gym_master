import java.time.DayOfWeek
import java.util.TreeMap

class Timetable {
    val timetable: MutableMap<DayOfWeek, TreeMap<TimeOfDay, MutableList<TrainingSession>>> = mutableMapOf()

    fun addNewTrainingSession(trainingSession: TrainingSession) {
        val timeMap = timetable.getOrPut(trainingSession.dayOfWeek) { TreeMap() }
        val sessions = timeMap.getOrPut(trainingSession.timeOfDay) { mutableListOf() }

        sessions.add(trainingSession)
    }

    fun getTrainingSessionsForDay(dayOfWeek: DayOfWeek) : TreeMap<TimeOfDay, MutableList<TrainingSession>>? {
        val sessions = timetable.get(dayOfWeek)
        if (sessions == null) {
            println("Тренировок нет")
            return null
        } else {
            return sessions
        }
    }

    fun getTrainingSessionsForDayAndTime(dayOfWeek: DayOfWeek, timeOfDay: TimeOfDay) : List<TrainingSession>? {
        val sessionsDayOfWeek: TreeMap<TimeOfDay, MutableList<TrainingSession>>? = timetable.get(dayOfWeek)
        val sessions: MutableList<TrainingSession>? = sessionsDayOfWeek?.get(timeOfDay)

        if (sessions == null) {
            println("Тренировок нет")
            return null
        } else {
            return sessions
        }
    }
}